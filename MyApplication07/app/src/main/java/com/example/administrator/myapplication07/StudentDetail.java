package com.example.administrator.myapplication07;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 2016/10/22.
 */
public class StudentDetail extends ActionBarActivity implements View.OnClickListener  {
    private Button btnSave,btnDelete;
    private Button btnClose;
    private EditText etName;
    private EditText etEmail;
    private EditText etAge;
    private TextView tvgrade;
    private int _student_id=0;

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),960 X 960。
    private static int output_X = 960;
    private static int output_Y = 960;

    private ImageView headImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);



        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etAge = (EditText) findViewById(R.id.etAge);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        tvgrade = (TextView) findViewById(R.id.tvgrade);

        _student_id =0;
        Intent intent = getIntent();
        _student_id =intent.getIntExtra("student_Id", 0);
        StudentRepo repo = new StudentRepo(this);
        Student student = new Student();
        student = repo.getStudentById(_student_id);
        double all = repo.getStudentGrade(_student_id);

        etAge.setText(String.valueOf(student.age));
        etName.setText(student.name);
        etEmail.setText(student.email);
       tvgrade.setText(""+all);


        headImage = (ImageView) findViewById(R.id.imageView);

        Button buttonLocal = (Button) findViewById(R.id.buttonLocal);
        assert buttonLocal != null;
        buttonLocal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                choseHeadImageFromGallery();
            }
        });

        Button buttonCamera = (Button) findViewById(R.id.buttonCamera);
        assert buttonCamera != null;
        buttonCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                choseHeadImageFromCameraCapture();
            }
        });
    }




    @Override
    public void onClick(View v) {
        if(v==findViewById(R.id.btnSave)){
            StudentRepo repo=new StudentRepo(this);
            Student student=new Student();
            student.age=Integer.parseInt(etAge.getText().toString());
            student.email=etEmail.getText().toString();
            student.name=etName.getText().toString();
            student.student_ID=_student_id;

            if(_student_id==0){
                _student_id=repo.insert(student);
                Toast.makeText(this,"New Student Insert",Toast.LENGTH_SHORT).show();
            }else{
                repo.update(student);
                Toast.makeText(this,"Student Record updated",Toast.LENGTH_SHORT).show();
            }
        }else if (v== findViewById(R.id.btnDelete)){
            StudentRepo repo = new StudentRepo(this);
            repo.delete(_student_id);
            Toast.makeText(this, "Student Record Deleted", Toast.LENGTH_SHORT);
            finish();
        }else if (v== findViewById(R.id.btnClose)){
            finish();
        }
    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {

        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            headImage.setImageBitmap(photo);
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }
}
