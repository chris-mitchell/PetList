package com.cmitchell687.petlist.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cmitchell687.petlist.R;
import com.cmitchell687.petlist.ui.petlist.PetsFragment;
import com.cmitchell687.petlist.utils.CameraCaptureViewModel;
import com.cmitchell687.petlist.utils.CameraCaptureViewModelFactory;
import com.cmitchell687.petlist.utils.InjectorUtils;

import static com.cmitchell687.petlist.utils.Constants.CAMERA_CAPTURE_REQUEST;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    private CameraCaptureViewModel cameraCaptureViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_container, PetsFragment.newInstance())
                    .commitAllowingStateLoss();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        CameraCaptureViewModelFactory factory = InjectorUtils.provideCameraCaptureViewModelFactory(this);
        cameraCaptureViewModel = ViewModelProviders.of(this, factory)
                .get(CameraCaptureViewModel.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_CAPTURE_REQUEST && resultCode == RESULT_OK) {
            cameraCaptureViewModel.photoComplete();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackStackChanged() {
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        getSupportActionBar().setDisplayHomeAsUpEnabled(backStackCount != 0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }
}
