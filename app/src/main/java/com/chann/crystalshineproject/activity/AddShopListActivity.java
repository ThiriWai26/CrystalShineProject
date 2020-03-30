package com.chann.crystalshineproject.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chann.crystalshineproject.R;
import com.chann.crystalshineproject.adapter.SpinnerCategoryAdapter;
import com.chann.crystalshineproject.data.ShopCategoriesResponse;
import com.chann.crystalshineproject.data.ShopCategory;
import com.chann.crystalshineproject.data.ShopStoreResponse;
import com.chann.crystalshineproject.data.Token;
import com.chann.crystalshineproject.data.TownsResponse;
import com.chann.crystalshineproject.databinding.ActivityEditShopListBinding;
import com.chann.crystalshineproject.service.RetrofitService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddShopListActivity extends AppCompatActivity {

    private EditText edtshopName, edtaddress;
    private TextView tvphoto;
    private Spinner spinnerCategory, spinnerTownship, spinnerRating, spinnerGrade;

    private String imagePath = "";
    private String token = null;
    private String name;
    private String address;
    private String img;
    private String category;
    private String township;
    private String rating;
    private String grade;

    private int categoryId = 1;
    private int townshipId = -1;
    List<String> rate = new ArrayList<>();
    List<String> grades = new ArrayList<>();
    List<String> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop_list);

        init();
    }

    private void init() {

        edtshopName = findViewById(R.id.edtshopName);
        tvphoto = findViewById(R.id.edtphoto);
        edtaddress = findViewById(R.id.edtAddress);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerTownship = findViewById(R.id.spinnerTownship);
        spinnerRating = findViewById(R.id.spinnerrating);
        spinnerGrade = findViewById(R.id.spinnergrade);

        Bundle bundle = getIntent().getExtras();

        townshipId = bundle.getInt("townshipId");
        Log.e("townshipId",String.valueOf(townshipId));

        token = bundle.getString("token");
        Log.e("token", token);

        loadSpinnerCategory();
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s=   spinnerCategory.getItemAtPosition(spinnerCategory.getSelectedItemPosition()).toString();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        RetrofitService.getApiEnd().towns(token).enqueue(new Callback<TownsResponse>() {
            @Override
            public void onResponse(Call<TownsResponse> call, Response<TownsResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("response.body","success");


                    }
                }
            }

            @Override
            public void onFailure(Call<TownsResponse> call, Throwable t) {

            }
        });

//        categories.add("Eos");
//        categories.add("Mollita");
//        categories.add("Offica");
//        categories.add("Commoidi");
//        categories.add("Cum est");
//        spinnerCategory.setAdapter(adapter);


//
//        townships.add("East Murray");
//        townships.add("East Palmaburgh");
//        townships.add("South Breannamouth");
//        townships.add("Mayaville");
//        townships.add("East Nicotown");
//        ArrayAdapter adapter1 = new ArrayAdapter<String>(this,R.layout.spinner_item, townships);
//        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        spinnerTownship.setAdapter(adapter1);

        rate.add("1");
        rate.add("2");
        rate.add("3");
        rate.add("4");
        rate.add("5");
        ArrayAdapter spinnerAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item, rate);
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerRating.setAdapter(spinnerAdapter);

        grades.add("A");
        grades.add("B");
        grades.add("C");
        grades.add("D");
        grades.add("E");
        ArrayAdapter gradesAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item, grades);
        gradesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerGrade.setAdapter(gradesAdapter);

    }

    private void loadSpinnerCategory() {
        RetrofitService.getApiEnd().shopCategory(token).enqueue(new Callback<ShopCategoriesResponse>() {
            @Override
            public void onResponse(Call<ShopCategoriesResponse> call, Response<ShopCategoriesResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("response.body","success");
                        List<ShopCategory> shopCategory = response.body().shopCategory;

                        for(int i=0; i<shopCategory.size(); i++)
                        {
                            categories.add(shopCategory.get(i).name);
                            Log.e("size", String.valueOf(response.body().shopCategory.size()));
                        }
                        ArrayAdapter adapter = new ArrayAdapter<>(AddShopListActivity.this, R.layout.spinner_item, response.body().shopCategory);
                        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                        spinnerCategory.setAdapter(adapter);
                    }
                    else{
                        Log.e("response.body","fail");
                    }
                }
                else{
                    Log.e("response","fail");
                }
            }

            @Override
            public void onFailure(Call<ShopCategoriesResponse> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {

            Uri uri = data.getData();
            imagePath = getImageFilePath(data.getData());
//            tvphoto.setText(imagePath);
        }
    }

    private String getImageFilePath(Uri data) {

        File file = new File(data.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];
        Cursor cursor = getApplicationContext().getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            Log.e("imagePath",imagePath);

            String imageName = file.getName();
            Log.e("imageName", imageName);
            Log.e("imageName", file.getName());

            String filename = imagePath.substring(imagePath.lastIndexOf("/"));
            tvphoto.setText(filename);

            return  imagePath;
        }

        return data.getPath();
    }

    public void onPhoto(View view) {
        Log.e("photoclick","ok");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    2);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

        }

    }

    public void onClickShopSave(View view) {

        Log.e("onclicksave", "ok");
        RequestBody token = RequestBody.create( MediaType.parse("multipart/form-data"), Token.MyToken.getToken());
        MultipartBody.Part photo = null;
        File file = new File(imagePath);
        Log.e("originalfilesize",String.valueOf(file.length()/1024));
        File compressFile = null;

        try {
            compressFile = new Compressor(getApplicationContext()).compressToFile(file);
            Log.e("compressfilesize",String.valueOf(compressFile.length()/1024));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.e("fileimagePath",imagePath);
        RequestBody imageBody = RequestBody.create( MediaType.parse("multipart/form-data"), file);
        photo = MultipartBody.Part.createFormData("image",file.getName(),imageBody);
        Log.e("file name",file.getName());
        Log.e("api","start");

        name = edtshopName.getText().toString();
        address = edtaddress.getText().toString();
        img = tvphoto.getText().toString();
        category =spinnerCategory.getSelectedItem().toString();
        township = spinnerTownship.getSelectedItem().toString();
        rating = spinnerRating.getSelectedItem().toString();
        grade = spinnerGrade.getSelectedItem().toString();

        Log.e("name", name);
        Log.e("address", address);
        Log.e("image", file.getName());
        Log.e("category", category);
        Log.e("township", township);
        Log.e("rating", String.valueOf(rating));
        Log.e("grade", grade);
        Log.e("token", Token.MyToken.getToken());
        Log.e("categoryId", String.valueOf(categoryId));
        Log.e("townshipId", String.valueOf(townshipId));

        Log.e("bind","success");
        RetrofitService.getApiEnd().shopStore(token,categoryId,townshipId,photo,rate,grade).enqueue(new Callback<ShopStoreResponse>() {
            @Override
            public void onResponse(Call<ShopStoreResponse> call, Response<ShopStoreResponse> response) {
                Log.e("api","ok");
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("response.body","success");
                        Toast.makeText(AddShopListActivity.this, "Add Shop List Success", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext() , LoginActivity.class);
                        startActivity(intent);

                    }
                    else {
                        Log.e("upload","fail");
                        Toast.makeText(getApplicationContext(),"Update fail",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Log.e("failure","fail");
                }

            }

            @Override
            public void onFailure(Call<ShopStoreResponse> call, Throwable t) {

            }
        });



    }


}
