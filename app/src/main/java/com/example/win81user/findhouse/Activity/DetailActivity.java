package com.example.win81user.findhouse.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.win81user.findhouse.Model.ItemModel;
import com.example.win81user.findhouse.Model.Property;
import com.example.win81user.findhouse.R;

import java.util.ArrayList;

import retrofit2.Retrofit;

/**
 * Created by Win81 User on 18/5/2560.
 */

public class DetailActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,ViewPagerEx.OnPageChangeListener {
    Retrofit retrofit;
    private ItemModel itemModel;
    private Toolbar toolbar;
    private SliderLayout mDemoSlider;
    private TextView description, txtdetail, tv_message, fakedata,rent,price,contactname;
    private ImageView img;
    private ArrayList<Property> data;
    Property property;
    private String srtUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testdetail);

        property = getIntent().getExtras().getParcelable("ItemPosition");
        Log.e("ner","ner"+property);
        initViews();
        adddata();
        loadimage();
    }


    private void adddata() {
        txtdetail.setText(property.getPropertyname());
        description.setText(property.getDescription());
        rent.setText(property.getStatus());
        contactname.setText(property.getLocation());
        price.setText(property.getPrice());
    }


    private void initViews(){

        txtdetail = (TextView) findViewById(R.id.propname);
        price = (TextView) findViewById(R.id.price);
        description = (TextView)findViewById(R.id.detail);
        contactname = (TextView) findViewById(R.id.contactname);
        rent = (TextView) findViewById(R.id.rent);
        toolbar = (Toolbar) findViewById(R.id.toolbardd);
        toolbar.setNavigationIcon(R.drawable.ic_navigate_before_black_36dp);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mDemoSlider = (SliderLayout) findViewById(R.id.slider1);
        mDemoSlider.addOnPageChangeListener(this);
    }
    private void loadimage(){
        TextSliderView textSliderView = new TextSliderView(this);
        srtUrl = property.getImage();
        textSliderView
                .description("")
                .image(srtUrl)
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .setOnSliderClickListener(this);
        textSliderView.bundle(new Bundle());
        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {

                Intent i = new Intent(DetailActivity.this, SpacePhotoActivity.class);
                i.putExtra("value",srtUrl);
                startActivity(i);

            }
        });

        mDemoSlider.addSlider(textSliderView);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);

    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
