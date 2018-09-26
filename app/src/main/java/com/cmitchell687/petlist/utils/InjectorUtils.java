package com.cmitchell687.petlist.utils;

import android.content.Context;

import com.cmitchell687.core.repository.PetRepository;
import com.cmitchell687.core.repository.PhotoRepository;
import com.cmitchell687.petlist.ui.add.AddPetViewModelFactory;
import com.cmitchell687.petlist.ui.details.PetDetailViewModelFactory;
import com.cmitchell687.petlist.ui.petlist.PetsViewModel;
import com.cmitchell687.petlist.ui.petlist.PetsViewModelFactory;

public class InjectorUtils {

    private static PetRepository providePetRepository(Context context) {
        return PetRepository.getInstance(context.getApplicationContext());
    }

    private static PhotoRepository providePhotoRepository() {
        return new PhotoRepository();
    }

    public static PetsViewModelFactory providePetsViewModelFactory(Context context) {
        return new PetsViewModelFactory(providePetRepository(context));
    }

    public static AddPetViewModelFactory provideAddPetViewModelFactory(Context context) {
        return new AddPetViewModelFactory(providePetRepository(context));
    }

    public static PetDetailViewModelFactory providePetDetailViewModelFactory(Context context) {
        return new PetDetailViewModelFactory(providePetRepository(context));
    }

    public static CameraCaptureViewModelFactory provideCameraCaptureViewModelFactory(Context context) {
        return new CameraCaptureViewModelFactory(providePhotoRepository());
    }
}
