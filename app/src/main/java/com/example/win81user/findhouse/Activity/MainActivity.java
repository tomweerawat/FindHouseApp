package com.example.win81user.findhouse.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.example.win81user.findhouse.Common.BaseActivity;
import com.example.win81user.findhouse.Fragment.PrimaryFragment;
import com.example.win81user.findhouse.Fragment.ShowDetailFragment;
import com.example.win81user.findhouse.Fragment.UpdatesFragment;
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
                showLoadingDialog();
                Fragment fragment = new ShowDetailFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.test,fragment);
                fragmentTransaction.commit();
                break;
            case 1:
                showLoadingDialog();
                Fragment fragment1= new UpdatesFragment();
                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.test,fragment1);
                fragmentTransaction1.commit();
                break;
            case 2:
                showLoadingDialog();
                Fragment fragment2 = new PrimaryFragment();
                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.test,fragment2);
                fragmentTransaction2.commit();
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

