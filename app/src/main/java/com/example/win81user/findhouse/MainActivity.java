package com.example.win81user.findhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.example.win81user.findhouse.Drawer.PrimaryFragment;
import com.example.win81user.findhouse.Drawer.SocialFragment;
import com.example.win81user.findhouse.Fragment.ProfileFragment;

public class MainActivity extends FragmentActivity {

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
                Fragment fragment = new SocialFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.test,fragment);
                fragmentTransaction.commit();
                break;
            case 1:
                Fragment fragment1= new PrimaryFragment();
                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.test,fragment1);
                fragmentTransaction1.commit();
                break;
            case 2:
                Fragment fragment2 = new ProfileFragment();
                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.test,fragment2);
                fragmentTransaction2.commit();
                break;
        }

//        mTextView.setText("Item Position\n " + position + "  Clicked");



    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}