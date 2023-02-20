package com.project.coffeeshop.views;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import com.project.coffeeshop.R;
import com.project.coffeeshop.adapter.ImageSliderAdapter;
import com.project.coffeeshop.services.ClickListenerImage;
import com.project.coffeeshop.models.ImageData;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton;
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup;


public class MainActivity extends AppCompatActivity implements ClickListenerImage {


    private ViewPager2 viewPager;
    int currentpage = 0;
    private final Handler handler = new Handler();
    private Runnable runnable;
    Timer timer;
    private ThemedButton coffe;
    private ThemedButton ncoffe;
    private ThemedButton extras;
    private ThemedButton machine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        viewPager = findViewById(R.id.view_pagerid);
        DotsIndicator dotsIndicator = findViewById(R.id.dots_indicator);
        ThemedToggleButtonGroup buttonGroup = findViewById(R.id.btn_item);
        coffe = findViewById(R.id.mn_coffee);
        ncoffe = findViewById(R.id.mn_ncoffee);
        extras = findViewById(R.id.mn_extras);
        machine = findViewById(R.id.mn_mccoffee);
        List<ImageData> imageData = new ArrayList<>();
        imageData.add(new ImageData(R.drawable.barista_pic));
        imageData.add(new ImageData(R.drawable.bean_pic));
        imageData.add(new ImageData(R.drawable.extras_pic));
        viewPager.setAdapter(new ImageSliderAdapter(imageData, viewPager, this));
        ImageSliderAdapter adapter = new ImageSliderAdapter(imageData, viewPager, this);

        viewPager.setAdapter(adapter);
        dotsIndicator.attachTo(viewPager);

        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);


        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        viewPager.setPageTransformer(compositePageTransformer);
        runnable = () -> {
            if (currentpage == imageData.size()) {
                currentpage = 0;
            }
            viewPager.setCurrentItem(currentpage++, true);
        };


        buttonGroup.selectButton(coffe.getId());
        replacefragment(new coffeefr());
        buttonGroup.setOnSelectListener(themedButton -> {
            if (coffe.isSelected()){
                replacefragment(new coffeefr());
            }else if(ncoffe.isSelected()){
               replacefragment(new noncoffeefr());
            }else if(extras.isSelected()){
                Toast.makeText(MainActivity.this,"Jancs",Toast.LENGTH_SHORT).show();
            }else if (machine.isSelected()){
                Toast.makeText(MainActivity.this,"Coks",Toast.LENGTH_SHORT).show();
            }
            return kotlin.Unit.INSTANCE;
        });
    }

    private void replacefragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }



    @Override
    protected void onStart() {
        super.onStart();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },200,4000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onPictureClicked(int position) {
        switch (position){
            case 0:
                Intent image = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com"));
                startActivity(image);
                break;
            case 1:
                Intent image2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com"));
                startActivity(image2);
                break;
            case 2:
                Intent image3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com"));
                startActivity(image3);
                break;
            default:
        }
    }

}