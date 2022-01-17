package uz.pdp.a4kfullwallpapers.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uz.pdp.a4kfullwallpapers.MainActivity;
import uz.pdp.a4kfullwallpapers.R;
import uz.pdp.a4kfullwallpapers.adapters.RvAdapter;
import uz.pdp.a4kfullwallpapers.databinding.FragmentBlankBinding;
import uz.pdp.a4kfullwallpapers.models.Category;
import uz.pdp.a4kfullwallpapers.models.ImageData;


public class BlankFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";

    private Category category;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance(Category category) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = (Category) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    private FragmentBlankBinding binding;
    private RvAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBlankBinding.inflate(inflater, container, false);
        adapter = new RvAdapter(category.getList(), new RvAdapter.OnItemRvClickLestener() {
            @Override
            public void onItemClick(ImageData imageData) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("image",imageData);
                NavOptions navOptions = ((MainActivity) getActivity()).navOptions();
                Navigation.findNavController(requireView()).navigate(R.id.photoFragment,bundle);
            }
        });

        binding.rv.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}