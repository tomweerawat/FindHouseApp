package com.example.win81user.findhouse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Win81 User on 4/12/2559.
 */

public class TestFragment extends Fragment  {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main,container,false);
        test(view);
        return view;
    }
    private void  test(View view){
        int str = getActivity().getIntent().getIntExtra("position",0);
        Log.d("LLLLLLLLLLLLLLLLLL","LLLLLLLLLLll"+str);
    }

}
