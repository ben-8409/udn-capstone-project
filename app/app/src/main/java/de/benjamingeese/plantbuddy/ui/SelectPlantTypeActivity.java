package de.benjamingeese.plantbuddy.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import de.benjamingeese.plantbuddy.AddPlantActivity;
import de.benjamingeese.plantbuddy.R;
import de.benjamingeese.plantbuddy.data.PlantType;
import de.benjamingeese.plantbuddy.dummy.PlantTypes;
import de.benjamingeese.plantbuddy.viewmodel.PlantTypeListViewModel;

public class SelectPlantTypeActivity extends AppCompatActivity {

    private PlantTypeRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_plant_type);
        setTitle(R.string.title_select_plant_activity);

        View recyclerView = findViewById(R.id.select_plant_type_list);

        //setup adapter
        mAdapter = new PlantTypeRecyclerViewAdapter(new PlantTypeRecyclerViewAdapter.PlantTypeClickListener() {
            @Override
            public void OnPlantTypeSelected(PlantType type) {
                Intent toAddPlant = new Intent(SelectPlantTypeActivity.this, AddPlantActivity.class);
                toAddPlant.putExtra(AddPlantActivity.EXTRA_PLANT_TYPE_ID, type.id);
                toAddPlant.putExtra(AddPlantActivity.EXTRA_PLANT_TYPE_NAME, type.name);
                startActivity(toAddPlant);
            }
        });

        //setup recyclerview
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        //Set up data binding
        final PlantTypeListViewModel viewModel =
                ViewModelProviders.of(this).get(PlantTypeListViewModel.class);

        subscribeUI(viewModel);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void subscribeUI(PlantTypeListViewModel viewModel) {
        viewModel.getPlantTypes().observe(this, new Observer<List<PlantType>>() {
            @Override
            public void onChanged(@Nullable List<PlantType> plantTypes) {
                mAdapter.setPlantTypeList(plantTypes);
            }
        });
    }
}
