package com.example.mooddiary;

import android.content.Intent;
import android.os.Bundle;

import com.example.mooddiary.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import android.widget.TextView;

/**
 * This is an activity that holding HomeFragment, FriendEventFragment, FriendMapFragment,
 * MyMapFragment, SendFragment, ShareFragment.
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private final static int HOME_TO_ADD_REQUEST = 10;
    private TextView usernameText;

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
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMoodEventActivity.class);
                intent.putExtra("action_add", true);
                startActivityForResult(intent, HOME_TO_ADD_REQUEST);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
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
        usernameText = headerView.findViewById(R.id.username);
        usernameText.setText(LoginActivity.userName);
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
     * This gets acitivity results of other fragments
     * @param requestCode
     *      requestCode from fragments
     * @param resultCode
     *      resultCode from fragments
     * @param data
     *      data from fragments
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        Fragment homeFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
        homeFragment.onActivityResult(requestCode, resultCode, data);
    }
}
