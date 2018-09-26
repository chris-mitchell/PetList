package com.cmitchell687.petlist.ui.add;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.cmitchell687.core.repository.PetRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AddPetViewModelTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private Observer<Boolean> observer = mock(Observer.class);

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private AddPetViewModel addPetViewModel;

    @Before
    public void setUp() {
        petRepository = mock(PetRepository.class);
        addPetViewModel = new AddPetViewModel(petRepository);
    }

    @Test
    public void givenValidPetInfoWhenDoneClickedThenVerifyInserted() {
        String pet = "abc";
        String owner = "abc";
        File file = new File("/abc");

        addPetViewModel.petNameWatcher.onTextChanged(pet, 3, 3, 3);
        addPetViewModel.ownerNameWatcher.onTextChanged(owner, 3, 3, 3);
        addPetViewModel.setPhotoFile(file);
        addPetViewModel.isPhotoTaken(true);

        MutableLiveData<Boolean> result = new MutableLiveData<>();
        result.setValue(true);

        addPetViewModel.isPetAdded().observeForever(observer);
        addPetViewModel.onDoneClicked(null);

        verify(petRepository, times(1)).insertPet(pet, owner, file);
        assertThat(addPetViewModel.isPetAdded().getValue(), equalTo(true));
    }

    @Test
    public void givenInvalidPetInfoWhenDoneClickedThenVerifyNotInserted() {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        result.setValue(false);

        addPetViewModel.isPetAdded().observeForever(observer);
        addPetViewModel.onDoneClicked(null);

        verify(petRepository, never()).insertPet(any(String.class), any(String.class), any(File.class));
        assertThat(addPetViewModel.isPetAdded().getValue(), equalTo(false));
    }

}
