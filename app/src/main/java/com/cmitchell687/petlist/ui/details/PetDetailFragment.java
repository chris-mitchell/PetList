package com.cmitchell687.petlist.ui.details;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmitchell687.core.data.PetModel;
import com.cmitchell687.petlist.R;
import com.cmitchell687.petlist.databinding.FragmentPetDetailsBinding;
import com.cmitchell687.petlist.utils.InjectorUtils;

public class PetDetailFragment extends Fragment {

    private static final String PET_ID = "pet_id";

    public static PetDetailFragment newInstance(int petId) {

        PetDetailFragment fragment = new PetDetailFragment();

        Bundle args = new Bundle();
        args.putInt(PET_ID, petId);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.pet_details_title);

        final FragmentPetDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pet_details, container, false);

        PetDetailViewModelFactory factory = InjectorUtils.providePetDetailViewModelFactory(getContext());
        final PetDetailViewModel petDetailViewModel = ViewModelProviders.of(this, factory)
                .get(PetDetailViewModel.class);

        int petId;
        if (getArguments() != null && getArguments().containsKey(PET_ID)) {
            petId = getArguments().getInt(PET_ID);
        } else {
            throw new IllegalArgumentException("Must set a Pet ID");
        }
        petDetailViewModel.getPet(petId).observe(this, new Observer<PetModel>() {
            @Override
            public void onChanged(@Nullable PetModel petModel) {
                binding.setViewModel(petDetailViewModel);
            }
        });

        binding.setViewModel(petDetailViewModel);

        return binding.getRoot();
    }
}
