package com.cmitchell687.petlist.utils;

import com.cmitchell687.core.data.PetModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PetFactory {

    public static List<PetModel> createPets(int count) {
        List<PetModel> models = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            models.add(createPet(i));
        }
        return models;
    }

    public static PetModel createPet(int id) {
        PetModel model = new PetModel();
        model.setId(id);
        model.setDogPhotoUri(UUID.randomUUID().toString());
        model.setDogName(UUID.randomUUID().toString());
        model.setOwnerName(UUID.randomUUID().toString());

        return model;
    }
}
