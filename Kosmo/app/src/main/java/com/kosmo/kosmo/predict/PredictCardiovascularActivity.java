package com.kosmo.kosmo.predict;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kosmo.kosmo.R;
import com.kosmo.kosmo.databinding.ActivityPredictCardiovascularBinding;
import com.kosmo.kosmo.model.dto.request.PredictCardiovascularRequest;
import com.kosmo.kosmo.model.repository.PredictModelRepository;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PredictCardiovascularActivity extends AppCompatActivity implements View.OnClickListener {
    private PredictModelRepository predictModelRepository;
    private List<List> responseList;
    private ActivityPredictCardiovascularBinding activityPredictCardiovascularBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPredictCardiovascularBinding = ActivityPredictCardiovascularBinding.inflate(getLayoutInflater());
        activityPredictCardiovascularBinding.predictCardiovascularBtn.setOnClickListener(this);
        activityPredictCardiovascularBinding.predictBackButton.setOnClickListener(view -> {
            onBackPressed();
            finish();
        });
        predictModelRepository = new PredictModelRepository();
        setContentView(activityPredictCardiovascularBinding.getRoot());
    }

    @Override
    public void onClick(View view) {
        RadioGroup genderRadioGroup = (RadioGroup)activityPredictCardiovascularBinding.predictCardiovascularGenderRadiogroup;
        int selectedIdGender = genderRadioGroup.getCheckedRadioButtonId();
        String gender="";
        if (selectedIdGender != -1) {
            RadioButton selectedRadioButton = findViewById(selectedIdGender);
            gender = selectedRadioButton.getTag().toString();
        } else {}
        RadioGroup smokeRadioGroup = (RadioGroup)activityPredictCardiovascularBinding.predictCardiovascularGenderRadiogroup;
        int selectedIdSmoke = genderRadioGroup.getCheckedRadioButtonId();
        String smoke="";
        if (selectedIdSmoke != -1) {
            RadioButton selectedRadioButton = findViewById(selectedIdSmoke);
            smoke = selectedRadioButton.getTag().toString();
        } else {}
        RadioGroup drinkRadioGroup = (RadioGroup)activityPredictCardiovascularBinding.predictCardiovascularGenderRadiogroup;
        int selectedIdDrink = genderRadioGroup.getCheckedRadioButtonId();
        String drink="";
        if (selectedIdDrink != -1) {
            RadioButton selectedRadioButton = findViewById(selectedIdDrink);
            drink = selectedRadioButton.getTag().toString();
        } else {}
        String height = ((EditText)activityPredictCardiovascularBinding.predictCardiovascularHeight).getText().toString();
        String weight = ((EditText)activityPredictCardiovascularBinding.predictCardiovascularWeight).getText().toString();
        String bloodPress_hi = ((EditText)activityPredictCardiovascularBinding.predictCardiovascularBloodpressHigh).getText().toString();
        String bloodPress_lo = ((EditText)activityPredictCardiovascularBinding.predictCardiovascularBloodpressLow).getText().toString();
        String col = ((EditText)activityPredictCardiovascularBinding.predictCardiovascularCol).getText().toString();
        String glucose = ((EditText)activityPredictCardiovascularBinding.predictCardiovascularGlu).getText().toString();
        String age = ((EditText)activityPredictCardiovascularBinding.predictCardiovascularAge).getText().toString();
        int intCol = Integer.parseInt(col);
        col = (intCol <= 200 ? 1 : (intCol <= 240 ? 2 : 3))+"";
        int intGlu = Integer.parseInt(glucose);
        glucose = (intGlu <= 100 ? 1 : (intGlu <= 125 ? 2 : 3))+"";
        Log.i("입력된 값들 확인 : ",gender+height+weight+bloodPress_hi+bloodPress_lo+col+glucose+smoke+drink+age);
        PredictCardiovascularRequest predictCardiovascularRequest = new PredictCardiovascularRequest(gender,height,weight,bloodPress_hi,bloodPress_lo,col,glucose,smoke,drink,age);

        Call<List> call = predictModelRepository.predictCardiovascular(predictCardiovascularRequest);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(PredictCardiovascularActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(PredictCardiovascularActivity.this).inflate(
                R.layout.predict_dialog_layout,
                (LinearLayout) findViewById(R.id.predict_dialog_layout));
        builder.setView(view.getRootView());
        builder.setCancelable(true);
        ((TextView)view.findViewById(R.id.predict_result_dialog_title)).setText("당신의 심혈관질환 발병 가능성은?");
        double value = (double) (responseList.get(0).get(1)) * 100;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String formattedValue = decimalFormat.format(value);
        ((TextView)view.findViewById(R.id.predict_result_dialog_content)).setText("심혈관질환 예측 모델의 결과는 \n"+formattedValue+"% 입니다.");
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
        activityPredictCardiovascularBinding = null;
    }
}
