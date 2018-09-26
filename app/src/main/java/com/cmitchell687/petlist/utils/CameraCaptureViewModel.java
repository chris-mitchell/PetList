package com.cmitchell687.petlist.utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.cmitchell687.core.repository.PhotoRepository;

import java.io.File;

public class CameraCaptureViewModel extends ViewModel {

    private PhotoRepository photoRepository;
    private MutableLiveData<Boolean> liveDataPhoto = new MutableLiveData<>();

    public CameraCaptureViewModel(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public void takePhoto(View view) {
        Context context =  view.getContext();
        photoRepository.createFile(context);
    }

    public LiveData<File> getPhotoPlaceholder() {
        return photoRepository.getFile();
    }

    public void photoComplete() {
        liveDataPhoto.setValue(true);
    }

    public LiveData<Boolean> getPhotoComplete() {
        return liveDataPhoto;
    }



}
