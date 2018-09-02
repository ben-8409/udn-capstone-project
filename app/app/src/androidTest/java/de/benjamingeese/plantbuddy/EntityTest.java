package de.benjamingeese.plantbuddy;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import de.benjamingeese.plantbuddy.data.AppDatabase;
import de.benjamingeese.plantbuddy.data.PlantTypeDao;

@RunWith(AndroidJUnit4.class)

public class EntityTest {
    private PlantTypeDao mPlantTypeDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        //use in memory db for hermetic tests
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mPlantTypeDao = mDb.plantTypeDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writePlantTypeAndReadInTypes() throws Exception {

    }
}
