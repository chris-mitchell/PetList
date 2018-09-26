package com.cmitchell687.petlist.ui.details;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.view.View;

import com.cmitchell687.core.data.PetModel;
import com.cmitchell687.core.repository.PetRepository;
import com.cmitchell687.petlist.utils.PetFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PetDetailViewModelTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private Observer<PetModel> observer = mock(Observer.class);

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private PetDetailViewModel petDetailViewModel;

    @Before
    public void setUp() {
        petRepository = mock(PetRepository.class);
        petDetailViewModel = new PetDetailViewModel(petRepository);
    }

    @Test
    public void givenValidPetWhenRequestedThenReturnsPet() {
        PetModel petModel = PetFactory.createPet(1);
        MutableLiveData<PetModel> result = new MutableLiveData<>();
        result.setValue(petModel);

        when(petRepository.getPet(1)).thenReturn(result);
        petDetailViewModel.getPet(1).observeForever(observer);

        assertThat(petDetailViewModel.getPet(1).getValue(), equalTo(petModel));
    }

    @Test
    public void givenNoPetWhenLoadingThenShowLoading() {
        MutableLiveData<PetModel> result = new MutableLiveData<>();
        result.setValue(null);

        when(petRepository.getPet(1)).thenReturn(result);
        petDetailViewModel.getPet(1).observeForever(observer);

        assertThat(petDetailViewModel.getContentVisibility(), equalTo(View.GONE));
        assertThat(petDetailViewModel.getLoadingVisibility(), equalTo(View.VISIBLE));
    }

    @Test
    public void givenPetWhenReturnedThenShowContent() {
        PetModel petModel = PetFactory.createPet(1);
        MutableLiveData<PetModel> result = new MutableLiveData<>();
        result.setValue(petModel);

        when(petRepository.getPet(1)).thenReturn(result);
        petDetailViewModel.getPet(1).observeForever(observer);

        assertThat(petDetailViewModel.getContentVisibility(), equalTo(View.VISIBLE));
        assertThat(petDetailViewModel.getLoadingVisibility(), equalTo(View.GONE));

    }

}
