package com.example.viola.movies;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

public class ApplicationExecuter {
    public static final Object LOCK = new Object();
    private static ApplicationExecuter sInstance;
    private final Executor diskIO;


    private final Executor mainThread;
    private final Executor networkIO;

    private ApplicationExecuter(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }

    public static ApplicationExecuter getsInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new ApplicationExecuter(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(3),
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    private static class MainThreadExecutor implements Executor {

        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }
}
