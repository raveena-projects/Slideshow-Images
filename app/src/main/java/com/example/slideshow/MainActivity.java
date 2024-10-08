package com.example.slideshow;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private final Handler handler = new Handler();
    private int[] images = {R.drawable.ic_android_black_24dp, R.drawable.image_2, R.drawable.image_3, R.drawable.image_2, R.drawable.image_3}; // Add your images here
    private int currentIndex = 0;
    private LinearLayout dotIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        dotIndicator = findViewById(R.id.dotIndicator);
        ImageAdapter adapter = new ImageAdapter(this, images);
        viewPager.setAdapter(adapter);

        setupDotIndicators();
        autoSlide();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateDots(position);
            }
        });
    }
    private void setupDotIndicators() {
        int dotCount = Math.min(images.length, 3);
        for (int i = 0; i < dotCount; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageResource(R.drawable.dot_inactive);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dotIndicator.addView(dot);
        }
        updateDots(0);
    }

    private void updateDots(int position) {
        int activeDotIndex;

        if (position % 4 == 0 || position % 4 == 3) {
            activeDotIndex = 0;
        } else {
            activeDotIndex = (position % 3);
        }

        for (int i = 0; i < dotIndicator.getChildCount(); i++) {
            ImageView dot = (ImageView) dotIndicator.getChildAt(i);
            dot.setImageResource(i == activeDotIndex ? R.drawable.dot_active : R.drawable.dot_inactive);
        }
    }
    private void autoSlide() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentIndex == images.length) {
                    currentIndex = 0;
                }
                viewPager.setCurrentItem(currentIndex++, true);
                handler.postDelayed(this, 3000); // Change image every 3 seconds
            }
        }, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}