package com.kosmo.kosmo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kosmo.kosmo.model.dto.UserDTO;
import com.kosmo.kosmo.model.repository.ReviewRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private View loginButton, logoutButton, writeDiaryButton;
    private LinearLayout review_list_scroll_view;
    private TextView nickName;
    private TextView email;
    private TextView gender;
    private ImageView profileImage;
    private UserDTO userDTO;
    ReviewRepository reviewRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        KakaoSdk.init(this, "01a74c117cc85be268501bf56576f5cf");
        loginButton = findViewById(R.id.login);
        logoutButton = findViewById(R.id.logout);
        nickName = findViewById(R.id.nickname);
        writeDiaryButton = findViewById(R.id.btn_to_write_diary);
        review_list_scroll_view = (LinearLayout)findViewById(R.id.review_list_scroll_view);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.gender);
        profileImage = findViewById(R.id.profile);
        reviewRepository = new ReviewRepository();
        // 카카오톡이 설치되어 있는지 확인하는 메서드 , 카카오에서 제공함. 콜백 객체를 이용합.
        Function2<OAuthToken,Throwable, Unit> callback =new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            // 콜백 메서드 ,
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                Log.e("KakaoLogin","CallBack Method");
                //oAuthToken != null 이라면 로그인 성공
                if(oAuthToken!=null){
                    // 토큰이 전달된다면 로그인이 성공한 것이고 토큰이 전달되지 않으면 로그인 실패한다.
                    updateKakaoLoginUi();


                }else {
                    //로그인 실패
                    Log.e("KakaoLogin", "invoke: login fail" );
                }

                return null;
            }
        };
        // 로그인 버튼 클릭 리스너
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 해당 기기에 카카오톡이 설치되어 있는 확인
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)){
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                }else{
                    // 카카오톡이 설치되어 있지 않다면
                    Log.e("KakaoLogin","카카오톡이 설치되어 있지 않음");
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                }
            }
        });

        // 로그아읏 버튼
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        updateKakaoLoginUi();
                        return null;
                    }
                });
                SharedPreferences pref = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                // 값 제거
                editor.remove("userId");
                editor.remove("userName");
                // 변경사항 저장
                editor.commit();
            }
        });
        updateKakaoLoginUi();
        ImageButton toMain = (ImageButton)findViewById(R.id.toMain);
        toMain.setOnClickListener(view -> {
            finish();
        });
        writeDiaryButton.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), WriteDiaryActivity.class);
            startActivity(intent);
        });
    }

    @SuppressLint("CheckResult")
    private void updateReviewUi() {
        String userId = userDTO.getId();
        Single<List<Map>> reviewListSingle = reviewRepository.getReviewListById("sem50000");
        reviewListSingle.subscribe(
            reviewList-> {
                for(Map review : reviewList) {
                    Log.i("가져온 리뷰 데이터 내용", "상품명 : "+review.get("PRODUCTNAME"));
                    Log.i("가져온 리뷰 데이터 내용", "내용 : "+review.get("CONTENT"));
                    // 새로운 LinearLayout을 생성하고 속성 설정
                    LinearLayout childLinearLayout = new LinearLayout(this);
                    float dpValue = 100; // 원하는 dp 값을 입력하세요
                    float pixels = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            dpValue,
                            getResources().getDisplayMetrics()
                    );
                    float dpValue2 = 150; // 원하는 dp 값을 입력하세요
                    float pixels2 = TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP,
                            dpValue2,
                            getResources().getDisplayMetrics()
                    );
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            (int)pixels,
                            (int)pixels2
                    );
                    layoutParams.setMargins(10,0,10,0);
                    childLinearLayout.setLayoutParams(layoutParams);

                    childLinearLayout.setOrientation(LinearLayout.VERTICAL);
                    childLinearLayout.setBackgroundResource(R.drawable.report_cardview_bg);
                    childLinearLayout.setPadding(30,30,30,30);
// 날짜 TextView 생성 및 설정
                    TextView dateTextView = new TextView(this);
                    dateTextView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    // SimpleDateFormat을 사용하여 문자열을 Date로 변환
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = inputFormat.parse(review.get("R_REGIDATE").toString());
                    String formattedDate = outputFormat.format(date);
                    dateTextView.setText(formattedDate);
                    dateTextView.setTextSize(8);
                    dateTextView.setPadding(4,4,4,4);
                    // 날짜 배경 및 텍스트 컬러 설정
                    dateTextView.setBackgroundResource(R.drawable.hashtag_rounded);
                    dateTextView.setTextColor(Color.parseColor("#FB4B16"));
                    childLinearLayout.addView(dateTextView);

                    // 브랜드명 TextView 생성 및 설정
                    TextView brandTextView = new TextView(this);
                    brandTextView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    brandTextView.setMaxLines(1);
                    brandTextView.setText(review.get("PRODUCTNAME").toString());
                    brandTextView.setTextColor(getColor(R.color.black));
                    brandTextView.setTextSize(10);
                    childLinearLayout.addView(brandTextView);

                    // RatingBar 생성 및 설정
                    RatingBar ratingBar = new RatingBar(this, null, android.R.attr.ratingBarStyleSmall);
                    ratingBar.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    ratingBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#E8AA4F")));
                    ratingBar.setRating(Integer.parseInt(review.get("STARSCORE").toString()));
                    childLinearLayout.addView(ratingBar);
                    // ImageView 생성 및 설정
                    ImageView imageView = new ImageView(this);
                    Glide.with(this).load(review.get("IMGURL")).placeholder(R.drawable.no_img).into(imageView);
                    float dpWidthValue = 80; // 원하는 dp 값을 입력하세요
                    float dpHeightValue = 50; // 원하는 dp 값을 입력하세요
                    float widthPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpWidthValue, getResources().getDisplayMetrics());
                    float heightPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpHeightValue, getResources().getDisplayMetrics());
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(
                            (int)widthPixels,
                            (int)heightPixels
                    ));
                    childLinearLayout.addView(imageView);

                    // 리뷰 TextView 생성 및 설정
                    TextView reviewTextView = new TextView(this);
                    reviewTextView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    reviewTextView.setText(review.get("CONTENT").toString());
                    reviewTextView.setTextColor(Color.parseColor("#616161"));
                    reviewTextView.setTextSize(9);
                    reviewTextView.setMaxLines(2);
                    childLinearLayout.addView(reviewTextView);
                    childLinearLayout.setTag((((Double)review.get("NO")).intValue()));
                    // 동적으로 생성한 childLinearLayout을 parentLinearLayout에 추가
                    review_list_scroll_view.addView(childLinearLayout);
                    childLinearLayout.setOnClickListener(view -> {
                        showCustomDialog(view,review.get("CONTENT").toString());
                    });
                }
            },
            throwable -> {

            }
        );
    }

    private void updateKakaoLoginUi() {

        // 로그인 여부에 따른 UI 설정
        UserApiClient.getInstance().me((user, throwable) -> {
            if (user != null) {
                // 유저의 아이디
                Log.d("KakaoLogin", "invoke: id =" + user.getId());
                // 유저의 이메일
                Log.d("KakaoLogin", "invoke: email =" + user.getKakaoAccount().getEmail());
                // 유저의 닉네임
                Log.d("KakaoLogin", "invoke: nickname =" + user.getKakaoAccount().getProfile().getNickname());
                // 유저의 성별
                Log.d("KakaoLogin", "invoke: gender =" + user.getKakaoAccount().getGender());
                String userGender = user.getKakaoAccount().getGender().toString();
                // 유저의 연령대
                Log.d("KakaoLogin", "invoke: age=" + user.getKakaoAccount().getAgeRange());
                userDTO = new UserDTO();
                userDTO.setEmail(user.getKakaoAccount().getEmail());
                userDTO.setName(user.getKakaoAccount().getProfile().getNickname());
                userDTO.setGender(user.getKakaoAccount().getGender().toString());
                SharedPreferences pref = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("userId", userDTO.getEmail());
                editor.putString("userName",userDTO.getName());
                editor.commit();
                // 유저 닉네임 세팅해주기
                nickName.setText(user.getKakaoAccount().getProfile().getNickname());
                email.setText(user.getKakaoAccount().getEmail());
                if(userGender.equals("MALE")) {
                    userGender = "남성";
                }
                else userGender = "여성";
                gender.setText(userGender);
                // 유저 프로필 사진 세팅해주기
                Glide.with(profileImage).load(user.getKakaoAccount().getProfile().getThumbnailImageUrl()).circleCrop().into(profileImage);
                Log.d("KakaoLogin", "invoke: profile = "+user.getKakaoAccount().getProfile().getThumbnailImageUrl());
                updateReviewUi();
                // 로그인이 되어있으면
                loginButton.setVisibility(View.GONE);
                logoutButton.setVisibility(View.VISIBLE);
            } else {
                // 로그인 되어있지 않으면
                nickName.setText("'???'");
                profileImage.setImageResource(R.drawable.baseline_person_outline_24);
                email.setText("'???'");
                loginButton.setVisibility(View.VISIBLE);
                logoutButton.setVisibility(View.GONE);
            }
            return null;
        });
    }
    private void showCustomDialog(View parentView,String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(this).inflate(
                R.layout.review_dialog_layout,
                (LinearLayout) findViewById(R.id.review_dialog_layout));
        builder.setView(view.getRootView());
        builder.setCancelable(true);
        ((TextView)view.findViewById(R.id.review_dialog_title)).setText("해당 리뷰를 삭제하시겠습니까?");
        ((TextView)view.findViewById(R.id.review_dialog_content)).setText(content);
        String productNo = parentView.getTag().toString();
        ((Button)view.findViewById(R.id.review_dialog_button)).setText("확인");
        AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.review_dialog_cancel_button).setOnClickListener(view2 -> alertDialog.dismiss());
        view.findViewById(R.id.review_dialog_button).setOnClickListener(view1 -> {
            Call<Integer> successInteger = reviewRepository.deleteReview("sem50000",productNo);
            successInteger.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    Log.i("삭제 성공","삭제 성공");
                    Toast.makeText(getApplicationContext(), "리뷰가 삭제되었습니다", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Log.i("삭제 실패",t.getMessage());
                }
            });
        });
        if(alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }
}