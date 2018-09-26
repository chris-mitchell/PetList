package com.cmitchell687.petlist.ui.add;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cmitchell687.petlist.R;
import com.cmitchell687.petlist.databinding.FragmentAddPetBinding;
import com.cmitchell687.petlist.utils.CameraCaptureViewModel;
import com.cmitchell687.petlist.utils.InjectorUtils;

import java.io.File;

import static com.cmitchell687.petlist.utils.Constants.AUTHORITY_FILE_PROVIDER;
import static com.cmitchell687.petlist.utils.Constants.CAMERA_CAPTURE_REQUEST;

public class AddPetFragment extends Fragment {

    public static AddPetFragment newInstance() {
        return new AddPetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentAddPetBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_pet,
                container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.add_pets_title);

        View view = binding.getRoot();

        AddPetViewModelFactory factory = InjectorUtils.provideAddPetViewModelFactory(getContext());
        final AddPetViewModel addPetViewModel = ViewModelProviders.of(this, factory)
                .get(AddPetViewModel.class);

        CameraCaptureViewModel cameraCaptureViewModel = ViewModelProviders.of(getActivity())
                .get(CameraCaptureViewModel.class);

        cameraCaptureViewModel.getPhotoComplete().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isComplete) {
                addPetViewModel.isPhotoTaken(isComplete != null && isComplete);
            }
        });

        cameraCaptureViewModel.getPhotoPlaceholder().observe(this, new Observer<File>() {
            @Override
            public void onChanged(@Nullable File file) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (file != null &&
                        takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

                    Uri photoUri = FileProvider.getUriForFile(getActivity(), AUTHORITY_FILE_PROVIDER, file);

                    addPetViewModel.setPhotoFile(file);

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    getActivity().startActivityForResult(takePictureIntent, CAMERA_CAPTURE_REQUEST);
                } else {
                    Toast.makeText(getActivity(), R.string.generic_error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        addPetViewModel.isPetAdded().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isComplete) {
                if (isComplete != null && isComplete && getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getContext(), R.string.add_pets_missing_fields, Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.setCameraCapture(cameraCaptureViewModel);
        binding.setViewModel(addPetViewModel);

        return view;
    }
}
