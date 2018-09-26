package com.cmitchell687.petlist.ui.add;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.databinding.adapters.TextViewBindingAdapter;
import android.text.TextUtils;
import android.view.View;

import com.cmitchell687.core.repository.PetRepository;

import java.io.File;

public class AddPetViewModel extends ViewModel {

    private PetRepository petRepository;
    private ObservableField<String> petName = new ObservableField<>();
    private ObservableField<String> ownerName = new ObservableField<>();
    private MutableLiveData<Boolean> isPetAdded = new MutableLiveData<>();
    private boolean photoTaken = false;
    private File file;

    public AddPetViewModel(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public void onDoneClicked(View view) {
        if (!photoTaken || TextUtils.isEmpty(petName.get()) || TextUtils.isEmpty(ownerName.get())) {
            isPetAdded.setValue(false);
        } else {
            petRepository.insertPet(petName.get(), ownerName.get(), file);
            isPetAdded.setValue(true);
        }
    }

    public LiveData<Boolean> isPetAdded() {
        return isPetAdded;
    }

    public void setPhotoFile(File file) {
        this.file = file;
    }

    public void isPhotoTaken(boolean isPhotoTaken) {
        photoTaken = isPhotoTaken;
    }

    public TextViewBindingAdapter.OnTextChanged petNameWatcher = new TextViewBindingAdapter.OnTextChanged() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            petName.set(s.toString());
        }
    };

    public TextViewBindingAdapter.OnTextChanged ownerNameWatcher = new TextViewBindingAdapter.OnTextChanged() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ownerName.set(s.toString());
        }
    };
}
