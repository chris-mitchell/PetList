package com.cmitchell687.petlist.ui.petlist;

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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PetsViewModelTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private Observer<List<PetModel>> observer = mock(Observer.class);

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private PetsViewModel petViewModel;

    @Before
    public void setUp() {
        petRepository = mock(PetRepository.class);
        petViewModel = new PetsViewModel(petRepository);
    }

    @Test
    public void givenPetsWhenSearchedThenShowResult() {
        List<PetModel> petModel = new ArrayList<>();
        PetModel myPet = new PetModel();
        myPet.setId(1);
        myPet.setDogName("Chuli");
        myPet.setOwnerName("me");
        petModel.add(myPet);

        MutableLiveData<List<PetModel>> result = new MutableLiveData<>();
        result.setValue(petModel);

        petViewModel.updateSearchTerm("chuli");

        when(petRepository.getPets("chuli")).thenReturn(result);
        petViewModel.getPets().observeForever(observer);

        assertThat(petViewModel.getPets().getValue().get(0), equalTo(myPet));
    }

    @Test
    public void givenPetsWhenQueriedThenShowContent() {
        List<PetModel> petModel = PetFactory.createPets(10);
        MutableLiveData<List<PetModel>> result = new MutableLiveData<>();
        result.setValue(petModel);

        when(petRepository.getPets("")).thenReturn(result);
        petViewModel.getPets().observeForever(observer);

        assertThat(petViewModel.getContentVisibility(), equalTo(View.VISIBLE));
        assertThat(petViewModel.getLoadingVisibility(), equalTo(View.GONE));
        assertThat(petViewModel.getEmptyStateVisibility(), equalTo(View.GONE));
    }

    @Test
    public void givenNoPetsWhenQueriedThenShowEmptyState() {
        List<PetModel> petModel = PetFactory.createPets(0);
        MutableLiveData<List<PetModel>> result = new MutableLiveData<>();
        result.setValue(petModel);

        when(petRepository.getPets("")).thenReturn(result);
        petViewModel.getPets().observeForever(observer);

        assertThat(petViewModel.getContentVisibility(), equalTo(View.GONE));
        assertThat(petViewModel.getLoadingVisibility(), equalTo(View.GONE));
        assertThat(petViewModel.getEmptyStateVisibility(), equalTo(View.VISIBLE));
    }

    @Test
    public void givenLoadingWhenQueriedThenShowLoading() {
        MutableLiveData<List<PetModel>> result = new MutableLiveData<>();
        result.setValue(null);

        when(petRepository.getPets("")).thenReturn(result);
        petViewModel.getPets().observeForever(observer);

        assertThat(petViewModel.getContentVisibility(), equalTo(View.GONE));
        assertThat(petViewModel.getLoadingVisibility(), equalTo(View.VISIBLE));
        assertThat(petViewModel.getEmptyStateVisibility(), equalTo(View.GONE));
    }

}
