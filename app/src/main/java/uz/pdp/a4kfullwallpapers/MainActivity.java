package uz.pdp.a4kfullwallpapers;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import java.io.File;
import java.io.FileOutputStream;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

import uz.pdp.a4kfullwallpapers.databinding.ActivityMainBinding;
import uz.pdp.a4kfullwallpapers.ui.home.InstallType;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private BlurView blurView;
    private NavController navController;
    private DrawerLayout drawer;
    private int clickInfo = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadBlurMain(binding.appBarMain.contentMain.blurView);

        setSupportActionBar(binding.appBarMain.toolbar);

        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        navigationView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_popular,
                R.id.nav_random,
                R.id.nav_liked,
                R.id.nav_history,
                R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);

        binding.appBarMain.contentMain.circleHome.setVisibility(View.VISIBLE);

        binding.appBarMain.contentMain.blurHome.setOnClickListener(v -> {
            Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.nav_home);
            setInvisible(0);
        });

        binding.appBarMain.contentMain.blurPopular.setOnClickListener(v -> {
            Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.nav_popular);
            setInvisible(1);
        });

        binding.appBarMain.contentMain.blurRandom.setOnClickListener(v -> {
            Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.nav_random);
            setInvisible(2);
        });

        binding.appBarMain.contentMain.blurLiked.setOnClickListener(v -> {
            Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.nav_liked);
            setInvisible(3);
        });
    }




    //    <-----=================================================================-------->


    public NavOptions navOptions (){
        return new NavOptions.Builder()
                .setEnterAnim(R.anim.enter)
                .setExitAnim(R.anim.exit)
                .setPopEnterAnim(R.anim.pop_enter)
                .setPopExitAnim(R.anim.pop_exit).build();
    }

    //    <-----=================================================================-------->

    private void setInvisible(int count) {

        binding.appBarMain.contentMain.circleHome.setVisibility(View.GONE);
        binding.appBarMain.contentMain.circlePopular.setVisibility(View.GONE);
        binding.appBarMain.contentMain.circleRandom.setVisibility(View.GONE);
        binding.appBarMain.contentMain.circleLiked.setVisibility(View.GONE);

        switch (count) {
            case 0:
                binding.appBarMain.contentMain.circleHome.setVisibility(View.VISIBLE);
                break;
            case 1:
                binding.appBarMain.contentMain.circlePopular.setVisibility(View.VISIBLE);
                break;
            case 2:
                binding.appBarMain.contentMain.circleRandom.setVisibility(View.VISIBLE);
                break;
            case 3:
                binding.appBarMain.contentMain.circleLiked.setVisibility(View.VISIBLE);
                break;
        }
    }

    //    <-----=================================================================-------->

    public void hide() {
        binding.appBarMain.contentMain.blurCardView.setVisibility(View.GONE);
        binding.appBarMain.toolbar.setVisibility(View.GONE);
    }

    public void show() {
        binding.appBarMain.contentMain.blurCardView.setVisibility(View.VISIBLE);
        binding.appBarMain.toolbar.setVisibility(View.VISIBLE);
    }

    //    <-----=================================================================-------->

    public void loadBlur(BlurView blurView) {
        float radius = 20f;
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();
        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(false)
                .setOverlayColor(Color.parseColor("#40000000"))
                .setHasFixedTransformationMatrix(false);
    }

//    <-----=================================================================-------->
    public void loadBlurMain(BlurView blurView) {
        float radius = 12f;
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();

        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(this))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true);
    }

    //    <-----=================================================================-------->


    public void shareImageandText(Bitmap bitmap) {
        Uri uri = getmageToShare(bitmap);
        Intent intent = new Intent(Intent.ACTION_SEND);

        // putting uri of image to be shared
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        // adding text to share
        intent.putExtra(Intent.EXTRA_TEXT, "Amazing wallpaper for your device\nPowered by @mukhtorov712");

        // Add subject Here
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");

        // setting type to image
        intent.setType("image/png");

        // calling startactivity() to share
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

    // Retrieving the url to share
    public Uri getmageToShare(Bitmap bitmap) {
        File imagefolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "shared_image.png");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(this, "com.anni.shareimage.fileprovider", file);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }

    //    <-----=================================================================-------->

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
        switch (id) {
            case R.id.nav_home:
                setInvisible(0);
                break;
            case R.id.nav_popular:
                setInvisible(1);
                break;
            case R.id.nav_random:
                setInvisible(2);
                break;
            case R.id.nav_liked:
                setInvisible(3);
                break;
        }
        //This is for maintaining the behavior of the Navigation view
        NavigationUI.onNavDestinationSelected(item, navController);
        //This is for closing the drawer after acting on it
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}