package com.example.win81user.findhouse.Utility;

/**
 * Created by Win81 User on 9/12/2559.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
        initTypFace();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTypFace();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initTypFace();
    }

    private void initTypFace() {
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "AlegreyaSansSC-Bold.ttf");
        setTypeface(typeface);
    }
}