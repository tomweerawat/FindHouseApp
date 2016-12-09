package com.example.win81user.findhouse.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.win81user.findhouse.API.RequestInterface;
import com.example.win81user.findhouse.ActivityDrawer;
import com.example.win81user.findhouse.Constants.Constants;
import com.example.win81user.findhouse.Model.ServerRequest;
import com.example.win81user.findhouse.Model.ServerResponse;
import com.example.win81user.findhouse.Model.User;
import com.example.win81user.findhouse.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText et_email,et_password;
    SharedPreferences pref;
    private Button btn_login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        pref = getSharedPreferences("userdata", Context.MODE_PRIVATE);
        btn_login = (Button)findViewById(R.id.btn_login);
        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);



        btn_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        btn_login.setEnabled(false);

       /* final ProgressDialog progressDialog = new ProgressDialog(LoginFragment.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();*/
        final MaterialDialog progressDialog = new MaterialDialog.Builder(this)
                .title("Authenticating...")
                .content("Pleasewait")
                .progress(true, 0)
                .show();
        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        // TODO: Implement your own authentication logic here.
        loginProcess(email,password);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                      /*  onLoginSuccess();*/

                        progressDialog.dismiss();
                    }
                }, 3000);


      /*  new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        onLoginSuccess();

                        progressDialog.dismiss();
                    }
                }, 3000);*/

    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {

        Toast.makeText(getBaseContext(), "Welocome "+pref.getString(Constants.NAME,""), Toast.LENGTH_LONG).show();
        btn_login.setEnabled(true);
//        finish();
        Intent i = new Intent(this,ActivityDrawer.class);
        startActivity(i);
    }


    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        btn_login.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("enter a valid email address");
            valid = false;
        } else {
            et_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            et_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            et_password.setError(null);

        }

        return valid;
    }

    private void loginProcess(String email, String password) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Log.e("OkHttpClient","connected"+client);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOGIN_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Toast.makeText(LoginFragment.this, resp.getMessage(), Toast.LENGTH_SHORT).show();

                if(resp.getResult().equals(Constants.SUCCESS)){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Constants.IS_LOGGED_IN,true);
                    editor.putString(Constants.EMAIL,resp.getUser().getEmail());
                    editor.putString(Constants.NAME,resp.getUser().getName());
                    editor.putString(Constants.USERIMAGE,resp.getUser().getUserimage());
                    editor.putString(Constants.UNIQUE_ID,resp.getUser().getUnique_id());
                    editor.commit();
                    onLoginSuccess();
                }else{
                    onLoginFailed();
                }



            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {


                Log.d(Constants.TAG,"failed");
                Toast.makeText(LoginFragment.this, "Fail", Toast.LENGTH_SHORT).show();

            }
        });
    }



}
