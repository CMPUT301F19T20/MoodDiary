package com.example.mooddiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.mooddiary.ui.friendevent.FriendEventFragment;
import com.example.mooddiary.ui.home.HomeFragment;
import com.example.mooddiary.ui.share.ShareFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is an activity that holding HomeFragment, FriendEventFragment, FriendMapFragment,
 * MyMapFragment, SendFragment, ShareFragment.
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private final static int HOME_TO_ADD_REQUEST = 10;
    private TextView mainUsernameText;
    private Button mainLogoutButton;
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

    @Override
    public void onBackPressed() {
        this.getSupportFragmentManager().popBackStack();

    }

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
