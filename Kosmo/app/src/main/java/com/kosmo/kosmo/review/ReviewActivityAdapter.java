package com.kosmo.kosmo.review;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kosmo.kosmo.R;
import com.kosmo.kosmo.analyze.AnalyzeReportFragment_1_type_Adaptor;


public class ReviewActivityAdapter extends RecyclerView.Adapter<ReviewActivityAdapter.ViewHolder> implements View.OnClickListener {
    @NonNull
    @Override
    public ReviewActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_type_recyclerview, parent, false);

        return new ReviewActivityAdapter.ViewHolder(view);
    }

    public ReviewActivityAdapter(String[] localDataSet) {
        this.localDataSet = localDataSet;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewActivityAdapter.ViewHolder holder, int position) {
        holder.getTextView().setTag("타입태그번호 : "+position);
        holder.getTextView().setTag(R.id.is_selected,false);
        holder.getTextView().setOnClickListener(this);
        holder.getTextView().setText(localDataSet[position]);
    }
    private String[] localDataSet;
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    @Override
    public void onClick(View view) {
        Log.i("유형이 클릭되었습니다","이미지가 클릭되었습니다"+view.getTag());
        TextView selectedView = view.findViewById(R.id.report_type_recyclerView_textview);
        boolean isSelected = (boolean) selectedView.getTag(R.id.is_selected);
        if(isSelected) {
            selectedView.setBackgroundResource(R.drawable.report_textview_bg);
            int color = ContextCompat.getColor(view.getContext(), R.color.whiteGrey);
            selectedView.setTextColor(color);
            selectedView.setTag(R.id.is_selected,false);
        }
        else {
            selectedView.setBackgroundResource(R.drawable.report_textview_selected_bg);
            int color = ContextCompat.getColor(view.getContext(), R.color.orange);
            selectedView.setTextColor(color);
            selectedView.setTag(R.id.is_selected,true);
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            LinearLayoutCompat cardView = (LinearLayoutCompat) view;
            this.textView = cardView.findViewById(R.id.report_type_recyclerView_textview);
        }

        public TextView getTextView() {
            return textView;
        }

    }
}
