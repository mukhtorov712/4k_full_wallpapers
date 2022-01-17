package uz.pdp.a4kfullwallpapers.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import uz.pdp.a4kfullwallpapers.models.Category;
import uz.pdp.a4kfullwallpapers.ui.home.BlankFragment;

public class PagerAdapter extends FragmentStateAdapter {

    private List<Category> categoryList;

    public PagerAdapter(@NonNull @NotNull Fragment fragment, List<Category> categoryList) {
        super(fragment);
        this.categoryList = categoryList;
    }


    @Override
    public Fragment createFragment(int position) {
        return BlankFragment.newInstance(categoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
