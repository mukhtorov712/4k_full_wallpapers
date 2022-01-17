package uz.pdp.a4kfullwallpapers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.muddzdev.quickshot.QuickShot;

import java.text.SimpleDateFormat;
import java.util.Date;

import uz.pdp.a4kfullwallpapers.ui.home.InstallType;

public class MyClass {

    // <-----=============================================================================------------->
    public void setToWallPaper(Context context, Bitmap bitmap, InstallType installType) {
        WallpaperManager wm = WallpaperManager.getInstance(context);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                switch (installType){
                    case FLAG_LOCK:
                        wm.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                        break;
                    case FLAG_SYSTEM:
                        wm.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                        break;
                    case FLAG_SYSTEMLOCK:
                        wm.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                        wm.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                        break;
                }
                Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Wallpaper not supported",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // <-----=============================================================================------------->
    public void InAnimation(LinearLayout outTop, LinearLayout outBottom, LinearLayout inTop, LinearLayout inBottom) {
        YoYo.with(Techniques.ZoomOutUp)
                .duration(500)
                .withListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        YoYo.with(Techniques.ZoomOutDown)
                                .duration(500)
                                .playOn(outBottom);
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        inBottom.setVisibility(View.VISIBLE);
                        inTop.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.ZoomInDown)
                                .duration(500)
                                .playOn(inTop);
                        YoYo.with(Techniques.ZoomInUp)
                                .duration(500)
                                .playOn(inBottom);
                    }
                })
                .playOn(outTop);
    }
    // <-----=============================================================================------------->
    public void OutAnimation(LinearLayout outTop, LinearLayout outBottom, LinearLayout inTop, LinearLayout inBottom) {
        YoYo.with(Techniques.ZoomOutUp)
                .duration(500)
                .withListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        YoYo.with(Techniques.ZoomOutDown)
                                .duration(500)
                                .playOn(outBottom);
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        YoYo.with(Techniques.ZoomInDown)
                                .duration(500)
                                .playOn(inTop);
                        YoYo.with(Techniques.ZoomInUp)
                                .duration(500)
                                .playOn(inBottom);
                    }
                }).playOn(outTop);
    }

    // <-----=============================================================================------------->
    public void saveImageToStorage(Context context, String imageName, Bitmap bitmap) {
        QuickShot.of(bitmap, context)
                .enableLogging()
                .setFilename(imageName)
                .setPath("4K Full Wallpaper")
                .toPNG()
                .setResultListener(new QuickShot.QuickShotListener() {
                    @Override
                    public void onQuickShotSuccess(String path) {
                        Toast.makeText(context, path, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onQuickShotFailed(String path, String errorMsg) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                })
                .save();
    }
    // <-----=============================================================================------------->
    public void fragmentInAnim(LinearLayout inTop, LinearLayout inBottom) {
            inTop.setVisibility(View.VISIBLE);
        inBottom.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomInLeft)
                .duration(500)
                .playOn(inTop);
        YoYo.with(Techniques.ZoomInLeft)
                .duration(500)
                .playOn(inBottom);
    }

    // <-----=============================================================================------------->
    public void fragmentOutAnim(View view, LinearLayout outTop, LinearLayout outBottom) {
//        binding.view1.setVisibility(View.VISIBLE);
//        binding.view2.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.ZoomOutLeft)
                .duration(500)
                .playOn(outTop);
        YoYo.with(Techniques.ZoomOutLeft)
                .duration(500).withListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Navigation.findNavController(view).popBackStack();
                super.onAnimationEnd(animation);
            }
        })
                .playOn(outBottom);
    }
    // <-----=============================================================================------------->
    public String generateFileName() {
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        String imageName = "MI_" + timeStamp;
        return imageName;
    }

    // <-----=============================================================================------------->
}
