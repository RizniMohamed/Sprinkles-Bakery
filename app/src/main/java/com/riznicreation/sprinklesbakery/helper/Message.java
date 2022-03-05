package com.riznicreation.sprinklesbakery.helper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;

import com.riznicreation.sprinklesbakery.R;

public class Message {
    public static void error(Context context,String msg){
        createToast(context,msg,R.drawable.ic_error,Color.RED);
    }
    public static void info(Context context,String msg){
        createToast(context,msg,R.drawable.ic_info,Color.BLUE);
    }
    public static void success(Context context,String msg){
        createToast(context,msg,R.drawable.ic_success,Color.GREEN);
    }

    private static void createToast(Context context, String msg, int icon, int color){
        TextView text = new TextView(context);
        text.setText(msg);
        text.setPadding(40,20,40,20);
        text.setTextSize(15);
        text.setCompoundDrawablePadding(10);
        text.setCompoundDrawablesWithIntrinsicBounds(icon,0,0,0);
        text.setTypeface(null, Typeface.BOLD);
        text.setTextColor(Color.WHITE);
        text.setBackground(AppCompatResources.getDrawable(context,R.drawable.roundedtextfield));
        text.setBackgroundTintList(ColorStateList.valueOf(color));
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(text);
        toast.show();

    }
}
