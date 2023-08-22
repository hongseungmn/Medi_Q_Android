package com.kosmo.kosmo.myDiary;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kosmo.kosmo.R;
import com.kosmo.kosmo.databinding.FragmentRankingTabNutrientBinding;
import com.kosmo.kosmo.ranking.TabFragmentCustomDialogUtil;
import com.kosmo.kosmo.review.ReviewActivityAdapter;


public class MyDiaryCustomDialog extends Dialog {


    public MyDiaryCustomDialog(@NonNull Context context) {
        super(context);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydiary_custom_dialog);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView myDiaryHeadRecyclerView = (RecyclerView)findViewById(R.id.mydiary_head_recyclerView);
        String[] stringArray1 = getContext().getResources().getStringArray(R.array.mydiary_head_text);
        MydiaryDialogAdapter mydiaryActivityAdapter1 = new MydiaryDialogAdapter(stringArray1);
        myDiaryHeadRecyclerView.setAdapter(mydiaryActivityAdapter1);
        myDiaryHeadRecyclerView.setLayoutManager(layoutManager1);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView myDiaryBreatheRecyclerView = (RecyclerView)findViewById(R.id.mydiary_breathe_recyclerView);
        String[] stringArray2 = getContext().getResources().getStringArray(R.array.mydiary_breathe_text);
        MydiaryDialogAdapter mydiaryActivityAdapter2 = new MydiaryDialogAdapter(stringArray2);
        myDiaryBreatheRecyclerView.setAdapter(mydiaryActivityAdapter2);
        myDiaryBreatheRecyclerView.setLayoutManager(layoutManager2);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView myDiaryStomacheRecyclerView = (RecyclerView)findViewById(R.id.mydiary_stomach_recyclerView);
        String[] stringArray3 = getContext().getResources().getStringArray(R.array.mydiary_stomach_text);
        MydiaryDialogAdapter mydiaryActivityAdapter3 = new MydiaryDialogAdapter(stringArray3);
        myDiaryStomacheRecyclerView.setAdapter(mydiaryActivityAdapter3);
        myDiaryStomacheRecyclerView.setLayoutManager(layoutManager3);

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView myDiaryMentaleRecyclerView = (RecyclerView)findViewById(R.id.mydiary_mental_recyclerView);
        String[] stringArray4 = getContext().getResources().getStringArray(R.array.mydiary_mental_text);
        MydiaryDialogAdapter mydiaryActivityAdapter4 = new MydiaryDialogAdapter(stringArray4);
        myDiaryMentaleRecyclerView.setAdapter(mydiaryActivityAdapter4);
        myDiaryMentaleRecyclerView.setLayoutManager(layoutManager4);

        LinearLayoutManager layoutManager5 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView myDiaryChestRecyclerView = (RecyclerView)findViewById(R.id.mydiary_chest_recyclerView);
        String[] stringArray5 = getContext().getResources().getStringArray(R.array.mydiary_chest_text);
        MydiaryDialogAdapter mydiaryActivityAdapter5 = new MydiaryDialogAdapter(stringArray5);
        myDiaryChestRecyclerView.setAdapter(mydiaryActivityAdapter5);
        myDiaryChestRecyclerView.setLayoutManager(layoutManager5);

        LinearLayoutManager layoutManager6 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView myDiaryBodyRecyclerView = (RecyclerView)findViewById(R.id.mydiary_body_recyclerView);
        String[] stringArray6 = getContext().getResources().getStringArray(R.array.mydiary_chest_text);
        MydiaryDialogAdapter mydiaryActivityAdapter6 = new MydiaryDialogAdapter(stringArray6);
        myDiaryBodyRecyclerView.setAdapter(mydiaryActivityAdapter6);
        myDiaryBodyRecyclerView.setLayoutManager(layoutManager6);


        MyDiaryCustomDialogUtil.dialogResize(getContext(),this,(float)1.0,(float)1.0);

    }
    private void closeDialog() {
        new Handler().postDelayed(() -> dismiss(), 800);
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
    }
}
