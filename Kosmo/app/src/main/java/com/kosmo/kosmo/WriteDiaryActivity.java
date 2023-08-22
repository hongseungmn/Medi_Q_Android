package com.kosmo.kosmo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.kosmo.kosmo.myDiary.MyDiaryCustomDialog;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;

public class WriteDiaryActivity extends AppCompatActivity {
    private MaterialCalendarView calendarView;
    private ImageButton backButton;
    private MyDiaryCustomDialog customDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);
        backButton = (ImageButton) findViewById(R.id.write_diary_back_btn);
        backButton.setOnClickListener(view -> onBackPressed());
        calendarView = findViewById(R.id.write_diary_calendar);

        // 좌우 화살표 사이 연, 월의 폰트 스타일 설정
        calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader);
        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        calendarView.addDecorators(new SaturDayDecorator(),new SundayDecorator(),new TodayDecorator(this));

        // 좌우 화살표 가운데의 연/월이 보이는 방식 커스텀
        calendarView.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                // CalendarDay라는 클래스는 LocalDate 클래스를 기반으로 만들어진 클래스다
                // 때문에 MaterialCalendarView에서 연/월 보여주기를 커스텀하려면 CalendarDay 객체의 getDate()로 연/월을 구한 다음 LocalDate 객체에 넣어서
                // LocalDate로 변환하는 처리가 필요하다
                LocalDate inputText = day.getDate();
                String[] calendarHeaderElements = inputText.toString().split("-");
                StringBuilder calendarHeaderBuilder = new StringBuilder();
                calendarHeaderBuilder.append(calendarHeaderElements[0])
                        .append(" ")
                        .append(calendarHeaderElements[1]);
                return calendarHeaderBuilder.toString();
            }
        });
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            Log.i("선택된 날짜",date.toString());
            customDialog = new MyDiaryCustomDialog(this);
            customDialog.setCancelable(true);

            int alpha = 50;
            int color = Color.argb(alpha, 0, 0 , 0);
            customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(color));
            customDialog.show();
        });
    }

    public static class TodayDecorator implements DayViewDecorator {
        private CalendarDay date = CalendarDay.today();
        private Drawable drawable;

        public TodayDecorator(Context context) {
            drawable = ContextCompat.getDrawable(context, R.drawable.report_note_bg);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day != null && day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(drawable);
        }
    }
    public static class SaturDayDecorator implements DayViewDecorator {
        private Calendar calendar = Calendar.getInstance();

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            int saturday = day.getDate().with(DayOfWeek.SATURDAY).getDayOfMonth();
            return saturday == day.getDay();
        }
        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }
    public static class SundayDecorator implements DayViewDecorator {
        private Calendar calendar = Calendar.getInstance();

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            int sunday = day.getDate().with(DayOfWeek.SUNDAY).getDayOfMonth();
            return sunday == day.getDay();
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }
}