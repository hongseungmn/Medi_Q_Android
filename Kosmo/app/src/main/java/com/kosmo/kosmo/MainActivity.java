package com.kosmo.kosmo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.sdk.common.KakaoSdk;
import com.kosmo.kosmo.analyze.AnalyzeFragment;
import com.kosmo.kosmo.databinding.ActivityMainBinding;
import com.kosmo.kosmo.food.FoodSharedViewModel;
import com.kosmo.kosmo.magazine.MagazineFragment;
import com.kosmo.kosmo.main.MainFragment;
import com.kosmo.kosmo.main.MainWebViewFragment;
import com.kosmo.kosmo.model.dto.UserDTO;
import com.kosmo.kosmo.predict.PredictFragment;
import com.kosmo.kosmo.ranking.RankingFragment;

public class MainActivity extends AppCompatActivity {

    MagazineFragment magazineFragment;
    AnalyzeFragment analyzeFragment;
    MainFragment mainFragment;
    RankingFragment rankingFragment;
    PredictFragment predictFragment;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        magazineFragment = new MagazineFragment();
        analyzeFragment = new AnalyzeFragment();
        mainFragment = new MainFragment();
        rankingFragment = new RankingFragment();
        predictFragment = new PredictFragment();
        binding.toMyInfoBtn.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            startActivity(intent);
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.containers, mainFragment).commit();
        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationview);
        navigationBarView.setSelectedItemId(R.id.main);
        navigationBarView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.magazine) {
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, magazineFragment).commit();
                return true;
            }
            else if(item.getItemId() == R.id.main) {
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, mainFragment).commit();
                return true;
            }
            else if(item.getItemId() == R.id.analyze) {
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, analyzeFragment).commit();
                return true;
            }
            else if(item.getItemId() == R.id.ranking) {
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, rankingFragment).commit();
                return true;
            }
            else if(item.getItemId() == R.id.predict) {
                getSupportFragmentManager().beginTransaction().replace(R.id.containers, predictFragment).commit();
                return true;
            }
            return false;
        });


    }
    public void replaceFragment(Fragment fragment, Bundle bundle) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.containers, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }

    @Override
    public void onBackPressed() {
        // 현재 보여지는 Fragment를 가져오기
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.containers);

        // 현재 보여지는 Fragment가 MainWebViewFragment 경우 처리
        if (fragment instanceof MainWebViewFragment) {
            MainWebViewFragment mainWebViewFragment = (MainWebViewFragment) fragment;
            mainWebViewFragment.onBackPressed(); // YourFragment의 뒤로가기 키 처리 메서드 호출
        } else {
            super.onBackPressed(); // 그 외의 경우 기본 뒤로가기 동작 실행
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        if((pref!=null) && (pref.contains("userId"))){
            String text = pref.getString("userId", "");
            Log.i("kosmo","저장된 사용자의 아이디"+text);
        }
    }


}