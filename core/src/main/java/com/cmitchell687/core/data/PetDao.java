package com.cmitchell687.core.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PetDao {
    @Query("SELECT * FROM pets WHERE dog_name " +
            "LIKE '%' || :term || '%' OR owner_name LIKE '%' || :term || '%'")
    LiveData<List<PetModel>> getPets(String term);

    @Query("SELECT * FROM pets WHERE id == :id")
    LiveData<PetModel> getPet(int id);

    @Insert
    void insertPet(PetModel petModel);
}
