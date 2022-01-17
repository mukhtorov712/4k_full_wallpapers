package uz.pdp.a4kfullwallpapers.ui.liked;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import uz.pdp.a4kfullwallpapers.databinding.FragmentLikedBinding;
import uz.pdp.a4kfullwallpapers.databinding.FragmentPopularBinding;

public class LikedFragment extends Fragment {

    private FragmentLikedBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentLikedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}