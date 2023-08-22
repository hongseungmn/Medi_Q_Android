package com.kosmo.kosmo.myDiary;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

public class MyDiaryCustomDialogUtil {
    public static void dialogResize(Context context, Dialog dialog, float width, float height) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
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
