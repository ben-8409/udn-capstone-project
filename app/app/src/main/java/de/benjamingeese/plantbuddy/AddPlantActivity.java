package de.benjamingeese.plantbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class AddPlantActivity extends AppCompatActivity {
    private static final long INVALID_PLANT_TYPE = -1;
    public static final String EXTRA_PLANT_TYPE_ID = "type-id";
    public static final String EXTRA_PLANT_TYPE_NAME = "type-name";

    private long mPlantTypeId;
    private EditText mPlantNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);

        //view binding
        TextView plantTypeName = findViewById(R.id.tv_add_plant_plant_type);
        mPlantNameEditText = findViewById(R.id.et_plant_name);


        if(savedInstanceState == null) {
            mPlantTypeId = getIntent().getLongExtra(EXTRA_PLANT_TYPE_ID, INVALID_PLANT_TYPE);
            plantTypeName.setText(getIntent().getStringExtra(EXTRA_PLANT_TYPE_NAME));
        } else {
            mPlantTypeId = savedInstanceState.getLong(EXTRA_PLANT_TYPE_ID);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(EXTRA_PLANT_TYPE_ID, mPlantTypeId);
        super.onSaveInstanceState(outState);
    }
}
