package com.cmitchell687.petlist.utils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.cmitchell687.core.repository.PhotoRepository;

public class CameraCaptureViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private PhotoRepository photoRepository;

    public CameraCaptureViewModelFactory(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CameraCaptureViewModel.class)) {
            return (T) (new CameraCaptureViewModel(photoRepository));
        }
        throw new IllegalStateException("Unknown ViewModel class");
    }

}
