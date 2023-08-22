package com.kosmo.kosmo.analyze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kosmo.kosmo.R;
import com.kosmo.kosmo.model.dto.FoodListDTO;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeReportFragment_2_confirm_Adaptor extends RecyclerView.Adapter<AnalyzeReportFragment_2_confirm_Adaptor.ViewHolder> {
    private List<FoodListDTO> itemList = new ArrayList<>();
    private Context context;

    // 데이터 리스트를 업데이트하는 메서드
    public void setDataList(List<FoodListDTO> newDataList) {
        itemList.clear(); // 기존의 데이터 리스트를 비웁니다.
        itemList.addAll(newDataList); // 새로운 데이터 리스트를 추가합니다.
        notifyDataSetChanged(); // 어댑터에게 데이터가 변경되었음을 알려줍니다.
    }
    public void clearItems() {
        itemList.clear();
        notifyDataSetChanged();
    }
    public AnalyzeReportFragment_2_confirm_Adaptor(Context context) {
        this.context = context;
    }
    private static OnItemClickListener itemClickListener;

    //인터페이스 선언
    public interface OnItemClickListener{
        //클릭시 동작할 함수
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayoutCompat searchFood;
        public ViewHolder(View view) {
            super(view);
            this.searchFood = (LinearLayoutCompat) view;
            this.searchFood.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION){
                    //동작 호출 (onItemClick 함수 호출)
                    if(itemClickListener != null){
                        itemClickListener.onItemClick(v, pos);
                    }
                }
            });
        }

        public LinearLayoutCompat getSearchFoodView() {
            return searchFood;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.autocomplate_result_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayoutCompat layoutCompat = holder.getSearchFoodView();
        ImageView productImageView = layoutCompat.findViewById(R.id.report_search_result_imageview);
        TextView productCompanyTextView = layoutCompat.findViewById(R.id.report_search_result_productCompany);
        TextView productNameTextView = layoutCompat.findViewById(R.id.report_search_result_productName);
        Glide.with(context).load(itemList.get(position).getImgURL()).placeholder(R.drawable.no_img).into(productImageView);
        productCompanyTextView.setText(itemList.get(position).getCompany());
        productNameTextView.setText(itemList.get(position).getProductName());
        productImageView.setTag(R.id.is_selected,false);
        productImageView.setTag(R.id.productNo,itemList.get(position).getNo());
        if(AnalyzeReportFragment_2.selectedProductNoSet.contains(productImageView.getTag(R.id.productNo))){
            productImageView.setTag(R.id.is_selected, true);
            productImageView.setBackgroundResource(R.drawable.report_search_result_selected_bg);
            int selectedcolor = ContextCompat.getColor(context, R.color.orange);
            productNameTextView.setTextColor(selectedcolor);
        }
        else {
            productImageView.setTag(R.id.is_selected, false);
            productImageView.setBackgroundResource(R.drawable.report_search_result_bg);
            int selectedcolor = ContextCompat.getColor(holder.itemView.getContext(), R.color.black);
            productNameTextView.setTextColor(selectedcolor);
        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
