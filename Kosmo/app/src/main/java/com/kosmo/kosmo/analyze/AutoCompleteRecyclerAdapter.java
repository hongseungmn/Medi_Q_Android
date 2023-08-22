package com.kosmo.kosmo.analyze;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kosmo.kosmo.R;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteRecyclerAdapter extends RecyclerView.Adapter<AutoCompleteRecyclerAdapter.ViewHolder> {
    private List<String> itemList = new ArrayList<>();

    // 데이터 리스트를 업데이트하는 메서드
    public void setDataList(List<String> newDataList) {
        itemList.clear(); // 기존의 데이터 리스트를 비웁니다.
        itemList.addAll(newDataList); // 새로운 데이터 리스트를 추가합니다.
        notifyDataSetChanged(); // 어댑터에게 데이터가 변경되었음을 알려줍니다.
    }
    public void clearItems() {
        itemList.clear();
        notifyDataSetChanged();
    }

    public AutoCompleteRecyclerAdapter() {}
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
        private TextView keywordTextView;
        public ViewHolder(View view) {
            super(view);
            this.keywordTextView = (TextView) view;
            this.keywordTextView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if(pos != RecyclerView.NO_POSITION){
                    //동작 호출 (onItemClick 함수 호출)
                    if(itemClickListener != null){
                        itemClickListener.onItemClick(v, pos);
                    }
                }
            });
        }

        public TextView getKeywordTextView() {
            return keywordTextView;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.autocomplate_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ((TextView)holder.getKeywordTextView()).setText(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
