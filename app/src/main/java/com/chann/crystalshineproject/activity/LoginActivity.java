package com.chann.crystalshineproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private CompositeDisposable disposable;
    private String ph = null;
    private String pwd = null;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        disposable = new CompositeDisposable();
    }

    public void onLogin(View view) {

        binding.progressBar.setVisibility(View.VISIBLE);

        ph = binding.ph.getText().toString();
        pwd = binding.pwd.getText().toString();
        if (ph.isEmpty()) {
            binding.ph.setError("Enter Phone Number");
        } else {
            binding.pwd.setError("Enter Password");
        }

        Disposable subscribe = RetrofitService.getApiEnd().userLogin(ph, pwd)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult, this::handleError);

        disposable.add(subscribe);
    }

    private void handleResult(Login login) {
        if (login.isSuccess()) {
            binding.progressBar.setVisibility(View.GONE);
            Token.token = login.getToken();
            Log.e("user token ", login.getToken());

            editor.putString("ph", ph);
            editor.putString("pwd",pwd);
            editor.apply();
            editor.commit();
            startActivity(MainActivity.getInstance(getApplicationContext()));
            finish();

        }
        else {
            binding.progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"Invalid User", Toast.LENGTH_LONG).show();
        }
    }

    private void handleError(Throwable t) {
        Log.e("loginFailure:", t.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

}
