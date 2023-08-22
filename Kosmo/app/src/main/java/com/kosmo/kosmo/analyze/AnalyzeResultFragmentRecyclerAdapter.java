package com.kosmo.kosmo.analyze;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kosmo.kosmo.R;
import com.kosmo.kosmo.model.dto.AnalyzeResultDTO;
import com.kosmo.kosmo.model.dto.FoodListDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnalyzeResultFragmentRecyclerAdapter extends RecyclerView.Adapter<AnalyzeResultFragmentRecyclerAdapter.ViewHolder> {
    private List<AnalyzeResultDTO> analyzeResultDTOS;
    private Context context;

    // 데이터 리스트를 업데이트하는 메서드
    public void setDataList(List<AnalyzeResultDTO> newDataList) {
        analyzeResultDTOS.clear(); // 기존의 데이터 리스트를 비웁니다.
        analyzeResultDTOS.addAll(newDataList); // 새로운 데이터 리스트를 추가합니다.
        notifyDataSetChanged(); // 어댑터에게 데이터가 변경되었음을 알려줍니다.
    }
    public void clearItems() {
        analyzeResultDTOS.clear();
        notifyDataSetChanged();
    }
    public AnalyzeResultFragmentRecyclerAdapter(Context context, List<AnalyzeResultDTO> analyzeResultDTOS) {
        this.context = context;
        this.analyzeResultDTOS = analyzeResultDTOS;
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayoutCompat searchFood;
        public ViewHolder(View view) {
            super(view);
            this.searchFood = (LinearLayoutCompat) view;

        }

        public LinearLayoutCompat getView() {
            return searchFood;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.analyze_result_take_purpose_container, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayoutCompat layoutCompat = holder.getView();
        TextView takePurposeTitleTextView = (TextView)layoutCompat.findViewById(R.id.take_purpose_title);
        TextView takePurposeContent1TextView = (TextView)layoutCompat.findViewById(R.id.take_purpose_content_1);
        TextView takePurposeContent2TextView = (TextView)layoutCompat.findViewById(R.id.take_purpose_content_2);
        TextView takePurposeRecommandTitle = (TextView) layoutCompat.findViewById(R.id.take_purpose_recommand_title);
        LinearLayout takePurposeRecommandHashTag = (LinearLayout)layoutCompat.findViewById(R.id.take_purpose_recommand_hashTag);
        takePurposeTitleTextView.setText(analyzeResultDTOS.get(position).getTakePurpose());
        String content2 = "'"+analyzeResultDTOS.get(position).getTakePurpose()+"' 에 도움이 되는 기능성 원료 "+analyzeResultDTOS.get(position).getFoodList().size()+" 가지 중 "+(analyzeResultDTOS.get(position).getFoodList().size() - analyzeResultDTOS.get(position).getIngredient_list_no_report().size())+" 개 를 섭취하고 있습니다";
        takePurposeContent1TextView.setText(content2);
        takePurposeContent2TextView.setText("복용 중이신 영양제 중 '"+analyzeResultDTOS.get(position).getTakePurpose() +"' 에 도움이 되는 기능성 원료입니다");
        takePurposeRecommandTitle.setText("▪ '"+analyzeResultDTOS.get(position).getTakePurpose()+"'에 좋은 추천 기능성 원료");
        // TextView를 동적으로 생성합니다.
        for(String food : analyzeResultDTOS.get(position).getFoodList()) {
            TextView textView = new TextView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(5, 5, 5, 5);
            textView.setLayoutParams(params);
            textView.setText(food);
            textView.setPadding(10, 3, 10, 3);
            textView.setTextColor(Color.parseColor("#22b27f"));
            textView.setBackgroundResource(R.drawable.hashtag_nutrient);

            // 생성한 TextView를 LinearLayout에 추가합니다.
            takePurposeRecommandHashTag.addView(textView);
        }
        LinearLayoutCompat takePurposeInnerLinearLayout = (LinearLayoutCompat)layoutCompat.findViewById(R.id.take_purpose_inner_linearLayout);
        Set<String> keys = analyzeResultDTOS.get(position).getFoodForHelpPurpose().keySet();
        if(keys.isEmpty()) {
            Log.i("key : ", "key가 없습니다");
            TextView titleTextView = new TextView(context);
            titleTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            titleTextView.setText("▪ 현재 도움이 되는 영양소가 존재하지 않아요!");
            titleTextView.setPadding(5, 5, 5, 5);
            titleTextView.setTextColor(Color.RED);
            titleTextView.setTextSize(15);
            titleTextView.setTypeface(null, Typeface.BOLD);
            takePurposeInnerLinearLayout.addView(titleTextView);
        }
        for(String key : keys) {
            Log.i("key : ", key);
            List<String> valueList = analyzeResultDTOS.get(position).getFoodForHelpPurpose().get(key);
            // 첫 번째 TextView 생성
            TextView titleTextView = new TextView(context);
            titleTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            titleTextView.setText("▪ "+key);
            titleTextView.setPadding(5, 5, 5, 5);
            titleTextView.setTextColor(Color.parseColor("#171717"));
            titleTextView.setTextSize(15);
            titleTextView.setTypeface(null, Typeface.BOLD);

            // 루트 레이아웃에 첫 번째 TextView 추가
            takePurposeInnerLinearLayout.addView(titleTextView);
            for(String value : valueList) {
                Log.i("value : ",value);
                // 두 번째 TextView 생성
                TextView contentTextView = new TextView(context);
                contentTextView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));
                contentTextView.setText("✅ "+value);
                contentTextView.setPadding(5, 5, 5, 5);
                contentTextView.setTextColor(Color.parseColor("#22b27f"));
                contentTextView.setTextSize(12);
                contentTextView.setTypeface(null, Typeface.BOLD);

                // 루트 레이아웃에 두 번째 TextView 추가
                takePurposeInnerLinearLayout.addView(contentTextView);
            }
        }
        /*
        AnalyzeResultFragmentInnerRecyclerAdapter analyzeResultFragmentInnerRecyclerAdapter = new AnalyzeResultFragmentInnerRecyclerAdapter(context, analyzeResultDTOS.get(position).getFoodForHelpPurpose());
        ((RecyclerView)layoutCompat.findViewById(R.id.take_purpose_inner_recyclerview)).setAdapter(analyzeResultFragmentInnerRecyclerAdapter);

         */
    }


    @Override
    public int getItemCount() {
        return analyzeResultDTOS.size();
    }

}
