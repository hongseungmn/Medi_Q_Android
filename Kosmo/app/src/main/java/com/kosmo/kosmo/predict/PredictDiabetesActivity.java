package com.kosmo.kosmo.predict;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kosmo.kosmo.R;
import com.kosmo.kosmo.databinding.ActivityPredictDiabetesBinding;
import com.kosmo.kosmo.model.dto.request.PredictDiabetesRequest;
import com.kosmo.kosmo.model.repository.PredictModelRepository;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PredictDiabetesActivity extends AppCompatActivity implements View.OnClickListener {
    private PredictModelRepository predictModelRepository;
    private List<List> responseList;
    private ActivityPredictDiabetesBinding activityPredictDiabetesBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPredictDiabetesBinding = ActivityPredictDiabetesBinding.inflate(getLayoutInflater());
        activityPredictDiabetesBinding.predictDiabetesBtn.setOnClickListener(this);
        activityPredictDiabetesBinding.predictBackButton.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
        predictModelRepository = new PredictModelRepository();
        setContentView(activityPredictDiabetesBinding.getRoot());
    }

    @Override
    public void onClick(View view) {
        String age = ((EditText)activityPredictDiabetesBinding.predictDiabetesAge).getText().toString();
        String bmi = ((EditText)activityPredictDiabetesBinding.predictDiabetesBmi).getText().toString();
        String glucose = ((EditText)activityPredictDiabetesBinding.predictDiabetesGlucose).getText().toString();
        String bloodPress = ((EditText)activityPredictDiabetesBinding.predictDiabetesBloodpress).getText().toString();
        PredictDiabetesRequest predictDiabetesRequest = new PredictDiabetesRequest(age,bmi,glucose,bloodPress);

        Call<List> call = predictModelRepository.predictDiabetes(predictDiabetesRequest);
        call.enqueue(new Callback<List>() {
            @Override
            public void onResponse(Call<List> call, Response<List> response) {
                if (response.isSuccessful()) {
                    Log.i("com.kosmo.kosmo",response.toString());
                    Log.i("com.kosmo.kosmo",response.body().toString());
                    responseList = response.body();
                    Log.i("com.kosmo.kosmo",responseList.get(0).toString());
                    showCustomDialog();
                } else {
                    // 서버 응답 실패 처리
                    Log.i("com.kosmo.kosmo","요청 응답 실패");
                }
            }

            @Override
            public void onFailure(Call<List> call, Throwable t) {
                Log.i("t",t.getMessage());
            }
        });
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PredictDiabetesActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(PredictDiabetesActivity.this).inflate(
                R.layout.predict_dialog_layout,
                (LinearLayout) findViewById(R.id.predict_dialog_layout));
        builder.setView(view.getRootView());
        builder.setCancelable(true);
        ((TextView)view.findViewById(R.id.predict_result_dialog_title)).setText("당신의 당뇨병질환 발병 가능성은?");
        double value = (double) (responseList.get(0).get(1)) * 100;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedValue = decimalFormat.format(value);
        ((TextView)view.findViewById(R.id.predict_result_dialog_content)).setText("당뇨병 예측 모델의 결과는 \n"+formattedValue+"% 입니다.");
        ((Button)view.findViewById(R.id.predict_result_dialog_button)).setText("확인");
        AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.predict_result_dialog_button).setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityPredictDiabetesBinding = null;
    }
}
