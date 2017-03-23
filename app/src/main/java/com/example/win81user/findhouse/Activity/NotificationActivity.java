package com.example.win81user.findhouse.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.win81user.findhouse.R;

/**
 * Created by Win81 User on 13/3/2560.
 */

public class NotificationActivity extends AppCompatActivity {

    private TextView tvtest;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_activity);
        initview();
       /* Bundle bundle = getIntent().getExtras();
        String text = bundle.getString("NOTIFICATION_MSG");
        Log.e("txttest","txttest"+bundle+""+"\n"+text);*/
        checknoti();


}

    private void checknoti(){
        Intent intent=this.getIntent();
        String text;
        if(intent !=null){
           /* Bundle bundle = getIntent().getExtras();
            String text = bundle.getString("NOTIFICATION_MSG");*/
            text = intent.getStringExtra("NOTIFICATION_MSG");
            tvtest.setText(text);
        }else {
            tvtest.setText("Hi there");
        }
    }
    private void initview(){
        tvtest = (TextView) findViewById(R.id.txttest);
        toolbar = (Toolbar) findViewById(R.id.tomtoolbar);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_36dp);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotificationActivity.this, ActivityDrawer.class));
            }
        });

    }
}
