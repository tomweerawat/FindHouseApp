package com.example.win81user.findhouse.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.example.win81user.findhouse.Common.BaseActivity;
import com.example.win81user.findhouse.Fragment.DetailFragment;
import com.example.win81user.findhouse.Fragment.DetailFragment1;
import com.example.win81user.findhouse.Fragment.DetailFragment2;
import com.example.win81user.findhouse.Fragment.DetailFragment4;
import com.example.win81user.findhouse.Fragment.PrimaryFragment;
import com.example.win81user.findhouse.R;
import com.example.win81user.findhouse.Utility.NextzyUtil;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mTextView;

        mTextView = (TextView) findViewById(R.id.textview_01);

        Intent intent = getIntent();
        int position = intent.getIntExtra("ItemPosition", -1);


        switch (position){
            case 0:
              /*  showLoadingDialog();
                Fragment fragment = new ShowDetailFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.test,fragment);
                fragmentTransaction.commit();*/
                showLoadingDialog();
                Fragment fragment = new DetailFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.test,fragment);
                fragmentTransaction.commit();
                break;
            case 1:
                showLoadingDialog();
                Fragment fragment1= new DetailFragment1();
                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.test,fragment1);
                fragmentTransaction1.commit();
                break;
            case 2:
                showLoadingDialog();
                Fragment fragment2 = new DetailFragment2();
                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.test,fragment2);
                fragmentTransaction2.commit();
                /*Intent i = new Intent(this, MapsActivity.class);
                startActivity(i);*/
                break;
            case 3:
                showLoadingDialog();
                Fragment fragment3 = new DetailFragment4();
                FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.test,fragment3);
                fragmentTransaction3.commit();
                break;
            case 4:
                showLoadingDialog();
                Fragment fragment4 = new PrimaryFragment();
                FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction4.replace(R.id.test,fragment4);
                fragmentTransaction4.commit();
                /*Intent i = new Intent(this, MapsActivity.class);
                startActivity(i);*/
                break;
        }


        NextzyUtil.launch(1000, new NextzyUtil.LaunchCallback() {
            @Override
            public void onLaunchCallback() {
                dismissDialog();

            }
        });
    }


//        mTextView.setText("Item Position\n " + position + "  Clicked");



    }

