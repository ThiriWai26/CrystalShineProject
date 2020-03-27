package com.chann.crystalshineproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chann.crystalshineproject.MainActivity;
import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.data.Login;
import com.chann.crystalshineproject.data.Token;
import com.chann.crystalshineproject.databinding.ActivityLoginBinding;
import com.chann.crystalshineproject.service.RetrofitService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private CompositeDisposable disposable;
    private String ph;
    private String pwd;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private EditText edtphoneno, edtpassword;
    private Button btnlogin;
    private String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        disposable = new CompositeDisposable();
        initLogin();
    }

    private void initLogin() {

        edtphoneno = findViewById(R.id.ph);
        edtpassword = findViewById(R.id.pwd);
        btnlogin = findViewById(R.id.btnlogin);
        token = pref.getString("token", null);

        if (token != null) {

            Token.MyToken.setToken(token);
            Intent intent = new Intent(getApplicationContext(), ProjectNameListActivity.class);
            intent.putExtra("Token", token);
            startActivity(intent);
            finish();
        }
        else{
            btnlogin.setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View view) {
        Log.e("onclick","ok");

        ph = edtphoneno.getText().toString();
        pwd = edtpassword.getText().toString();

//        for ( int i = 0; i < phno.length(); i++ )
//        {
//            ph += phno.charAt(i);
//        }
//        for ( int i = 0; i < passw.length(); i++)
//        {
//            pwd += passw.charAt(i);
//        }
//
        Log.e("phone_num", String.valueOf(ph));
        Log.e("password", String.valueOf(pwd));

        userLogin(ph,pwd);
    }

    private void userLogin(String ph, String pwd) {
        Log.e("userLogin","ok");
        RetrofitService.getApiEnd().userLogin(ph,pwd).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("Token", response.body().token);

                        token = response.body().token;
                        Token.MyToken.setToken(token);

                        editor.putString("token", token);
                        editor.apply();
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), ProjectNameListActivity.class);
                        intent.putExtra("Token", response.body().token);
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this,response.body().errorMessage,Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.e("failure",t.toString());
            }
        });


    }
}
