package com.kosmo.kosmo.myDiary;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;

public class MyDiaryDateDecorator implements DayViewDecorator {
    private final Calendar calendar = Calendar.getInstance();

    public MyDiaryDateDecorator() {

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {

        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(null);
        view.addSpan(new StyleSpan(Typeface.BOLD));   // 달력 안의 모든 숫자들이 볼드 처리됨
    }
}
