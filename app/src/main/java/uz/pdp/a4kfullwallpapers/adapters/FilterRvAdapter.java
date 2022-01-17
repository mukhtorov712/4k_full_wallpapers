package uz.pdp.a4kfullwallpapers.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devs.sketchimage.SketchImage;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import uz.pdp.a4kfullwallpapers.databinding.ItemFilterRvBinding;
import uz.pdp.a4kfullwallpapers.models.FilterValue;

public class FilterRvAdapter extends RecyclerView.Adapter<FilterRvAdapter.MyFilterHolder>{

    private List<FilterValue> filterValueList;
    private OnFilterItemClickLestener lestener;

    public FilterRvAdapter(List<FilterValue> filterValueList, OnFilterItemClickLestener lestener) {
        this.filterValueList = filterValueList;
        this.lestener = lestener;
    }

    @Override
    public MyFilterHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemFilterRvBinding binding =
                ItemFilterRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        MyFilterHolder myFilterHolder = new MyFilterHolder(binding);
        return myFilterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyFilterHolder holder, int position) {
        FilterValue filterValue = filterValueList.get(position);
        SketchImage sketchImage = new SketchImage.Builder(holder.binding.getRoot().getContext(), filterValue.getImage()).build();
        Bitmap imageAs = sketchImage.getImageAs(filterValue.getValue(), 80);
        holder.binding.image.setImageBitmap(imageAs);
        holder.itemView.setOnClickListener(v -> {
            lestener.onClickFilterItem(filterValue.getValue());
        });
    }


    @Override
    public int getItemCount() {
        return filterValueList.size();
    }


    class MyFilterHolder extends RecyclerView.ViewHolder {
        ItemFilterRvBinding binding;
        public MyFilterHolder(ItemFilterRvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnFilterItemClickLestener{
        void onClickFilterItem(int value);
    }

}
