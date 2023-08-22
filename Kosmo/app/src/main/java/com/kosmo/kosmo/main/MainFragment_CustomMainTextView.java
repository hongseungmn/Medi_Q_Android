package com.kosmo.kosmo.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.kosmo.kosmo.R;

public class MainFragment_CustomMainTextView extends androidx.appcompat.widget.AppCompatTextView {
    private Drawable imageDrawable;
    public MainFragment_CustomMainTextView(Context context) {
        super(context);
        init(context);
    }
    public MainFragment_CustomMainTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        // 이미지 초기화
        imageDrawable = ContextCompat.getDrawable(context, R.drawable.main_textview_img);
        // 이미지 크기 설정 (원하는 크기로 조정)
        int imageSize = context.getResources().getDimensionPixelSize(R.dimen.main_textview_img_dimen2);
        if (imageDrawable != null) {
            imageDrawable.setBounds(0, 0, imageSize, imageSize);
        }
    }
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // 텍스트 영역의 오른쪽 하단에 이미지를 그립니다.
        if (imageDrawable != null) {
            int textHeight = getLayout().getHeight();
            int imageHeight = imageDrawable.getIntrinsicHeight();
            int imageY = textHeight+50; // 텍스트 높이에 맞춰 이미지의 Y 위치 계산
            int imageX = getWidth() - imageDrawable.getIntrinsicWidth()-80; // 오른쪽에 배치
            canvas.save();
            canvas.translate(imageX, imageY);
            imageDrawable.draw(canvas);
            canvas.restore();
        }
    }
}
