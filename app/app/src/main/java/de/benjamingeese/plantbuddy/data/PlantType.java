package de.benjamingeese.plantbuddy.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class PlantType {
    @PrimaryKey
    public int id;
    public String name;
    public String englishName;
    public String imageUrl;
}
