package com.example.overlaytest;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class OverlayService extends Service {


    Button overlayedButton;

    ImageView  myImage;

    int width;
    int height;

    public OverlayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();



        Toast.makeText(getBaseContext(),"onCreate", Toast.LENGTH_LONG).show();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                0,
//              WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                      | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.RIGHT | Gravity.TOP;
        params.setTitle("Load Average");
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);


        overlayedButton = new Button(this);
        overlayedButton.setText("Overlay button");


        overlayedButton.setBackgroundColor(Color.BLACK);

        WindowManager.LayoutParams params2 = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, 0//WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL//
                , PixelFormat.TRANSLUCENT);
        params2.gravity = Gravity.LEFT | Gravity.TOP;
        params2.x = 0;
        params2.y = 0;
        wm.addView(overlayedButton, params);


        //Alright enough playing around, let's overlay an imageview




        myImage = new ImageView(this);
        myImage.setImageResource(R.drawable.smug_nami);
        myImage.setMaxWidth(100);
        myImage.setMaxHeight(100);



        WindowManager.LayoutParams params3 = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                0,
//              WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                      | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSPARENT);


        wm.addView(myImage,params);


        Log.d("onCreate", "end");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getBaseContext(),"onDestroy", Toast.LENGTH_LONG).show();

        if(overlayedButton != null)
        {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(overlayedButton);
            overlayedButton = null;
        }

        if(myImage != null)
        {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(myImage);
            myImage = null;
        }
    }


}


