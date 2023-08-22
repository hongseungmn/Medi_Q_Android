package com.kosmo.kosmo.magazine;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kosmo.kosmo.R;
import com.kosmo.kosmo.databinding.FragmentMagazineGridBinding;
import com.kosmo.kosmo.databinding.FragmentMagazineMainBinding;
import com.kosmo.kosmo.MainActivity;
import com.kosmo.kosmo.main.MainWebViewFragment;

public class MagazineFragment extends Fragment implements View.OnClickListener {
    public static MagazineFragment newInstance() {
        return new MagazineFragment();
    }
    FragmentMagazineGridBinding fragmentMagazineGridBinding;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        //fragmentMagazineGridBinding = FragmentMagazineGridBinding.inflate(getLayoutInflater());
        View rootView = inflater.inflate(R.layout.fragment_magazine_main, container, false);
        fragmentMagazineGridBinding = FragmentMagazineGridBinding.bind(rootView.findViewById(R.id.magazine_grid));
        for (int i = 0; i < fragmentMagazineGridBinding.getRoot().getChildCount(); i++) {
            View view = fragmentMagazineGridBinding.getRoot().getChildAt(i);
            view.setOnClickListener(this);
        }
        return rootView;
    }

    @Override
    public void onClick(View view) {
        int resourceId = view.getId(); // 리소스 ID를 가져옴
        Bundle bundle = new Bundle(); // 번들에 저장
        if (resourceId == R.id.magazine_01) {
            Log.i("com.kosmo.kosmo", "매거진 01을 클릭했습니다.");
            bundle.putString("magazinePage","http://192.168.0.16:9090/magazine.do?no=01");
            ((MainActivity)getActivity()).replaceFragment(MainWebViewFragment.newInstance(),bundle);
        } else if (resourceId == R.id.magazine_02) {
            Log.i("com.kosmo.kosmo", "매거진 02을 클릭했습니다.");
            bundle.putString("magazinePage","http://192.168.0.16:9090/magazine.do?no=02");
            ((MainActivity)getActivity()).replaceFragment(MainWebViewFragment.newInstance(),bundle);
        }
        else if (resourceId == R.id.magazine_03) {
            Log.i("com.kosmo.kosmo", "매거진 03을 클릭했습니다.");
            bundle.putString("magazinePage","http://192.168.0.16:9090/magazine.do?no=03");
            ((MainActivity)getActivity()).replaceFragment(MainWebViewFragment.newInstance(),bundle);
        }
        else if (resourceId == R.id.magazine_04) {
            Log.i("com.kosmo.kosmo", "매거진 04을 클릭했습니다.");
            bundle.putString("magazinePage","http://192.168.0.16:9090/magazine.do?no=04");
            ((MainActivity)getActivity()).replaceFragment(MainWebViewFragment.newInstance(),bundle);
        }
        else if (resourceId == R.id.magazine_05) {
            Log.i("com.kosmo.kosmo", "매거진 05을 클릭했습니다.");
            bundle.putString("magazinePage","http://192.168.0.16:9090/magazine.do?no=05");
            ((MainActivity)getActivity()).replaceFragment(MainWebViewFragment.newInstance(),bundle);
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentMagazineGridBinding = null;
    }
}