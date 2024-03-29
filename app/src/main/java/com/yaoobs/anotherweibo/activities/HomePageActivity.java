package com.yaoobs.anotherweibo.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.yaoobs.anotherweibo.R;
import com.yaoobs.anotherweibo.fragments.DiscoverFragment;
import com.yaoobs.anotherweibo.fragments.HomeFragment;
import com.yaoobs.anotherweibo.fragments.ProfileFragment;

import de.greenrobot.event.EventBus;

public class HomePageActivity extends BaseActivity {

    private RadioGroup rgTab;
    private FrameLayout flContainer;
    private FragmentTabHost tabHost;
    private RadioButton rbHome;
    private RadioButton rbDiscover;
    private RadioButton rbProfile;
    private Class fragment[];
    private int menu_id = R.menu.menu_home;
    private boolean isExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getToolbar().setDisplayHomeAsUpEnabled(false).setTitle(R.string.app_name);
        fragment = new Class[]{HomeFragment.class, DiscoverFragment.class, ProfileFragment.class};
        initialize();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
    }

    private void initialize() {

        rgTab = (RadioGroup) findViewById(R.id.rgTab);
        flContainer = (FrameLayout) findViewById(R.id.flContainer);
        tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        rbHome = (RadioButton) findViewById(R.id.rbHome);
        rbDiscover = (RadioButton) findViewById(R.id.rbDiscover);
        rbProfile = (RadioButton) findViewById(R.id.rbProfile);
        tabHost.setup(getApplicationContext(), getSupportFragmentManager(), R.id.flContainer);
        for (int i = 0; i < fragment.length; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(String.valueOf(i)).setIndicator(String.valueOf(i));
            tabHost.addTab(tabSpec, fragment[i], null);
        }
        tabHost.setCurrentTab(0);
        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbHome:
                        tabHost.setCurrentTab(0);
                        menu_id = R.menu.menu_home;
                        break;
                    case R.id.rbDiscover:
                        tabHost.setCurrentTab(1);
                        menu_id = -1;
                        break;
                    case R.id.rbProfile:
                        tabHost.setCurrentTab(2);
                        menu_id = -1;
                        break;
                }
                supportInvalidateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu_id == -1) {
            menu.clear();
        } else {
            getMenuInflater().inflate(menu_id, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        EventBus.getDefault().post(item.getItemId());
        return true;
    }

    public void onBackPressed() {
        if(isExit){
            this.finish();
        }else {
            Toast.makeText(this,R.string.toast_press_again_to_exit,Toast.LENGTH_SHORT).show();
            isExit =true;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    isExit =false;
                }
            },2000);
        }
    }
}
