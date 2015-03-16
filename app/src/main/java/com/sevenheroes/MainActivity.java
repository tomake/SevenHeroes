package com.sevenheroes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;

import com.sevenheroes.fragment.MainPageFragment;
import com.sevenheroes.fragment.NavigationDrawerFragment;
import com.sevenheroes.util.InjectView;
import com.sevenheroes.util.Injector;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @InjectView(R.id.drawer_layout)
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Injector.get(this).inject();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,mDrawerLayout);

        Fragment gamePageFragment = new MainPageFragment(mNavigationDrawerFragment);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.container , gamePageFragment)
                .commit();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {}


}
