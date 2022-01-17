package uz.pdp.a4kfullwallpapers.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import uz.pdp.a4kfullwallpapers.databinding.ItemImageRvBinding;
import uz.pdp.a4kfullwallpapers.models.Category;
import uz.pdp.a4kfullwallpapers.models.ImageData;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder>{

    private List<ImageData> imageDataList;
    private OnItemRvClickLestener lestener;

    public RvAdapter(List<ImageData> imageDataList, OnItemRvClickLestener lestener) {
        this.imageDataList = imageDataList;
        this.lestener = lestener;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        ItemImageRvBinding binding =
                ItemImageRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(binding);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        Picasso.get().load(imageDataList.get(position).getImageUrl()).into(holder.binding.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lestener.onItemClick(imageDataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ItemImageRvBinding binding;
        public MyViewHolder(ItemImageRvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemRvClickLestener{
      void onItemClick(ImageData imageData);
    }
}
