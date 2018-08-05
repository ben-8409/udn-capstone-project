package de.benjamingeese.plantbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.benjamingeese.plantbuddy.dummy.PlantTypes;

public class SelectPlantTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_plant_type);
        setTitle(R.string.title_select_plant_activity);

        View recyclerView = findViewById(R.id.select_plant_type_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new PlantTypeRecyclerViewAdapter(new PlantTypeRecyclerViewAdapter.PlantTypeClickListener() {
                @Override
                public void OnPlantTypeSelected(PlantTypes.PlantType type) {
                    Intent toAddPlant = new Intent(SelectPlantTypeActivity.this, AddPlantActivity.class);
                    toAddPlant.putExtra(AddPlantActivity.EXTRA_PLANT_TYPE_ID, type.id);
                    toAddPlant.putExtra(AddPlantActivity.EXTRA_PLANT_TYPE_NAME, type.name);
                    startActivity(toAddPlant);
                }
            }, PlantTypes.ITEMS));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    public static class PlantTypeRecyclerViewAdapter
            extends RecyclerView.Adapter<PlantTypeRecyclerViewAdapter.ViewHolder> {

        private final List<PlantTypes.PlantType> mTypes;
        PlantTypeClickListener mSelectListener;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlantTypes.PlantType type = (PlantTypes.PlantType) view.getTag(R.id.TAG_PLANT_TYPE);
                if (mSelectListener != null && type != null) {
                    mSelectListener.OnPlantTypeSelected(type);
                }
            }
        };

        PlantTypeRecyclerViewAdapter(PlantTypeClickListener selectListener, List<PlantTypes.PlantType> types) {
            mSelectListener = selectListener;
            mTypes = types;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.selectplant_list_content, parent, false);
            return new PlantTypeRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PlantTypes.PlantType type = mTypes.get(position);
            //TODO Image handling
            holder.mPlantTypeName.setText(type.name);

            holder.itemView.setOnClickListener(mOnClickListener);
            holder.itemView.setTag(R.id.TAG_PLANT_TYPE, type);
        }

        @Override
        public int getItemCount() {
            return mTypes.size();
        }

        interface PlantTypeClickListener {
            void OnPlantTypeSelected(PlantTypes.PlantType type);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mPlantTypeName;
            final ImageView mPlantTypeImage;

            public ViewHolder(View itemView) {
                super(itemView);
                mPlantTypeName = itemView.findViewById(R.id.tv_select_plant_item_plant_type);
                mPlantTypeImage = itemView.findViewById(R.id.iv_select_plant_item_plant_image);
            }
        }
    }

}
