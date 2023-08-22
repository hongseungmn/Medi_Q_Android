package com.kosmo.kosmo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kosmo.kosmo.model.dto.response.GoogleResponse;
import com.kosmo.kosmo.model.repository.PredictModelRepository;
import com.kosmo.kosmo.myDiary.ProgressDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCameraActivity extends AppCompatActivity {
    private Bitmap bitmap;
    private String titleText;
    ProgressDialog customProgressDialog;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_camera);
        imageView = findViewById(R.id.picture_image);
        Button picButton = (Button) findViewById(R.id.take_picture);

        picButton.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activityResultPicture.launch(intent);
        });
        ((ImageButton)findViewById(R.id.camera_back_button)).setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
        //로딩창 객체 생성
        customProgressDialog = new ProgressDialog(this);
        //로딩창을 투명하게
        //customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button srcButton = (Button) findViewById(R.id.search_food);
        srcButton.setOnClickListener(view -> {
            customProgressDialog.show();
            Bitmap capturedBitmap = captureView(imageView);
            PredictModelRepository predictModelRepository = new PredictModelRepository();
            String base64EncodedImage = encodeBitmapToBase64(capturedBitmap);
            imageView.invalidate();
            try {
                Call<GoogleResponse> call = predictModelRepository.googleOCR(base64EncodedImage);
                call.enqueue(new Callback<GoogleResponse>() {
                    @Override
                    public void onResponse(Call<GoogleResponse> call, Response<GoogleResponse> response) {
                        if (response.isSuccessful()) {
                            GoogleResponse serverResponse = response.body();
                            String imageBase64 = serverResponse.getBase64();
                            titleText = serverResponse.getTitleText();
                            titleText = titleText.replaceAll("\\s+", " ");
                            byte[] decodedBytes = Base64.decode(imageBase64, Base64.DEFAULT);
                            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                            showCustomDialog(decodedBitmap);
                            imageView.setImageBitmap(decodedBitmap);
                            Log.i("com.SearchCameraActivity","titleText : "+titleText);
                            // 생성된 Bitmap을 ImageView에 설정하여 이미지를 표시합니다.
                            // 추출한 데이터 활용
                            customProgressDialog.dismiss();
                        } else {
                            // 에러 처리 코드
                            try {
                                String errorBody = response.errorBody().string();
                                Log.e("API Error", errorBody);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<GoogleResponse> call, Throwable t) {
                        Log.i("응답 파일 변환 에러",t.toString());
                    }
                });
            }catch (Exception e){
                Log.i("파일 변환 에러",e.toString());
            }
        });
    }

    ActivityResultLauncher<Intent> activityResultPicture = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //결과 OK, 데이터 null 아니면
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        Bundle extras = result.getData().getExtras();
                        bitmap = (Bitmap) extras.get("data");
                        imageView.setImageBitmap(bitmap);
                    }
                }
            });
    public static Bitmap captureView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    // 스크린샷을 Base64로 인코딩하는 함수
    public static String encodeBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void showCustomDialog(Bitmap decodedBitmap) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchCameraActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(SearchCameraActivity.this).inflate(
                R.layout.search_dialog_layout,
                (LinearLayout) findViewById(R.id.search_dialog_layout));
        builder.setView(view.getRootView());
        builder.setCancelable(true);
        ((TextView)view.findViewById(R.id.search_result_dialog_title)).setText("검색 결과");
        EditText editText = (EditText)view.findViewById(R.id.search_result_dialog_content);
        editText.setText(titleText);
        ((ImageView)view.findViewById(R.id.search_image_result_dialog_content)).setImageBitmap(decodedBitmap);
        ((Button)view.findViewById(R.id.search_result_dialog_button)).setText("확인");
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.search_result_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FoodTableActivity.class);
                Log.i("com.searchCamera","editText : "+editText.getText());
                intent.putExtra("foodName",editText.getText().toString());
                startActivity(intent);
            }
        });
        if(alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }
}