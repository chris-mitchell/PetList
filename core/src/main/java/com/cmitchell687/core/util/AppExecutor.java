package com.cmitchell687.core.util;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {

    private static final Executor IO_EXECUTOR = Executors.newSingleThreadExecutor();

    public static void runOnIoThread(@NonNull Runnable runnable) {
        IO_EXECUTOR.execute(runnable);
    }

}
