package com.example.win81user.findhouse.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.win81user.findhouse.Constants.Constants;
import com.example.win81user.findhouse.Fragment.LoginFragment;
import com.example.win81user.findhouse.R;

public class Activity1 extends FragmentActivity {

    SharedPreferences pref;
    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);
        pref = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        Log.d("hhh","hhh"+"\t"+pref);
        initFragment();
    }

    private void initFragment(){
//        Fragment fragment = new Fragment();
        if(pref.getBoolean(Constants.IS_LOGGED_IN,false)){
//            fragment = new ProfileFragment();
            Intent i = new Intent(Activity1.this,ActivityDrawer.class);
            startActivity(i);
        }else {
//            fragment = new LoginFragment();
            Intent i = new Intent(Activity1.this,LoginFragment.class);
            startActivity(i);
        }

//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_frame,fragment);
//        ft.commit();
    }

}
