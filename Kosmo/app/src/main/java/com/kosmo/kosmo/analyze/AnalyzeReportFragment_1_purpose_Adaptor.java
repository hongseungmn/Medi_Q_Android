package com.kosmo.kosmo.analyze;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kosmo.kosmo.R;

public class AnalyzeReportFragment_1_purpose_Adaptor extends RecyclerView.Adapter<AnalyzeReportFragment_1_purpose_Adaptor.ViewHolder> implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        Log.i("이미지가 클릭되었습니다","이미지가 클릭되었습니다"+view.getTag());
        ImageView selectedView = view.findViewById(R.id.report_purpose_recyclerView_imageview);
        boolean isSelected = (boolean) selectedView.getTag(R.id.is_selected);
        if(isSelected) {
            selectedView.setBackgroundResource(R.drawable.report_cardview_bg);
            selectedView.setTag(R.id.is_selected,false);
            TextView selectedTextView = view.findViewById(R.id.report_purpose_recyclerView_textview);

        }
        else {
            selectedView.setBackgroundResource(R.drawable.report_cardview_selected_bg);
            selectedView.setTag(R.id.is_selected,true);
            TextView selectedTextView = view.findViewById(R.id.report_purpose_recyclerView_textview);

        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            LinearLayoutCompat cardView = (LinearLayoutCompat) view;
            this.imageView = cardView.findViewById(R.id.report_purpose_recyclerView_imageview);
            // Define click listener for the ViewHolder's View
            this.textView = cardView.findViewById(R.id.report_purpose_recyclerView_textview);
        }

        public TextView getTextView() {
            return textView;
        }
        public ImageView getImageView() {
            return imageView;
        }
    }

    private String[] stringTakePurpose;
    private TypedArray imageTakePurposeResIds;

    public AnalyzeReportFragment_1_purpose_Adaptor(String[] stringTakePurpose, TypedArray imageTakePurposeResIds) {
        this.stringTakePurpose = stringTakePurpose;
        this.imageTakePurposeResIds = imageTakePurposeResIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.report_purpose_recyclerview, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getImageView().setTag("이미지태그번호 : "+position);
        viewHolder.getImageView().setTag(R.id.is_selected,false);
        viewHolder.getImageView().setImageResource(imageTakePurposeResIds.getResourceId(position,-1));
        viewHolder.getTextView().setText(stringTakePurpose[position]);
        viewHolder.getImageView().setOnClickListener(this);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return stringTakePurpose.length;
    }
}
