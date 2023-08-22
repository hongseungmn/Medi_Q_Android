package com.kosmo.kosmo.analyze;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kosmo.kosmo.R;

public class AnalyzeReportFragment_1_type_Adaptor extends RecyclerView.Adapter<AnalyzeReportFragment_1_type_Adaptor.ViewHolder> implements View.OnClickListener {

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

    private String[] localDataSet;

    public AnalyzeReportFragment_1_type_Adaptor(String[] dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.report_type_recyclerview, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setTag("타입태그번호 : "+position);
        viewHolder.getTextView().setTag(R.id.is_selected,false);
        viewHolder.getTextView().setOnClickListener(this);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}
