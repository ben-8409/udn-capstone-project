package de.benjamingeese.plantbuddy.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.benjamingeese.plantbuddy.R;
import de.benjamingeese.plantbuddy.data.PlantType;

public class PlantTypeRecyclerViewAdapter
        extends RecyclerView.Adapter<PlantTypeRecyclerViewAdapter.ViewHolder> {

    private List<PlantType> mTypes;
    PlantTypeClickListener mSelectListener;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PlantType type = (PlantType) view.getTag(R.id.TAG_PLANT_TYPE);
            if (mSelectListener != null && type != null) {
                mSelectListener.OnPlantTypeSelected(type);
            }
        }
    };

    PlantTypeRecyclerViewAdapter(PlantTypeClickListener selectListener) {
        mSelectListener = selectListener;
    }

    public void setPlantTypeList(final List<PlantType> types) {
        mTypes = types;
        notifyDataSetChanged();
        //TODO: Implement DiffUtil could be nice.
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selectplant_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlantType type = mTypes.get(position);
        //image handling
        if(type.imageUrl != null && !TextUtils.isEmpty(type.imageUrl)
                && holder.mPlantTypeImage != null) {
            Picasso.get().load(type.imageUrl)
                    .into(holder.mPlantTypeImage);
            //TODO Loading and error indicator images
        }
        holder.mPlantTypeName.setText(type.name);

        holder.itemView.setOnClickListener(mOnClickListener);
        holder.itemView.setTag(R.id.TAG_PLANT_TYPE, type);
    }

    @Override
    public int getItemCount() {
        return mTypes == null ? 0 : mTypes.size();
    }

    interface PlantTypeClickListener {
        void OnPlantTypeSelected(PlantType type);
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
