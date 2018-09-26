package com.cmitchell687.core.database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cmitchell687.core.LiveDataTestUtil;
import com.cmitchell687.core.data.AppDatabase;
import com.cmitchell687.core.data.PetDao;
import com.cmitchell687.core.data.PetModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
public class PetDatabaseTests {

    private AppDatabase database;
    private PetDao petDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        petDao = database.petDao();
    }

    @After
    public void closeDb() {
        database.close();
    }



    @Test
    public void givenOnePetWhenPetInsertedThenFindPet() throws InterruptedException {
        PetModel pet = PetFactory.createPet(1);
        petDao.insertPet(pet);

        LiveData<PetModel> foundPet = petDao.getPet(1);

        assertThat(pet, equalTo(LiveDataTestUtil.getValue(foundPet)));
    }

    @Test
    public void givenTenPetsWhenPetsInsertedThenFindTenPets() throws InterruptedException {
        int count = 10;

        List<PetModel> pets = PetFactory.createPets(count);
        for (PetModel pet : pets) {
            petDao.insertPet(pet);
        }

        LiveData<List<PetModel>> foundPet = petDao.getPets("");
        assertThat(pets, equalTo(LiveDataTestUtil.getValue(foundPet)));
    }

    @Test
    public void givenPetsWhenPetsInsertedThenFindPetByName() throws InterruptedException {

        // given
        List<PetModel> pets = new ArrayList<>();
        PetModel myPet = PetFactory.createPet(1);
        myPet.setOwnerName("me");
        myPet.setDogName("chuli");
        pets.add(myPet);
        pets.add(PetFactory.createPet(2));
        pets.add(PetFactory.createPet(3));

        List<PetModel> knownPets = new ArrayList<>();
        knownPets.add(myPet);

        // when
        for (PetModel pet : pets) {
            petDao.insertPet(pet);
        }

        // then
        LiveData<List<PetModel>> foundPets = petDao.getPets("chuli");
        assertThat(knownPets, equalTo(LiveDataTestUtil.getValue(foundPets)));

    }

}
