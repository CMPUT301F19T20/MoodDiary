package com.example.mooddiary;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class PagerActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private Button btn_skip;
    private ImageView image_indicator_zero;
    private ImageView image_indicator_one;
    private ImageView image_indicator_two;
    private Button btn_finish;
    private ImageButton mNextBtn;

    private GuidePagerAdapter mGuidePagerAdapter;

    private int lastLeftValue = 0;
    private ImageView[] indicators;
    // Current page location
    private int currentPage = 0;

    // Whether to enter the guide page for the first time
    private boolean isFirstGuide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Immersive status bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        // Change the status bar color
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black_trans80));
        setContentView(R.layout.activity_pager);
        // Enter the App for the first time and show the guide page
        isFirstGuide = Utils.readSharedSetting(PagerActivity.this, "isGuide", false);
        if (isFirstGuide) {
            gotoMain();
        }
        initView();
        initMDViewPager();
    }

    /**
     * Go to Login Activity
     */
    private void gotoMain() {
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void initMDViewPager() {
        mGuidePagerAdapter = new GuidePagerAdapter(getSupportFragmentManager());
        mNextBtn = (ImageButton) findViewById(R.id.btn_next);
        indicators = new ImageView[]{
                image_indicator_zero,
                image_indicator_one,
                image_indicator_two
        };

        mViewPager.setAdapter(mGuidePagerAdapter);
        mViewPager.setCurrentItem(currentPage);
        updateIndicators(currentPage);

        final int[] bgs = new int[]{
                R.drawable.wave1,
                R.drawable.wave2,
                R.drawable.wave3};
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mViewPager.setBackgroundResource(bgs[position]);
            }

            @Override
            public void onPageSelected(int position) {
                // Update current corner position
                currentPage = position;
                updateIndicators(currentPage);

                switch (position) {
                    case 0:
                        mViewPager.setBackgroundResource(bgs[0]);
                        break;
                    case 1:
                        mViewPager.setBackgroundResource(bgs[1]);
                        break;
                    case 2:
                        mViewPager.setBackgroundResource(bgs[2]);
                        break;
                }

                btn_skip.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
                btn_finish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
                mNextBtn.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    /**
     * Update indicator by switching between two different drawables。
     */
    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.container);
        btn_skip = (Button) findViewById(R.id.btn_skip);
        image_indicator_zero = (ImageView) findViewById(R.id.image_indicator_zero);
        image_indicator_one = (ImageView) findViewById(R.id.image_indicator_one);
        image_indicator_two = (ImageView) findViewById(R.id.image_indicator_two);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        mNextBtn = (ImageButton) findViewById(R.id.btn_next);

        btn_skip.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        mNextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_skip:
                gotoMain();
                break;
            case R.id.btn_finish:
                Utils.saveSharedSetting(PagerActivity.this, "isGuide", true);
                gotoMain();
                break;
            case R.id.btn_next:
                currentPage += 1;
                if (currentPage > 2) {
                    currentPage = 2;
                }
                mViewPager.setCurrentItem(currentPage, true);
                break;
            default:
                break;
        }
    }
}