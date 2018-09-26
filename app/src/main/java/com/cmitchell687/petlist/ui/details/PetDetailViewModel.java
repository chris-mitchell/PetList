package com.cmitchell687.petlist.ui.details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.cmitchell687.core.data.PetModel;
import com.cmitchell687.core.repository.PetRepository;
import com.cmitchell687.petlist.utils.Visibility;

public class PetDetailViewModel extends ViewModel {

    private LiveData<PetModel> petModel;
    private PetRepository petRepository;

    public PetDetailViewModel(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public LiveData<PetModel> getPet(int petId) {
        petModel = petRepository.getPet(petId);
        return petModel;
    }

    public String getDogPhotoUri() {
        return petModel.getValue() != null ? petModel.getValue().getDogPhotoUri() : "";
    }

    public String getDogName() {
        return petModel.getValue() != null ? petModel.getValue().getDogName() : "";
    }

    public String getOwnerName() {
        return petModel.getValue() != null ? petModel.getValue().getOwnerName() : "";
    }

    @Visibility
    public int getLoadingVisibility() {
        return petModel.getValue() == null ? View.VISIBLE : View.GONE;
    }

    @Visibility
    public int getContentVisibility() {
        return petModel.getValue() != null ? View.VISIBLE : View.GONE;
    }
}
