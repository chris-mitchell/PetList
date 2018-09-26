package com.cmitchell687.core.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.cmitchell687.core.util.AppExecutor;
import com.cmitchell687.core.util.SingleLiveEvent;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PhotoRepository {

    private static final String TAG = PhotoRepository.class.getSimpleName();

    private SingleLiveEvent<File> file = new SingleLiveEvent<>();

    public void createFile(final Context context) {
        Runnable createFile = new Runnable() {
            @Override
            public void run() {
                File tmp;
                try {
                    tmp = createImageFile(context.getApplicationContext());
                } catch (IOException e) {
                    Log.e(TAG, "Failed to create file: " + e);
                    file.postValue(null);
                    return;
                }
                file.postValue(tmp);
            }
        };
        AppExecutor.runOnIoThread(createFile);
    }

    public LiveData<File> getFile() {
        return file;
    }

    private File createImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getFilesDir();
        return File.createTempFile(imageFileName,".jpg", storageDir);
    }
}
