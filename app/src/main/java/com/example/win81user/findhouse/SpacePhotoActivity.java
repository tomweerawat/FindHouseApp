package com.example.win81user.findhouse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;


public class SpacePhotoActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {

    public static final String EXTRA_SPACE_PHOTO = "SpacePhotoActivity.SPACE_PHOTO";

    private ImageView mImageView;
    Context context;
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_pager_image);
        Bundle bundle = getIntent().getExtras();
        String text = bundle.getString("value");
        Toast.makeText(this,"Hello"+text,Toast.LENGTH_SHORT).show();
        initview();
        TextSliderView textSliderView = new TextSliderView(this);
        textSliderView
                .image(text)

                .setOnSliderClickListener(this);


        textSliderView.bundle(new Bundle());
        mDemoSlider.addSlider(textSliderView);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(40000);
/*
   Glide.with(this)
                .load(text)
                .asBitmap()
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImageView);

*/



      /*  DetailFragment spacePhoto = getIntent().getParcelableExtra(EXTRA_SPACE_PHOTO);

        Glide.with(this)
                .load(R.mipmap.ic_launcher)
                .asBitmap()
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImageView);
*/


    }

    private void initview() {
/* mImageView = (ImageView) findViewById(R.id.imagepager);*/

        mDemoSlider = (SliderLayout) findViewById(R.id.imagepager);
        mDemoSlider.addOnPageChangeListener(this);
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

 private SimpleTarget target = new SimpleTarget<Bitmap>() {

        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {

           onPalette(Palette.from(bitmap).generate());
           mImageView.setImageBitmap(bitmap);
        }

        public void onPalette(Palette palette) {
            if (null != palette) {
                ViewGroup parent = (ViewGroup) mImageView.getParent().getParent();
                parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY));
            }
        }
    };

}
