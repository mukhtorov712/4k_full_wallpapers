package uz.pdp.a4kfullwallpapers.ui.home;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import uz.pdp.a4kfullwallpapers.R;
import uz.pdp.a4kfullwallpapers.adapters.PagerAdapter;
import uz.pdp.a4kfullwallpapers.databinding.FragmentHomeBinding;
import uz.pdp.a4kfullwallpapers.databinding.ItemTabBinding;
import uz.pdp.a4kfullwallpapers.models.Category;
import uz.pdp.a4kfullwallpapers.models.ImageData;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private List<Category> categoryList;
    private PagerAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadData();


        adapter = new PagerAdapter(this,categoryList);
        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                int tabCount = binding.tabLayout.getTabCount();

                    ItemTabBinding itemTabBinding = ItemTabBinding.inflate(getLayoutInflater());
                    itemTabBinding.tv.setText(categoryList.get(tabCount).getTitle());
                    if (tabCount == 0) {
                        itemTabBinding.circle.setVisibility(View.VISIBLE);
                        itemTabBinding.tv.setTextColor(Color.WHITE);
                    } else {
                        itemTabBinding.circle.setVisibility(View.INVISIBLE);
                        itemTabBinding.tv.setTextColor(Color.parseColor("#AFADAD"));
                    }
                    tab.setCustomView(itemTabBinding.getRoot());

            }
        }).attach();

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ItemTabBinding itemTabBinding = ItemTabBinding.bind(tab.getCustomView());
                itemTabBinding.tv.setTextColor(Color.WHITE);
                itemTabBinding.circle.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ItemTabBinding itemTabBinding = ItemTabBinding.bind(tab.getCustomView());
                itemTabBinding.tv.setTextColor(Color.parseColor("#AFADAD"));
                itemTabBinding.circle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return root;
    }

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson=new Gson();

    private void loadData() {
        categoryList = new ArrayList<>();

        List<ImageData> imageData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            imageData.add(new ImageData("https://wallpapershome.ru/images/pages/pic_v/19776.jpg",
                    "Muxtorov Yoqubjon" ,"12312312", "3,2MB", "4000 × 6000", false));
            imageData.add(new ImageData("https://images.unsplash.com/photo-1619420880175-1518c6a5ce89?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDR8Sjl5clBhSFhSUVl8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                    "Muxtorov Yoqubjon" ,"12312312", "3,2MB", "4000 × 6000", false));
            imageData.add(new ImageData("https://images.unsplash.com/photo-1617864064479-f203fc7897c0?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDd8Sjl5clBhSFhSUVl8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                    "Muxtorov Yoqubjon" ,"12312312", "3,2MB", "4000 × 6000", false));
            imageData.add(new ImageData("https://images.unsplash.com/photo-1544384050-f80fac6e525a?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDMzfEo5eXJQYUhYUlFZfHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                    "Muxtorov Yoqubjon" ,"12312312", "3,2MB", "4000 × 6000", false));
            imageData.add(new ImageData("https://images.unsplash.com/photo-1624896385509-e3a8f0dbf583?ixid=MnwxMjA3fDB8MHx0b3BpYy1mZWVkfDM3fEo5eXJQYUhYUlFZfHxlbnwwfHx8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60",
                    "Muxtorov Yoqubjon" ,"12312312", "3,2MB", "4000 × 6000", false));
        }

        categoryList.add(new Category("ALL", imageData));
        categoryList.add(new Category("NEW", imageData));
        categoryList.add(new Category("ANIMALS", imageData));
        categoryList.add(new Category("TECHNOLOGY", imageData));
        categoryList.add(new Category("NATURE", imageData));
        categoryList.add(new Category("FILM", imageData));

        sharedPreferences = getActivity().getSharedPreferences("myFile", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String stringCategory = gson.toJson(categoryList);
        editor.putString("catygory",stringCategory);
        editor.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}