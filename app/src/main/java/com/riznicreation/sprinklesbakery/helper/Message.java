package com.riznicreation.sprinklesbakery.helper;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.riznicreation.sprinklesbakery.R;

public class Message {
    public static void error(Context context,String msg){
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.roundedtextfield);
        view.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.error)));

        TextView text = view.findViewById(android.R.id.message);
        text.setCompoundDrawablePadding(25);
        text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error,0,0,0);
        text.setTextColor(Color.parseColor("#FFFFFF"));
        toast.show();
    }
    public static void info(Context context,String msg){
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.roundedtextfield);
        view.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.info)));

        TextView text = view.findViewById(android.R.id.message);
        text.setCompoundDrawablePadding(25);
        text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_info,0,0,0);
        text.setTextColor(Color.parseColor("#FFFFFF"));
        toast.show();
    }
    public static void success(Context context,String msg){
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.roundedtextfield);
        view.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.success)));

        TextView text = view.findViewById(android.R.id.message);
        text.setCompoundDrawablePadding(25);
        text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_success,0,0,0);
        text.setTextColor(Color.parseColor("#FFFFFF"));
        toast.show();
    }
}
