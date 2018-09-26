package com.cmitchell687.petlist.ui.add;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.cmitchell687.core.repository.PetRepository;
import com.cmitchell687.petlist.ui.petlist.PetsViewModel;

public class AddPetViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private PetRepository petRepository;

    public AddPetViewModelFactory(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddPetViewModel.class)) {
            return (T) (new AddPetViewModel(petRepository));
        }
        throw new IllegalStateException("Unknown ViewModel class");
    }
}
