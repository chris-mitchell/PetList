package com.cmitchell687.petlist.ui.details;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.cmitchell687.core.repository.PetRepository;

public class PetDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private PetRepository petRepository;

    public PetDetailViewModelFactory(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PetDetailViewModel.class)) {
            return (T) (new PetDetailViewModel(petRepository));
        }
        throw new IllegalStateException("Unknown ViewModel class");
    }

}
