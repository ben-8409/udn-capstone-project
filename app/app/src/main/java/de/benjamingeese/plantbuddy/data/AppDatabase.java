/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.benjamingeese.plantbuddy.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.List;

import de.benjamingeese.plantbuddy.AppExecutors;
import de.benjamingeese.plantbuddy.dummy.PlantTypes;

@Database(entities = {PlantType.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "plantbuddy-db" ;

    //Implement a singleton as recommended since creating a room database is expensive
    //See: https://github.com/googlesamples/android-architecture-components/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/db/AppDatabase.java
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if(sInstance == null) {
            synchronized (AppDatabase.class) {
                if(sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    //Implement a singleton as recommended since creating a room database is expensive
    //See: https://github.com/googlesamples/android-architecture-components/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/db/AppDatabase.java
    private static AppDatabase buildDatabase(final Context applicationContext, final AppExecutors executors) {
        return Room.databaseBuilder(applicationContext, AppDatabase.class, DATABASE_NAME).addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                executors.diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        final AppDatabase database = AppDatabase.getInstance(applicationContext, executors);
                        //Generate data for pre-population
                        final List<PlantType> types = PlantTypes.ITEMS;
                        database.runInTransaction(new Runnable() {
                            @Override
                            public void run() {
                                database.plantTypeDao().insertAll(types);
                            }
                        });
                        database.setDatabaseCreated();

                    }
                });
            }
        }).build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }


    public abstract PlantTypeDao plantTypeDao();
}
