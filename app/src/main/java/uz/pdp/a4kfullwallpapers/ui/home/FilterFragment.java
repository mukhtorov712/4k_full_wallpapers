package uz.pdp.a4kfullwallpapers.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.devs.sketchimage.SketchImage;
import com.squareup.picasso.Picasso;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import java.util.ArrayList;
import java.util.List;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;
import uz.pdp.a4kfullwallpapers.MainActivity;
import uz.pdp.a4kfullwallpapers.MyClass;
import uz.pdp.a4kfullwallpapers.adapters.FilterRvAdapter;
import uz.pdp.a4kfullwallpapers.databinding.FragmentFilterBinding;
import uz.pdp.a4kfullwallpapers.models.FilterValue;
import uz.pdp.a4kfullwallpapers.models.ImageData;

public class FilterFragment extends Fragment {


    private ImageData imageData;

    public FilterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageData = (ImageData) getArguments().getSerializable("image");
        }
    }

    private FragmentFilterBinding binding;
    private MyClass myClass = new MyClass();
    private int clickInfo = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);

        myClass.fragmentInAnim(binding.navigation, binding.filter);
        clickInfo = 0;

//
//        Picasso.get().load(imageData.getImageUrl()).into(binding.image);
//
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) binding.image.getDrawable();
//        Bitmap bitmap = bitmapDrawable.getBitmap();
//
//        PhotoFilter photoFilter = new PhotoFilter();
//        Bitmap two = photoFilter.two(getContext(), bitmap);
//
//        binding.image.setImageBitmap(two);


//        PhotoEditorView photoEditorView = binding.photoEditorView;
//
//        PhotoEditor mPhotoEditor = new PhotoEditor.Builder(getContext(), photoEditorView)
//                .setPinchTextScalable(true)
//                .build();
//        mPhotoEditor.setFilterEffect(PhotoFilter.BRIGHTNESS);
//
//
//        BitmapDrawable drawable = (BitmapDrawable)binding.photoEditorView.getSource().getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//
//        binding.image.setImageBitmap(bitmap);




        loadBlur();

        Picasso.get().load(imageData.getImageUrl()).into(binding.image);

        BitmapDrawable drawable = (BitmapDrawable) binding.image.getDrawable();
        Bitmap bmOriginal = drawable.getBitmap();


        SketchImage sketchImage = new SketchImage.Builder(getContext(), bmOriginal).build();
        Bitmap imageAs1 = sketchImage.getImageAs(0, 80);
        binding.image.setImageBitmap(imageAs1);
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Bitmap bmProcessed = sketchImage.getImageAs(0, progress);
                binding.image.setImageBitmap(bmProcessed);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        int progress = binding.seekBar.getProgress();

        List<FilterValue> filterValues = loadFilter(bmOriginal);
        FilterRvAdapter adapter = new FilterRvAdapter(filterValues, value -> {
            binding.seekBar.setProgress(80);
            Bitmap imageAs = sketchImage.getImageAs(value, progress);
            binding.image.setImageBitmap(imageAs);
            binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Bitmap bmProcessed = sketchImage.getImageAs(value, progress);
                    binding.image.setImageBitmap(bmProcessed);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        });



        binding.filterRv.setAdapter(adapter);

        binding.btnDownload.setOnClickListener(v -> {
            Bitmap filterBitmap = loadFilterBitmap();
            String imageName = myClass.generateFileName();
            myClass.saveImageToStorage(getContext(),imageName, filterBitmap);
        });


        binding.btnDone.setOnClickListener(v -> {
            myClass.InAnimation(binding.navigation, binding.filter, binding.view2Top, binding.view2Bottom);
            clickInfo = 1;
        });

        binding.btnBack2.setOnClickListener(v -> {
            myClass.OutAnimation(binding.view2Top, binding.view2Bottom, binding.navigation, binding.filter);
            clickInfo = 0;
        });

        binding.btnInstall.setOnClickListener(v -> {
            myClass.InAnimation(binding.view2Top, binding.view2Bottom, binding.intallTop, binding.installBottom);
            clickInfo = 2;
        });

        binding.btnBackInstall.setOnClickListener(v -> {
            myClass.OutAnimation(binding.intallTop, binding.installBottom, binding.view2Top, binding.view2Bottom);
            clickInfo = 1;
        });

        binding.btnInstallLock.setOnClickListener(v -> {
            Bitmap filterBitmap = loadFilterBitmap();
            myClass.setToWallPaper(getContext(), filterBitmap, InstallType.FLAG_LOCK);
        });

        binding.btnInstallHome.setOnClickListener(v -> {
            Bitmap filterBitmap = loadFilterBitmap();
            myClass.setToWallPaper(getContext(), filterBitmap, InstallType.FLAG_SYSTEM);
        });

        binding.btnInstallAll.setOnClickListener(v -> {
            Bitmap filterBitmap = loadFilterBitmap();
            myClass.setToWallPaper(getContext(), filterBitmap, InstallType.FLAG_SYSTEMLOCK);
        });

        binding.btnExit.setOnClickListener(v -> {
            myClass.fragmentOutAnim(requireView(), binding.navigation, binding.filter);
            clickInfo = -1;
        });


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                switch (clickInfo) {
                    case 0:
                        myClass.fragmentOutAnim(requireView(), binding.navigation, binding.filter);
                        clickInfo = -1;
                        break;
                    case 1:
                        myClass.OutAnimation(binding.view2Top, binding.view2Bottom, binding.navigation, binding.filter);
                        clickInfo = 0;
                        break;
                    case 2:
                        myClass.OutAnimation(binding.intallTop, binding.installBottom, binding.view2Top, binding.view2Bottom);
                        clickInfo = 0;
                        break;
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return binding.getRoot();
    }

    private void loadBlur() {
        ((MainActivity) getActivity()).loadBlur(binding.done);
        ((MainActivity) getActivity()).loadBlur(binding.exit);
        ((MainActivity) getActivity()).loadBlur(binding.back2);
        ((MainActivity) getActivity()).loadBlur(binding.backInstall);
        ((MainActivity) getActivity()).loadBlur(binding.download);
        ((MainActivity) getActivity()).loadBlur(binding.install);
        ((MainActivity) getActivity()).loadBlur(binding.installAll);
        ((MainActivity) getActivity()).loadBlur(binding.installHome);
        ((MainActivity) getActivity()).loadBlur(binding.installLock);
    }

    private Bitmap loadFilterBitmap(){
        BitmapDrawable drawable1 = (BitmapDrawable) binding.image.getDrawable();
        Bitmap filterBitmap = drawable1.getBitmap();
        return filterBitmap;
    }



    private List<FilterValue> loadFilter(Bitmap bmOriginal) {
        List<FilterValue> filterValues = new ArrayList<>();
        filterValues.add(new FilterValue(bmOriginal, "Sketch", 0));
        filterValues.add(new FilterValue(bmOriginal, "Sketch", 1));
        filterValues.add(new FilterValue(bmOriginal, "Sketch", 2));
        filterValues.add(new FilterValue(bmOriginal, "Sketch", 3));
        filterValues.add(new FilterValue(bmOriginal, "Sketch", 4));
        filterValues.add(new FilterValue(bmOriginal, "Sketch", 5));
        filterValues.add(new FilterValue(bmOriginal, "Sketch", 6));
        filterValues.add(new FilterValue(bmOriginal, "Sketch", 7));
        filterValues.add(new FilterValue(bmOriginal, "Sketch", 8));
        filterValues.add(new FilterValue(bmOriginal, "Sketch", 9));
        return filterValues;
    }
}