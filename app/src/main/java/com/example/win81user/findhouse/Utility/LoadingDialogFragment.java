/*
Copyright 2016 Nextzy

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.example.win81user.findhouse.Utility;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.win81user.findhouse.R;


/**
 * Created by Akexorcist on 8/3/15 AD.
 */
public class LoadingDialogFragment extends DialogFragment {

    public static LoadingDialogFragment newInstance() {
        return new LoadingDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        setCancelable(false);

        return inflater.inflate(R.layout.dialog_loading, container);
    }

    public static class Builder {
        public Builder() { }

        public LoadingDialogFragment build() {
            return LoadingDialogFragment.newInstance();
        }
    }
}
