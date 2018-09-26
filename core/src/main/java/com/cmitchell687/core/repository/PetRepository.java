package com.cmitchell687.core.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cmitchell687.core.data.AppDatabase;
import com.cmitchell687.core.data.PetDao;
import com.cmitchell687.core.data.PetModel;
import com.cmitchell687.core.util.AppExecutor;

import java.io.File;
import java.util.List;

public class PetRepository {

    private static PetRepository petRepository;
    private PetDao petDao;

    private PetRepository(PetDao petDao) {
        this.petDao = petDao;
    }

    public LiveData<List<PetModel>> getPets(String term) {
        return petDao.getPets(term);
    }

    public LiveData<PetModel> getPet(int id) {
        return petDao.getPet(id);
    }

    public void insertPet(@NonNull final String dogName, @NonNull final String ownerName,
                          @NonNull final File photo) {
        Runnable insertRunnable = new Runnable() {
            @Override
            public void run() {
                PetModel petModel = new PetModel();
                petModel.setDogName(dogName);
                petModel.setOwnerName(ownerName);
                petModel.setDogPhotoUri(photo.toString());
                petDao.insertPet(petModel);
            }
        };
        AppExecutor.runOnIoThread(insertRunnable);
    }

    public static PetRepository getInstance(Context context) {
        if (petRepository == null) {
            petRepository = new PetRepository(AppDatabase.getInstance(context).petDao());
        }
        return petRepository;
    }
}
