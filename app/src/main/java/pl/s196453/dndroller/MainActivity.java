package pl.s196453.dndroller;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    private ActionBar topbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topbar = getSupportActionBar();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        topbar.setTitle(R.string.title_roll);
        fragmentLoad(new RollFragment());

        //MyAppDatabase database = Room.databaseBuilder(this, MyAppDatabase.class, "profiledb") //not needed fragments call an instance of db
        //        .build();
    }

    //method to load fragments
    private void fragmentLoad(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null); //passes data to save from fragment to back stack
        transaction.commit();
    }

    //changing fragments and title of action bar
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_roll:
                    topbar.setTitle(R.string.title_roll);
                    fragment = new RollFragment();
                    fragmentLoad(fragment);
                    return true;
                case R.id.navigation_profile:
                    topbar.setTitle(R.string.title_profile);
                    fragment = new ProfileFragment();
                    fragmentLoad(fragment);
                    return true;
                case R.id.navigation_information:
                    topbar.setTitle(R.string.title_information);
                    fragment = new InfoFragment();
                    fragmentLoad(fragment);
                    return true;
            }
            return false;
        }
    };


}
