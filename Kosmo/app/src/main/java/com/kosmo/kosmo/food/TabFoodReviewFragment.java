package com.kosmo.kosmo.food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kosmo.kosmo.databinding.FragmentFoodtableReviewTabBinding;
import com.kosmo.kosmo.model.dto.ReviewDTO;
import com.kosmo.kosmo.model.dto.TotalReviewDTO;
import com.kosmo.kosmo.model.repository.FoodListRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.rxjava3.core.Single;

public class TabFoodReviewFragment extends Fragment {

    private FragmentFoodtableReviewTabBinding fragmentFoodtableReviewTabBinding;
    private FoodSharedViewModel foodSharedViewModel;

    private FoodListRepository foodListRepository;
    private TabFoodReviewFragmentInfiniteRecyclerAdapter tabFoodReviewFragmentInfiniteRecyclerAdapter;

    private String foodNo;
    private List<ReviewDTO> reviewDTOList;
    public boolean isLoading = false;
    private boolean isEnd = false;
    private int current = 2;
    private RecyclerView infiniteRecyclerView;
    private Handler handler = new Handler(Looper.getMainLooper());

    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentFoodtableReviewTabBinding = FragmentFoodtableReviewTabBinding.inflate(getLayoutInflater());
        Button to3Dgraph = (Button) fragmentFoodtableReviewTabBinding.to3dGraph;
        foodSharedViewModel.getSharedData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String data) {
                // 데이터를 처리하는 로직
                Log.d("TabFoodReviewFragment", "Received data: " + data);
                foodNo = data;
                foodListRepository = new FoodListRepository();
                Single<TotalReviewDTO> totalReview = foodListRepository.getTotalReview(foodNo);
                totalReview.subscribe(
                        review->{
                            ((TextView)fragmentFoodtableReviewTabBinding.totalReviewScore).setText(review.getStarScoreTotal()+"");
                            ((RatingBar)fragmentFoodtableReviewTabBinding.totalReviewStar).setRating(review.getStarScoreTotal());
                            int totalValue = 0;
                            Set<String> keySet = review.getStarScore().keySet();
                            for(String key : keySet) {
                                totalValue += (int)(double)review.getStarScore().get(key);
                            }

                            ((ProgressBar)fragmentFoodtableReviewTabBinding.prgLight).setMax(totalValue);
                            ((ProgressBar)fragmentFoodtableReviewTabBinding.prgLight2).setMax(totalValue);
                            ((ProgressBar)fragmentFoodtableReviewTabBinding.prgLight3).setMax(totalValue);
                            ((ProgressBar)fragmentFoodtableReviewTabBinding.prgLight4).setMax(totalValue);
                            ((ProgressBar)fragmentFoodtableReviewTabBinding.prgLight5).setMax(totalValue);
                            ((ProgressBar)fragmentFoodtableReviewTabBinding.prgLight).setProgress((int)(double)review.getStarScore().get("COUNT_1"));
                            ((ProgressBar)fragmentFoodtableReviewTabBinding.prgLight2).setProgress((int)(double)review.getStarScore().get("COUNT_2"));
                            ((ProgressBar)fragmentFoodtableReviewTabBinding.prgLight3).setProgress((int)(double)review.getStarScore().get("COUNT_3"));
                            ((ProgressBar)fragmentFoodtableReviewTabBinding.prgLight4).setProgress((int)(double)review.getStarScore().get("COUNT_4"));
                            ((ProgressBar)fragmentFoodtableReviewTabBinding.prgLight5).setProgress((int)(double)review.getStarScore().get("COUNT_5"));
                            int effectTotalCount = 0;
                            for(Map map : review.getEffectList()) {
                                effectTotalCount += (int)(double)map.get("COUNT");
                            }
                            //[{COUNT=1, VALUE=감기 덜 걸림}, {COUNT=0, VALUE=피로 개선}, {COUNT=0, VALUE=효과가 없었어요}, {COUNT=0, VALUE=구내염 개선}]
                            int i=1;
                            Log.i("com.kosmo.kosmo","effectTotalCount : "+effectTotalCount);
                            Log.i("com.kosmo.kosmo","review.getEffectList() : "+ review.getEffectList().toString());
                            Iterator<Map<String, Object>> iterator = review.getEffectList().iterator();
                            int index = 1;
                            while (iterator.hasNext()) {
                                Map map = iterator.next();
                                TextView textView;
                                TextView percentView;
                                if(index==1) {
                                    textView = (TextView)fragmentFoodtableReviewTabBinding.effectText1;
                                    percentView = (TextView)fragmentFoodtableReviewTabBinding.effectPercent1;
                                    textView.setText(map.get("VALUE").toString());
                                    try {percentView.setText((int)((double)map.get("COUNT") / effectTotalCount*100)+"%");}
                                    catch (Exception e) {Log.i("com.kosmo.kosmo","리뷰 데이터가 없습니다");}
                                }
                                else if(index==2) {
                                    textView = (TextView)fragmentFoodtableReviewTabBinding.effectText2;
                                    percentView = (TextView)fragmentFoodtableReviewTabBinding.effectPercent2;
                                    textView.setText(map.get("VALUE").toString());
                                    try {percentView.setText((int)((double)map.get("COUNT") / effectTotalCount*100)+"%");}
                                    catch (Exception e) {Log.i("com.kosmo.kosmo","리뷰 데이터가 없습니다");}
                                }
                                else if(index==3) {
                                    textView = (TextView)fragmentFoodtableReviewTabBinding.effectText3;
                                    percentView = (TextView)fragmentFoodtableReviewTabBinding.effectPercent3;
                                    textView.setText(map.get("VALUE").toString());
                                    try {percentView.setText((int)((double)map.get("COUNT") / effectTotalCount*100)+"%");}
                                    catch (Exception e) {Log.i("com.kosmo.kosmo","리뷰 데이터가 없습니다");}
                                }
                                else if(index==4) {
                                    textView = (TextView)fragmentFoodtableReviewTabBinding.effectText4;
                                    percentView = (TextView)fragmentFoodtableReviewTabBinding.effectPercent4;
                                    textView.setText(map.get("VALUE").toString());
                                    try {percentView.setText((int)((double)map.get("COUNT") / effectTotalCount*100)+"%");}
                                    catch (Exception e) {Log.i("com.kosmo.kosmo","리뷰 데이터가 없습니다");}
                                }
                                index++;
                            }
                            int noEffectTotalCount = 0;
                            for(Map map : review.getNoEffectList()) {
                                noEffectTotalCount += (int)(double)map.get("COUNT");
                            }
                            Iterator<Map<String, Object>> iterator2 = review.getNoEffectList().iterator();
                            int index2 = 1;
                            while (iterator2.hasNext()) {
                                Map map = iterator2.next();
                                TextView textView;
                                TextView percentView;
                                if(index2==1) {
                                    textView = (TextView)fragmentFoodtableReviewTabBinding.noEffectText1;
                                    percentView = (TextView)fragmentFoodtableReviewTabBinding.noEffectPercent1;
                                    textView.setText(map.get("VALUE").toString());
                                    try {percentView.setText((int)((double)map.get("COUNT") / noEffectTotalCount*100)+"%");}
                                    catch (Exception e) {Log.i("com.kosmo.kosmo","리뷰 데이터가 없습니다");}
                                }
                                else if(index2==2) {
                                    textView = (TextView)fragmentFoodtableReviewTabBinding.noEffectText2;
                                    percentView = (TextView)fragmentFoodtableReviewTabBinding.noEffectPercent2;
                                    textView.setText(map.get("VALUE").toString());
                                    try {percentView.setText((int)((double)map.get("COUNT") / noEffectTotalCount*100)+"%");}
                                    catch (Exception e) {Log.i("com.kosmo.kosmo","리뷰 데이터가 없습니다");}
                                }
                                else if(index2==3) {
                                    textView = (TextView)fragmentFoodtableReviewTabBinding.noEffectText3;
                                    percentView = (TextView)fragmentFoodtableReviewTabBinding.noEffectPercent3;
                                    textView.setText(map.get("VALUE").toString());
                                    try {percentView.setText((int)((double)map.get("COUNT") / noEffectTotalCount*100)+"%");}
                                    catch (Exception e) {Log.i("com.kosmo.kosmo","리뷰 데이터가 없습니다");}
                                }
                                else if(index2==4) {
                                    textView = (TextView)fragmentFoodtableReviewTabBinding.noEffectText4;
                                    percentView = (TextView)fragmentFoodtableReviewTabBinding.noEffectPercent4;
                                    textView.setText(map.get("VALUE").toString());
                                    try {percentView.setText((int)((double)map.get("COUNT") / noEffectTotalCount*100)+"%");}
                                    catch (Exception e) {Log.i("com.kosmo.kosmo","리뷰 데이터가 없습니다");}
                                }
                                index2++;
                            }
                            Log.i("com.kosmo.kosmo.review","리뷰 데이터 로딩 마침");
                            infiniteRecyclerView = fragmentFoodtableReviewTabBinding.infiniteRecyclerView;
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                            tabFoodReviewFragmentInfiniteRecyclerAdapter = new TabFoodReviewFragmentInfiniteRecyclerAdapter(getContext());
                            infiniteRecyclerView.setAdapter(tabFoodReviewFragmentInfiniteRecyclerAdapter);
                            infiniteRecyclerView.setLayoutManager(layoutManager);
                            foodListRepository = new FoodListRepository();
                            Single<List<ReviewDTO>> reviewList = foodListRepository.getReviewList(foodNo,"0");
                            reviewList.subscribe(
                                    reviewDTOS -> {
                                        Log.i("초기 데이터 로딩","초기 리뷰 데이터 로딩 실행 : reviewDTOS.size()"+reviewDTOS.size());
                                        if (!reviewDTOS.isEmpty()) {
                                            reviewDTOList = reviewDTOS;
                                            Log.i("초기 데이터 로딩","setDataList(reviewDTOList) 실행 : reviewDTOList.size"+reviewDTOList.size());
                                            tabFoodReviewFragmentInfiniteRecyclerAdapter.addMoreData(reviewDTOList);
                                        } else {
                                            // 리뷰가 없는 경우에 대한 처리
                                            // 예: 리뷰 없음을 알리는 텍스트를 화면에 표시하거나, 다른 작업을 수행
                                        }
                                    },
                                    Throwable -> {
                                        Log.i("초기 데이터 로딩","초기 리뷰 데이터 로딩시 에러");
                                        Log.i("초기 데이터 로딩",Throwable.getMessage());
                                    }
                            );
                            infiniteRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                    Log.i("onScrolled","함수 실행");
                                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                                    int totalItemCount = layoutManager.getItemCount();
                                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                                    Log.i("onScrolled","totalItemCount : "+totalItemCount);
                                    Log.i("onScrolled","lastVisibleItemPosition : "+lastVisibleItemPosition);
                                    if (!isLoading  && (lastVisibleItemPosition == totalItemCount-1) ) {
                                        // 스크롤이 제일 하단에 도달했을 때 추가적인 작업을 수행합니다.
                                        // 예를 들어, 새로운 데이터를 로드하거나 뷰를 동적으로 생성합니다.
                                        // 여기에 원하는 작업을 추가하면 됩니다.
                                        if(isEnd) return;
                                        handler.postDelayed(() -> {
                                            Log.i("onScrolled","loadMoreData()실행됨 (isEnd) : "+isEnd);
                                            loadMoreData();
                                        },4000);
                                    }
                                }
                            });
                        },
                        throwable -> {
                            Log.i("com.kosmo.kosmo",throwable.getMessage());
                        }
                );
            }
        });
        to3Dgraph.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);

            // String으로 받아서 넣기
            String url = "http://192.168.0.16:9090/Android3DGraph?no="+foodNo;
            Intent internetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(internetIntent);
        });
        return fragmentFoodtableReviewTabBinding.getRoot();
    }
    // ViewModel 인스턴스를 받는 메서드
    public void setSharedViewModel(FoodSharedViewModel foodSharedViewModel) {
        this.foodSharedViewModel = foodSharedViewModel;
    }

    @SuppressLint("CheckResult")
    private void loadMoreData() {
        if (isLoading) {
            return; // 이미 데이터를 불러오는 중이면 무시
        }
        isLoading = true;
        Log.i("loadMoreData()","loadMoreData() 실행됨");

        Log.i("loadMoreData()","loadMoreData() current의 값:"+current);
        Single<List<ReviewDTO>> reviewList = foodListRepository.getReviewList(foodNo,String.valueOf(current));
        reviewList.subscribe(
                reviewDTOS -> {
                    if(reviewDTOS.size()!=0) {
                        Log.i("loadMoreData()","loadMoreData() if(reviewDTOS.size()!=0) size="+reviewDTOS.size());
                        reviewDTOList = reviewDTOS;
                        tabFoodReviewFragmentInfiniteRecyclerAdapter.addMoreData(reviewDTOList);
                        tabFoodReviewFragmentInfiniteRecyclerAdapter.notifyItemInserted(reviewDTOList.size() - 1);
                    }
                    else {
                        //tabFoodReviewFragmentInfiniteRecyclerAdapter.removeLastItem();

                        isEnd = true;
                        Log.i("loadMoreData()","더이상 리뷰가 존재하지 않습니다");
                        return;
                    }
                    isLoading = false;
                },
                Throwable -> {
                    Log.i("loadMoreData()","리뷰 데이터 받아오기 실패");
                    isLoading = false;
                }
        );
        current += 2;
    }

}
