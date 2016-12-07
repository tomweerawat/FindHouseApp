package com.example.win81user.findhouse;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.win81user.findhouse.Constants.Constants;
import com.example.win81user.findhouse.Fragment.LoginFragment;
import com.example.win81user.findhouse.Fragment.ProfileFragment;

public class Activity1 extends FragmentActivity {

    private SharedPreferences pref;
    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);
        pref = getPreferences(0);
        Log.d("bbbbbbbbb","bbbbbbbbbb"+pref);
        initFragment();
    }

    private void initFragment(){
        Fragment fragment = new Fragment();
        if(pref.getBoolean(Constants.IS_LOGGED_IN,false)){
            fragment = new ProfileFragment();
//            Intent i = new Intent(Activity1.this,ActivityDrawer.class);
//            startActivity(i);
        }else {
            fragment = new LoginFragment();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,fragment);
        ft.commit();

//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_frame,fragment);
//        ft.commit();
    }

}
