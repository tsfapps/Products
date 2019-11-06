package com.knotlink.salseman.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.user.ModelUser;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.GetImei;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ModelUser tModels;
    private SharedPrefManager tSharedPrefManager;

    @BindView(R.id.et_phone_login)
    protected EditText et_phone;
    @BindView(R.id.et_password_login)
    protected EditText et_password;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        tSharedPrefManager = new SharedPrefManager(this);
    }
    @OnClick(R.id.btn_login)
    public void onButtonClick(View view) {
        submitBtn();
    }
    private void submitBtn() {
        String mPhoneNo = et_phone.getText().toString().trim();
        String mPass = et_password.getText().toString().trim();
        if (mPhoneNo.isEmpty()) {
            et_phone.setError("Enter phone no");
        } else if (mPass.isEmpty()) {
            et_password.setError("Enter the password");
        } else {
            Api api = ApiClients.getApiClients().create(Api.class);
            Call<ModelUser> call = api.getUserDetail(GetImei.getDeviceIMEI(LoginActivity.this, this), mPhoneNo, mPass);
            call.enqueue(new Callback<ModelUser>() {
                @Override
                public void onResponse(Call<ModelUser> call, Response<ModelUser> response) {
                    tModels = response.body();
                    if (!tModels.getError()) {
                        String strUserId = tModels.getUser().getUserId();
                        String strName = tModels.getUser().getUserName();
                        String strAddress = tModels.getUser().getUserAddress();
                        String strPhone = tModels.getUser().getUserPhone();
                        String strEmail = tModels.getUser().getUserEmail();
                        String strAdhar = tModels.getUser().getUserAdharNo();
                        String strAsmId = tModels.getUser().getUserAsmId();
                        String strSmId = tModels.getUser().getUserSmId();
                        String strDob = tModels.getUser().getUserDob();
                        String strDoj = tModels.getUser().getUserDoj();
                        String strImage = tModels.getUser().getUserImage();
                        String strVehicleNo = tModels.getUser().getUserVehicleNo();
                        String strUserType = tModels.getUser().getUserType();

                        tSharedPrefManager.setUserData(strUserId, strName, strAddress, strPhone,
                        strEmail, strAdhar, strAsmId, strSmId,
                        strDob, strDoj, strImage, strVehicleNo, strUserType);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), tModels.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ModelUser> call, Throwable t) {
                CustomLog.e(Constant.TAG, "Not responding : " + t);
                }
            });
        }
    }




}
