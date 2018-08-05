package de.benjamingeese.plantbuddy.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantTypes {

    public static final List<PlantType> ITEMS = new ArrayList<>();

    public static final Map<Long, PlantType> ITEM_MAP = new HashMap<>();

    public static final int count = 10;

    static {
        for(int i = 0; i < count; i++) {
            addItem(createItem(i));
        }
    }

    private static void addItem(PlantType t) {
        ITEMS.add(t);
        ITEM_MAP.put(t.id, t);
    }

    private static PlantType createItem(int i) {
        return new PlantType(i, "Type " + i, null);
    }

    public static class PlantType {
        public final long id;
        public final String name;
        public final String imageUrl;


        public PlantType(long id, String name, String imageUrl) {
            this.id = id;
            this.name = name;
            this.imageUrl = imageUrl;
        }
    }
}
