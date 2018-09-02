package de.benjamingeese.plantbuddy.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.benjamingeese.plantbuddy.data.PlantType;

public class PlantTypes {

    public static final List<PlantType> ITEMS = new ArrayList<>();

    public static final Map<Integer, PlantType> ITEM_MAP = new HashMap<Integer, PlantType>();

    public static final int count = 10;

    static {
        //first plant
        PlantType type = new PlantType();
        type.id = 1;
        type.name = "Euphorbia pulcherrima";
        type.englishName = "Poinsettia";
        type.imageUrl = "https://upload.wikimedia.org/wikipedia/commons/3/33/Poinsettia_MBG.jpg";
        addItem(type);

        //second plant
        PlantType type2 = new PlantType();
        type2.id = 2;
        type2.name = "Yucca";
        type2.imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/79/Yucca_queretaroensis_fh_0360_MEX_B.jpg/400px-Yucca_queretaroensis_fh_0360_MEX_B.jpg";
        addItem(type2);

    }

    private static void addItem(PlantType t) {
        ITEMS.add(t);
        ITEM_MAP.put(t.id, t);
    }

}
