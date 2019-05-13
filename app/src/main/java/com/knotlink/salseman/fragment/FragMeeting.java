package com.knotlink.salseman.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.MainActivity;
import com.knotlink.salseman.api.Api;
import com.knotlink.salseman.api.ApiClients;
import com.knotlink.salseman.model.ModelLead;
import com.knotlink.salseman.model.ModelMeeting;
import com.knotlink.salseman.storage.SharedPrefManager;
import com.knotlink.salseman.utils.CheckPermission;
import com.knotlink.salseman.utils.Constant;
import com.knotlink.salseman.utils.CustomLog;
import com.knotlink.salseman.utils.CustomToast;
import com.knotlink.salseman.utils.DateUtils;
import com.knotlink.salseman.utils.SetImage;
import com.knotlink.salseman.utils.SetTitle;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;

public class FragMeeting extends Fragment {
    private ModelMeeting tModels;
    private Context tContext;
    private SharedPrefManager tSharedPrefManager;
    String strVendorType;

    @BindView(R.id.et_meeting_orgName)
    protected EditText etMeetingOrgName;
    @BindView(R.id.et_meeting_contactName)
    protected EditText etMeetingContactName;
    @BindView(R.id.et_meeting_contactNumber)
    protected EditText etMeetingContactNumber;
    @BindView(R.id.et_meeting_address)
    protected EditText etMeetingAddress;
    @BindView(R.id.et_meeting_email)
    protected EditText etMeetingEmail;
    @BindView(R.id.et_meeting_remarks)
    protected EditText etMeetingComments;
    @BindView(R.id.btn_meeting_submit)
    protected Button btnMeetingSubmit;
    @BindView(R.id.iv_upload_meeting)
    protected ImageView ivUploadMeeting;
    @BindView(R.id.rg_meeting)
    protected RadioGroup rgMeeting;

  @BindView(R.id.tv_meeting_time)
    protected TextView tvMeetingTime;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_meeting, container, false);
        ButterKnife.bind(this, view);

        initFrag();
        return view;
    }
    public void initFrag(){
        tContext = getContext();
        tSharedPrefManager = new SharedPrefManager(tContext);
        SetTitle.tbTitle("Meeting", getActivity());

        rgMeeting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_meeting_customer:
                        CustomToast.toastTop(getActivity(), "Customer Selected");
                        etMeetingOrgName.setHint("Organisation Name*");
                        strVendorType = "Customer";
                        break;
                    case R.id.rb_meeting_prospect:
                        CustomToast.toastTop(getActivity(), "Prospect Selected");
                        etMeetingOrgName.setHint("Prospect Name*");
                        strVendorType = "Prospect";
                        break;
                }
            }
        });

        tvMeetingTime.setText(DateUtils.getCurrentTime());

    }


    @OnClick(R.id.iv_upload_meeting)
    public void onUploadMeetingClicked(View view){
        showPictureDialog();
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {"Photo Gallery", "Camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    private void choosePhotoFromGallery() {
        if (CheckPermission.isReadStorageAllowed(getContext())) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, Constant.GALLERY);
            return;
        }
        CheckPermission.requestStoragePermission(getActivity());
    }
    private void takePhotoFromCamera() {
        if (CheckPermission.isCameraAllowed(getContext())) {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, Constant.CAMERA);
            return;
        }
        CheckPermission.requestCameraPermission(getActivity());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == Constant.GALLERY) {
            if (data != null) {
                SetImage.setGalleryImage(tContext, ivUploadMeeting, data);
            }

        } else if (requestCode == Constant.CAMERA) {

            SetImage.setCameraImage(ivUploadMeeting, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constant.STORAGE_PERMISSION_CODE) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "You  denied the permission...", Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.btn_meeting_submit)
    public void meetingSubmit(View view){
        callApi();
    }


    private void callApi(){
        String strUserId = tSharedPrefManager.getUserId();
        String strOrgName = etMeetingOrgName.getText().toString().trim();
        String strContactName = etMeetingContactName.getText().toString().trim();
        String strContactNumber = etMeetingContactNumber.getText().toString().trim();
        String strAddress = etMeetingAddress.getText().toString().trim();
        String strEmail = etMeetingEmail.getText().toString().trim();
        String strRemarks = etMeetingComments.getText().toString().trim();
        Api api = ApiClients.getApiClients().create(Api.class);
        Call<ModelMeeting> call = api.uploadMeeting(strUserId, strVendorType, strOrgName, strContactName, strContactNumber, strAddress, strEmail, strRemarks);
        call.enqueue(new Callback<ModelMeeting>() {
            @Override
            public void onResponse(Call<ModelMeeting> call, Response<ModelMeeting> response) {
                tModels = response.body();
                if (!tModels.getError()){
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                    getFragmentManager().beginTransaction().remove(FragMeeting.this).commit();
                    getFragmentManager().beginTransaction().replace(R.id.container_main, new FragDashboard()).commit();
                }
                else {
                    CustomToast.toastTop(getActivity(), tModels.getMessage());
                }
            }
            @Override
            public void onFailure(Call<ModelMeeting> call, Throwable t) {
                CustomLog.d(Constant.TAG, "Not Responding Meeting : "+t);

            }
        });
    }
}
