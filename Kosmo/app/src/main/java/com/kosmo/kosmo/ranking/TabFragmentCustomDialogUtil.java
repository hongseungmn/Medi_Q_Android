package com.kosmo.kosmo.ranking;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

public class TabFragmentCustomDialogUtil {
    public static void dialogResize(Context context, Dialog dialog, float width, float height) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

//        if (Build.VERSION.SDK_INT < 30) {
//            Display display = windowManager.getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//
//            android.view.Window window = dialog.getWindow();
//            int x = (int) (size.x * width);
//            int y = (int) (size.y * height);
//
//            if (window != null) {
//                window.setLayout(x, y);
//            }
//        } else {
//            android.view.Window window = dialog.getWindow();
//            WindowManager.LayoutParams layoutParams = window.getAttributes();
//            android.graphics.Rect rect = windowManager.getCurrentWindowMetrics().getBounds();
//
//            int x = (int) (rect.width() * width);
//            int y = (int) (rect.height() * height);
//
//            layoutParams.width = x;
//            layoutParams.height = y;
//
//            window.setAttributes(layoutParams);
//        }

        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        android.view.Window window = dialog.getWindow();
        int x = (int) (size.x * width);
        int y = (int) (size.y * height);

        if (window != null) {
            window.setLayout(x, y);
        }
    }
}
