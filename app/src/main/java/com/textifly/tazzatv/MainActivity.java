package com.textifly.tazzatv;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.textifly.tazzatv.Activity.LoginActivity;
import com.textifly.tazzatv.Adapter.DrawerAdapter;
import com.textifly.tazzatv.Model.DrawerModel;
import com.textifly.tazzatv.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private NavOptions.Builder navBuilder;

    private ActionBarDrawerToggle mDrawerToggle;

    List<DrawerModel> modelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.lightgrey));
        }
        setDefaultView();
        setDrawerMenu();

        binding.navLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.fade_in_animation,R.anim.fade_out_animation);
            }
        });

        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer();

            }
        });
    }

    private void setDrawerMenu() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvMenu.setLayoutManager(layoutManager);

        modelList = new ArrayList<>();

        modelList.add(new DrawerModel("Home"));
        DrawerAdapter drawerMenuAdapter = new DrawerAdapter(modelList, this);
        binding.rvMenu.setAdapter(drawerMenuAdapter);

        drawerMenuAdapter.setListenerDrawerMenu(new OnDrawerMenuListener() {
            @Override
            public void onDrawerMenuClick(int pos) {
                Bundle bundle = new Bundle();
                switch (pos) {
                    case 0:
                        Log.e("Pos", "= 0");
                        openDrawer();
                }
            }
        });
    }

    private void setDefaultView() {
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.appBarMain.bottomNav, navController);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navBuilder = new NavOptions.Builder();
        navBuilder.setEnterAnim(R.anim.fade_in_animation)
                .setExitAnim(R.anim.fade_out_animation)
                .setPopEnterAnim(R.anim.fade_in_animation)
                .setPopExitAnim(R.anim.fade_out_animation);

        /*mDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout,
                R.drawable.ic_dashboard_black_24dp, R.string.title_home) {*/
        mDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout,
                R.drawable.home, R.string.title_home) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
                Log.e("onDrawerClosed= ", "Closed");
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here
                Log.e("onDrawerOpened= ", "Closed");
            }
        };
        binding.drawerLayout.addDrawerListener(mDrawerToggle);
    }

    public void openDrawer() {
        if (!binding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            binding.drawerLayout.openDrawer(Gravity.LEFT);
        } else {
            binding.drawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    public interface OnDrawerMenuListener {
        void onDrawerMenuClick(int pos);
    }
}