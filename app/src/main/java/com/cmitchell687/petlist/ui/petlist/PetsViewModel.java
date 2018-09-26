package com.cmitchell687.petlist.ui.petlist;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.cmitchell687.core.data.PetModel;
import com.cmitchell687.core.repository.PetRepository;
import com.cmitchell687.petlist.utils.Visibility;

import org.w3c.dom.Text;

import java.util.List;

public class PetsViewModel extends ViewModel {

    private MutableLiveData<String> mutableTerm = new MutableLiveData<>();
    private LiveData<List<PetModel>> filteredPets;

    public PetsViewModel(final PetRepository petRepository) {
        filteredPets = Transformations.switchMap(mutableTerm, new Function<String, LiveData<List<PetModel>>>() {
            @Override
            public LiveData<List<PetModel>> apply(String input) {
                return petRepository.getPets(input);
            }
        });

        clearSearchTerm();
    }

    public LiveData<List<PetModel>> getPets() {
        return filteredPets;
    }

    public void clearSearchTerm() {
        mutableTerm.setValue("");
    }

    public void updateSearchTerm(String term) {
        mutableTerm.setValue(term);
    }

    @Visibility
    public int getLoadingVisibility() {
        return filteredPets.getValue() == null ? View.VISIBLE : View.GONE;
    }

    @Visibility
    public int getEmptyStateVisibility() {
        return filteredPets.getValue() != null &&
                filteredPets.getValue().size() == 0 ? View.VISIBLE : View.GONE;
    }

    @Visibility
    public int getContentVisibility() {
        return filteredPets.getValue() != null &&
                filteredPets.getValue().size() != 0 ? View.VISIBLE : View.GONE;
    }

}
