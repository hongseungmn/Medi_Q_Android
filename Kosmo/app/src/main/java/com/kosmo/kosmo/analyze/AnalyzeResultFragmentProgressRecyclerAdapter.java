package com.kosmo.kosmo.analyze;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kosmo.kosmo.R;
import com.kosmo.kosmo.model.dto.NutIntakeDTO;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Set;

public class AnalyzeResultFragmentProgressRecyclerAdapter extends RecyclerView.Adapter<AnalyzeResultFragmentProgressRecyclerAdapter.ViewHolder> {
    private List<NutIntakeDTO> nutIntakeDTOList;
    private Context context;

    // 데이터 리스트를 업데이트하는 메서드
    public void setDataList(List<NutIntakeDTO> newDataList) {
        nutIntakeDTOList.clear(); // 기존의 데이터 리스트를 비웁니다.
        nutIntakeDTOList.addAll(newDataList); // 새로운 데이터 리스트를 추가합니다.
        notifyDataSetChanged(); // 어댑터에게 데이터가 변경되었음을 알려줍니다.
    }
    public void clearItems() {
        nutIntakeDTOList.clear();
        notifyDataSetChanged();
    }
    public AnalyzeResultFragmentProgressRecyclerAdapter(Context context, List<NutIntakeDTO> nutIntakeDTOList) {
        this.context = context;
        this.nutIntakeDTOList = nutIntakeDTOList;
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        public ViewHolder(View view) {
            super(view);
            this.linearLayout = (LinearLayout) view;

        }

        public LinearLayout getView() {
            return linearLayout;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.analyze_result_progress_recyclerview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayout linearLayout = holder.getView();
        ProgressBar progressBar = (ProgressBar)linearLayout.findViewById(R.id.progress_content);
        TextView progressTitleView = (TextView) linearLayout.findViewById(R.id.progress_title);
        TextView progressMaxvalView = (TextView) linearLayout.findViewById(R.id.max_value);
        progressTitleView.setText(nutIntakeDTOList.get(position).getNut()+"의 적정 섭취량");
        if(nutIntakeDTOList.get(position).getDRI() > nutIntakeDTOList.get(position).getNutNumber()) {
            progressBar.setProgressDrawable(ContextCompat.getDrawable(context,R.drawable.progressbar_progressbar1));
        }
        else if((nutIntakeDTOList.get(position).getDRI() <= nutIntakeDTOList.get(position).getNutNumber()) && (nutIntakeDTOList.get(position).getUL() > nutIntakeDTOList.get(position).getNutNumber())) {
            progressBar.setProgressDrawable(ContextCompat.getDrawable(context,R.drawable.progressbar_progressbar2));
        }
        else {
            progressBar.setProgressDrawable(ContextCompat.getDrawable(context,R.drawable.progressbar_progressbar3));
        }
        if(nutIntakeDTOList.get(position).getUL() <=0) {
            progressBar.setMax((int) (nutIntakeDTOList.get(position).getDRI()*30));
            progressBar.setProgress((int) (nutIntakeDTOList.get(position).getNutNumber()*10));
            progressMaxvalView.setVisibility(View.INVISIBLE);
        }
        else {
            progressBar.setMax((int) (nutIntakeDTOList.get(position).getDRI()*30));
            progressBar.setProgress((int) (nutIntakeDTOList.get(position).getNutNumber()*10));
        }

    }


    @Override
    public int getItemCount() {
        return nutIntakeDTOList.size();
    }

}
