package de.benjamingeese.plantbuddy.viewmodel;
//Notice: This file is based on the Android Architecture Components Basic Example
//See: https://github.com/googlesamples/android-architecture-components/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/viewmodel/ProductListViewModel.java
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

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import java.util.List;

import de.benjamingeese.plantbuddy.PlantBuddyApp;
import de.benjamingeese.plantbuddy.data.PlantType;

public class PlantTypeListViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<PlantType>> mObservablePlantTypes;

    public PlantTypeListViewModel(Application application) {
        super(application);

        mObservablePlantTypes = new MediatorLiveData<>();
        //set to null on start
        mObservablePlantTypes.setValue(null);

        //get data
        LiveData<List<PlantType>> plantTypes =
                ((PlantBuddyApp) application).getDatabase().plantTypeDao().loadAll();

        //observe
        mObservablePlantTypes.addSource(plantTypes, new Observer<List<PlantType>>() {
            @Override
            public void onChanged(@Nullable List<PlantType> plantTypes) {
                mObservablePlantTypes.setValue(plantTypes);
            }
        });

    }

    public LiveData<List<PlantType>> getPlantTypes() {
        return mObservablePlantTypes;
    }
}
