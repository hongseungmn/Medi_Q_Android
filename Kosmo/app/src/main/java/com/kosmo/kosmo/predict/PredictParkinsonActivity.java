package com.kosmo.kosmo.predict;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kosmo.kosmo.R;
import com.kosmo.kosmo.model.dto.response.PredictParkinsonResponse;
import com.kosmo.kosmo.model.repository.PredictModelRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PredictParkinsonActivity extends AppCompatActivity {
    private MyPaintView myView;
    int count =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_parkinson);
        PredictModelRepository predictModelRepository = new PredictModelRepository();
        myView = new MyPaintView(this);
        ((ImageButton)findViewById(R.id.predictBackButton)).setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
        ((LinearLayout) findViewById(R.id.paintLayout)).addView(myView);
        myView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        ((ImageView)findViewById(R.id.parkinson_example_img)).setClipToOutline(true);

        ((Button)findViewById(R.id.btnClear)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap capturedBitmap = captureView(myView);
                String base64EncodedImage = encodeBitmapToBase64(capturedBitmap);
                myView.mBitmap.eraseColor(Color.TRANSPARENT);
                myView.invalidate();
                try {
                    Call<PredictParkinsonResponse> call = predictModelRepository.predictParkinson(base64EncodedImage);
                    call.enqueue(new Callback<PredictParkinsonResponse>() {
                        @Override
                        public void onResponse(Call<PredictParkinsonResponse> call, Response<PredictParkinsonResponse> response) {
                            if (response.isSuccessful()) {
                                PredictParkinsonResponse serverResponse = response.body();

                                String imageBase64 = serverResponse.getImageBase64();
                                //double score = serverResponse.getScore();
                                byte[] decodedBytes = Base64.decode(imageBase64, Base64.DEFAULT);
                                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                                // 생성된 Bitmap을 ImageView에 설정하여 이미지를 표시합니다.
                                showCustomDialog(decodedBitmap);

                                // 추출한 데이터 활용
                            } else {
                                // 에러 처리 코드
                            }
                        }

                        @Override
                        public void onFailure(Call<PredictParkinsonResponse> call, Throwable t) {
                            Log.i("응답 파일 변환 에러",t.toString());
                        }
                    });
                }catch (Exception e){
                    Log.i("파일 변환 에러",e.toString());
                }
            }
        });
    }

    private static class MyPaintView extends View {
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mPaint;
        public MyPaintView(Context context) {
            super(context);
            mPath = new Path();
            mPaint = new Paint();
            mPaint.setColor(Color.BLACK);
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(20);
            mPaint.setStyle(Paint.Style.STROKE);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(mBitmap, 0, 0, null); //지금까지 그려진 내용
            canvas.drawPath(mPath, mPaint); //현재 그리고 있는 내용
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int x = (int)event.getX();
            int y = (int)event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPath.reset();
                    mPath.moveTo(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mPath.lineTo(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    mPath.lineTo(x, y);
                    mCanvas.drawPath(mPath, mPaint); //mBitmap 에 기록
                    mPath.reset();
                    break;
            }
            this.invalidate();
            return true;
        }
    }
    // 뷰(View)의 내용을 스크린샷으로 캡처하는 함수
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
        AlertDialog.Builder builder = new AlertDialog.Builder(PredictParkinsonActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(PredictParkinsonActivity.this).inflate(
                R.layout.predict_parkinson_dialog_layout,
                (LinearLayout) findViewById(R.id.predict_parkinson_dialog_layout));
        builder.setView(view.getRootView());
        builder.setCancelable(true);
        ((TextView)view.findViewById(R.id.predict_parkinson_result_dialog_title)).setText("파킨슨 예측 결과입니다");
        ((ImageView)view.findViewById(R.id.predict_parkinson_result_dialog_content)).setImageBitmap(decodedBitmap);
        ((Button)view.findViewById(R.id.predict_parkinson_result_dialog_button)).setText("확인");
        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.predict_parkinson_result_dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        if(alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }
}