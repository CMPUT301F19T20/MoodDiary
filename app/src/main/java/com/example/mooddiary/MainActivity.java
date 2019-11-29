package com.example.mooddiary;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is an activity that holding HomeFragment, FriendEventFragment, FriendMapFragment,
 * MyMapFragment, SendFragment, ShareFragment.
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView mainUsernameText;
    private Button mainLogoutButton;
    private Switch mainSwitchSwitch;
    private NavigationView navigationView;
    private long firstBackPressedTime;


    /**
     * This creates the view of Main activity
     * @param savedInstanceState
     *      If the activity is being re-initialized after previously being shut down
     *      then this Bundle contains the data it most recently supplied in.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_friend_event, R.id.nav_my_map,
                R.id.nav_friend_map, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View headerView = navigationView.getHeaderView(0);
        mainUsernameText = headerView.findViewById(R.id.username);
        mainUsernameText.setText(LoginActivity.userName);

        mainLogoutButton = navigationView.findViewById(R.id.main_logout_button);
        mainLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mainSwitchSwitch = navigationView.findViewById(R.id.main_switch_mode_switch);
        int currentMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        mainSwitchSwitch.setChecked(currentMode == Configuration.UI_MODE_NIGHT_YES);
        mainSwitchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Save the night mode status, Application can judge whether to set night mode according to this value
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

                    // ThemeConfig theme configuration, here just save the boolean value of whether it is night mode
                    NightModeConfig.getInstance().setNightMode(getApplicationContext(),true);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    NightModeConfig.getInstance().setNightMode(getApplicationContext(),false);
                }
                ViewGroup viewGroup = findViewById(R.id.nav_view);
                viewGroup.invalidate();
            }
        });
    }

    /**
     *  This deals with the action of choosing to navigate Up within
     *  application's activity hierarchy from the action bar by the user.
     * @return
     *      This is true if Up navigation completed successfully and MainActivity was finished,
     *      false otherwise.
     */
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * This allows you to get back according the back botton
     */
    @Override
    public void onBackPressed() {
        this.getSupportFragmentManager().popBackStack();

    }

    /**
     * This is Called when a key up event has occurred.
     * @param keyCode The value in event.getKeyCode().
     * @param event  Description of the key event.
     * @return  If you handled the event, return true.
     *          If you want to allow the event to be handled by the next receiver,
     *          return false.
     */

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (navigationView.getCheckedItem().getItemId() == R.id.nav_home) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                long secondBackPressedTime = System.currentTimeMillis();
                if (secondBackPressedTime - firstBackPressedTime > 2000) {
                    Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                    firstBackPressedTime = secondBackPressedTime;
                    return true;
                } else {
                    moveTaskToBack(true);
                }
            }
        }
        return super.onKeyUp(keyCode, event);
    }

}
