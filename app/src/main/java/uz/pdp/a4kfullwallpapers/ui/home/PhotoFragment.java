package uz.pdp.a4kfullwallpapers.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import uz.pdp.a4kfullwallpapers.MainActivity;
import uz.pdp.a4kfullwallpapers.MyClass;
import uz.pdp.a4kfullwallpapers.R;
import uz.pdp.a4kfullwallpapers.databinding.FragmentPhotoBinding;
import uz.pdp.a4kfullwallpapers.models.Category;
import uz.pdp.a4kfullwallpapers.models.ImageData;

import static android.content.Context.MODE_PRIVATE;


public class PhotoFragment extends Fragment {


    public PhotoFragment() {

    }

    private ImageData imageData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageData = (ImageData) getArguments().getSerializable("image");
        }
    }

    private FragmentPhotoBinding binding;
    private int clickInfo = -1;
    private MyClass myClass = new MyClass();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson=new Gson();
    private List<ImageData> imageLikeList;
    private List<Category> categoryList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences("myFile", MODE_PRIVATE);
        editor = sharedPreferences.edit();





        ((MainActivity) getActivity()).hide();

        binding = FragmentPhotoBinding.inflate(inflater, container, false);
        Picasso.get().load(imageData.getImageUrl()).into(binding.image);

        loadViewsBlur();


        myClass.fragmentInAnim(binding.view1, binding.view2); //Animation for PhotoFragment views(buttons)
        clickInfo = 0;

        binding.btnBack.setOnClickListener(v -> {
            myClass.fragmentOutAnim(requireView(), binding.view1, binding.view2);
            clickInfo = -1;
        });

        //get imageView bitmap(image.png)
        BitmapDrawable bitmapDrawable = (BitmapDrawable) binding.image.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        binding.btnShare.setOnClickListener(v -> {
            ((MainActivity) getActivity()).shareImageandText(bitmap);
        });

        binding.btnDownload.setOnClickListener(v -> {
            String imageName = myClass.generateFileName();
            myClass.saveImageToStorage(getContext(), imageName, bitmap);
        });

        binding.btnInfo.setOnClickListener(v -> {
            binding.tvUrl.setText(imageData.getImageUrl());
            binding.tvAuthor.setText(imageData.getAuthor());
            binding.tvDownload.setText(imageData.getDownload());
            binding.tvSize.setText(imageData.getSize());
            binding.tvDimention.setText(imageData.getDimension());
            infoInAnimation();
            clickInfo = 1;
        });


        binding.btnExit.setOnClickListener(v -> {
            infoOutAnimation();
            clickInfo = 0;
        });

        binding.btnInstall.setOnClickListener(v -> {
            myClass.InAnimation(binding.view1, binding.view2, binding.viewBackInstall, binding.viewInstall);
            clickInfo = 2;
        });

        binding.btnBackInstall.setOnClickListener(v -> {
            myClass.OutAnimation(binding.viewBackInstall, binding.viewInstall, binding.view1, binding.view2);

            clickInfo = 0;
        });

        binding.btnInstallHome.setOnClickListener(v -> {
            myClass.setToWallPaper(getContext(), bitmap, InstallType.FLAG_SYSTEM);
        });

        binding.btnInstallLock.setOnClickListener(v -> {
            myClass.setToWallPaper(getContext(), bitmap, InstallType.FLAG_LOCK);
        });

        binding.btnInstallAll.setOnClickListener(v -> {
            myClass.setToWallPaper(getContext(), bitmap, InstallType.FLAG_SYSTEMLOCK);
        });

        binding.btnFilter.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("image", imageData);
            NavOptions navOptions = ((MainActivity) getActivity()).navOptions();
            Navigation.findNavController(requireView()).navigate(R.id.filterFragment, bundle);
        });

        String string = sharedPreferences.getString("like", "");
        if (string.equals("")) {
            imageLikeList = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<ImageData>>() {}.getType();
            imageLikeList = gson.fromJson(string, type);
        }

        String stringCategory = sharedPreferences.getString("catygory", "");
        if (string.equals("")) {
            categoryList = new ArrayList<>();
        } else {
            Type type = new TypeToken<List<Category>>() {}.getType();
            categoryList = gson.fromJson(stringCategory, type);
        }

        imageLikeList.contains(imageData)

        Toast.makeText(getContext(), contains+"", Toast.LENGTH_SHORT).show();


        boolean liked = imageData.isLiked();
        if (contains){
            binding.btnLike.setImageResource(R.drawable.ic_photo_liked);
        }

        binding.btnLike.setOnClickListener(v -> {
            if (!liked){
                binding.btnLike.setImageResource(R.drawable.ic_photo_liked);
                imageLikeList.add(imageData);
                imageData.setLiked(true);
                Toast.makeText(getContext(), "Liked", Toast.LENGTH_SHORT).show();
            }else {
                binding.btnLike.setImageResource(R.drawable.ic_photo_like);
                imageLikeList.remove(imageData);
                imageData.setLiked(false);
                Toast.makeText(getContext(), "Unliked", Toast.LENGTH_SHORT).show();
            }
            String s = gson.toJson(imageLikeList);
            editor.putString("like",s);
            editor.commit();
        });




        //For pressing back navigate
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                switch (clickInfo) {
                    case 0:
                        myClass.fragmentOutAnim(requireView(), binding.view1, binding.view2);
                        clickInfo = -1;
                        break;
                    case 1:
                        infoOutAnimation();
                        clickInfo = 0;
                        break;
                    case 2:
                        myClass.OutAnimation(binding.viewBackInstall, binding.viewInstall, binding.view1, binding.view2);
                        clickInfo = 0;
                        break;
                }
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);


        return binding.getRoot();
    }


    // <-----=============================================================================------------->
    private void loadViewsBlur() {
        ((MainActivity) getActivity()).loadBlur(binding.back);
        ((MainActivity) getActivity()).loadBlur(binding.share);
        ((MainActivity) getActivity()).loadBlur(binding.info);
        ((MainActivity) getActivity()).loadBlur(binding.download);
        ((MainActivity) getActivity()).loadBlur(binding.install);
        ((MainActivity) getActivity()).loadBlur(binding.filter);
        ((MainActivity) getActivity()).loadBlur(binding.like);
        ((MainActivity) getActivity()).loadBlur(binding.exit);
        ((MainActivity) getActivity()).loadBlur(binding.blurDialog);
        ((MainActivity) getActivity()).loadBlur(binding.installAll);
        ((MainActivity) getActivity()).loadBlur(binding.installHome);
        ((MainActivity) getActivity()).loadBlur(binding.installLock);
        ((MainActivity) getActivity()).loadBlur(binding.backInstall);
    }


    // <-----=============================================================================------------->
    private void infoInAnimation() {
        YoYo.with(Techniques.ZoomOutUp)
                .duration(500)
                .withListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        YoYo.with(Techniques.ZoomOutDown)
                                .duration(500)
                                .playOn(binding.view2);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        binding.viewExit.setVisibility(View.VISIBLE);
                        binding.viewDialog.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.ZoomInDown)
                                .duration(500)
                                .playOn(binding.viewExit);
                        YoYo.with(Techniques.SlideInUp)
                                .duration(500)
                                .playOn(binding.viewDialog);
                    }
                })
                .playOn(binding.view1);
    }

    // <-----=============================================================================------------->
    private void infoOutAnimation() {
        YoYo.with(Techniques.ZoomOutUp)
                .duration(500)
                .withListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        YoYo.with(Techniques.SlideOutDown)
                                .duration(500)
                                .playOn(binding.viewDialog);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        YoYo.with(Techniques.ZoomInDown)
                                .duration(500)
                                .playOn(binding.view1);
                        YoYo.with(Techniques.ZoomInUp)
                                .duration(500)
                                .playOn(binding.view2);
                    }
                }).playOn(binding.viewExit);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((MainActivity) getActivity()).show();
    }
}