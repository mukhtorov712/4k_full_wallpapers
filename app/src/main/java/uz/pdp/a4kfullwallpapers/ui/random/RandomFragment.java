package uz.pdp.a4kfullwallpapers.ui.random;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import uz.pdp.a4kfullwallpapers.databinding.FragmentPopularBinding;
import uz.pdp.a4kfullwallpapers.databinding.FragmentRandomBinding;

public class RandomFragment extends Fragment {

    private FragmentRandomBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentRandomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}