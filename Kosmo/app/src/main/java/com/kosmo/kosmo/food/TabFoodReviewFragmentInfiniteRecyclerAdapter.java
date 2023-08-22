package com.kosmo.kosmo.food;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kosmo.kosmo.R;
import com.kosmo.kosmo.model.dto.ReviewDTO;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabFoodReviewFragmentInfiniteRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ReviewDTO> reviewDTOList = new ArrayList<>();
    private int VIEW_TYPE_ITEM = 0;
    private int VIEW_TYPE_LOADING = 1;

    private Context context;

    // 데이터 리스트를 업데이트하는 메서드
    public void setDataList(List<ReviewDTO> newDataList) {
        reviewDTOList = newDataList;
        Log.i("setDataList() : ",reviewDTOList.toString());
        notifyDataSetChanged(); // 어댑터에게 데이터가 변경되었음을 알려줍니다.
    }
    public void clearItems() {
        reviewDTOList.clear();
        notifyDataSetChanged();
    }
    public TabFoodReviewFragmentInfiniteRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void addMoreData(List<ReviewDTO> reviewDTOList) {
        Log.i("com.kosmo.kosmo","addMoreData() 호출 reviewDTOList의 size() : "+reviewDTOList.size());
        for(ReviewDTO review : reviewDTOList) {
            if(review != null) {
                this.reviewDTOList.add(review);
                Log.i("com.kosmo.kosmo","review :"+review.toString());
            }
            else {
                Log.i("com.kosmo.kosmo","review == null");
            }
        }
        addLoadingItem();
        notifyItemInserted(reviewDTOList.size() - 1);
    }

    public void removeLastItem() {
        int lastItemIndex = getItemCount() - 1;
        if (lastItemIndex >= 0 && lastItemIndex < reviewDTOList.size()) {
            Log.i("com.kosmo.kosmo", "마지막 요소 제거 " + reviewDTOList.toString());
            reviewDTOList.remove(lastItemIndex);
            notifyItemRemoved(lastItemIndex);
            Log.i("com.kosmo.kosmo", "마지막 요소 제거 " + reviewDTOList.toString());
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayoutCompat reviewViewHolder;
        public ViewHolder(View view) {
            super(view);
            this.reviewViewHolder = (LinearLayoutCompat) view;

        }

        public LinearLayoutCompat getView() {
            return reviewViewHolder;
        }
    }

    public static class LoadingHolder extends RecyclerView.ViewHolder {
        private LinearLayout reviewViewHolder;
        public LoadingHolder(View view) {
            super(view);
            this.reviewViewHolder = (LinearLayout) view;
        }
        public LinearLayout getLoadingView() {
            return reviewViewHolder;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.food_review_layout, parent, false);
            return new ViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.food_review_loading_layout, parent, false);
            return new LoadingHolder(view);
        }
        throw new IllegalArgumentException("Invalid view type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();

        if(holder instanceof ViewHolder) {
            Log.i("com.kosmo.kosmo","onBindViewHolder 발생 : holder.getAdapterPosition()"+pos);
            LinearLayoutCompat layoutCompat = ((ViewHolder) holder).getView();
            LinearLayout effectListLayout = (LinearLayout) layoutCompat.findViewById(R.id.effect_list_layout);
            LinearLayout noEffectListLayout = (LinearLayout) layoutCompat.findViewById(R.id.noEffect_list_layout);
            TextView reviewLayoutUserIdTextView = (TextView) layoutCompat.findViewById(R.id.review_layout_userId);
            TextView reviewLayoutUserInfoTextView = (TextView) layoutCompat.findViewById(R.id.review_layout_userInfo);
            TextView reviewLayoutDateTextView = (TextView) layoutCompat.findViewById(R.id.review_layout_date);
            RatingBar reviewLayoutStarScore = (RatingBar) layoutCompat.findViewById(R.id.review_layout_star_score);
            TextView reviewLayoutEffectTextView = (TextView) layoutCompat.findViewById(R.id.review_layout_effect);
            TextView reviewLayoutNoEffectTextView = (TextView) layoutCompat.findViewById(R.id.review_layout_noEffect);
            TextView reviewLayoutContentTextView = (TextView) layoutCompat.findViewById(R.id.review_layout_content);
            if(reviewDTOList.get(position) ==null) {
                return;
            }
            reviewLayoutUserIdTextView.setText(reviewDTOList.get(position).getR_id());
            reviewLayoutDateTextView.setText(reviewDTOList.get(position).getR_regidate());
            List<String> effectList = Arrays.asList(reviewDTOList.get(position).getEffect().replace("[","").replace("]","").split(","));
            for(String effect : effectList) {
                TextView dynamicTextView = new TextView(context);
                dynamicTextView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                dynamicTextView.setText(effect);
                dynamicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                dynamicTextView.setPadding(dpToPx(2), dpToPx(2), dpToPx(2), dpToPx(2));
                dynamicTextView.setTextColor(Color.parseColor("#25a6fe"));
                dynamicTextView.setTypeface(null, Typeface.BOLD);
                dynamicTextView.setBackgroundResource(R.drawable.hashtag_effected);
                dynamicTextView.setGravity(Gravity.CENTER);

                // TextView를 부모 뷰그룹에 추가합니다.
                effectListLayout.addView(dynamicTextView);
            }
            List<String> noEffectList = Arrays.asList(reviewDTOList.get(position).getNoEffect().replace("[","").replace("]","").split(","));
            Log.i("noEffectList", noEffectList.toString());
            for(String effect : noEffectList) {
                TextView dynamicTextView = new TextView(context);
                dynamicTextView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));
                dynamicTextView.setText(effect);
                dynamicTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                dynamicTextView.setPadding(dpToPx(2), dpToPx(2), dpToPx(2), dpToPx(2));
                dynamicTextView.setTextColor(Color.parseColor("#ff4b4b"));
                dynamicTextView.setTypeface(null, Typeface.BOLD);
                dynamicTextView.setBackgroundResource(R.drawable.hashtag_no_effected);
                dynamicTextView.setGravity(Gravity.CENTER);

                // TextView를 부모 뷰그룹에 추가합니다.
                noEffectListLayout.addView(dynamicTextView);
            }
        }
        else if(holder instanceof LoadingHolder) {
            Log.i("com.kosmo.kosmo","onBindViewHolderLoadingHolder 발생 : LoadingHolder.getAdapterPosition()");
            LinearLayout layoutCompat = ((LoadingHolder) holder).getLoadingView();
        }
    }



    @Override
    public int getItemCount() {
        if (reviewDTOList != null) {
            return reviewDTOList.size();
        } else {
            //clearItems();
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 만약 마지막 아이템이면 로딩 뷰의 타입을 반환합니다.
        return (position == reviewDTOList.size() - 1) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }
    // 로딩 아이템을 추가하는 메서드
    public void addLoadingItem() {
        reviewDTOList.add(null); // 로딩 아이템은 null로 추가
        Log.i("com.kosmo.kosmo","로딩 바가 추가되었습니다");
        notifyItemInserted(reviewDTOList.size() - 1);
    }
    private int dpToPx(int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
