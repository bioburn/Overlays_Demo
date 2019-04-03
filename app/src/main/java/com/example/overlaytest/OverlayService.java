package com.example.overlaytest;

import android.Manifest;
import android.animation.AnimatorSet;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class OverlayService extends Service {


    Button overlayedButton;

    ImageView  myImage;

    FrameLayout floating_layout;

    ImageView SpinningImage;

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

        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        //how many times it rotates
        anim.setRepeatCount(Animation.ABSOLUTE);
        //the speed
        anim.setDuration(2000);

        RotateAnimation counterClockwise = new RotateAnimation(0.0f, -360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        counterClockwise.setInterpolator(new LinearInterpolator());
        //how many times it rotates
        counterClockwise.setRepeatCount(Animation.ABSOLUTE);
        //the speed
        counterClockwise.setDuration(2000);





        myImage.startAnimation(anim);
        overlayedButton.startAnimation(anim);

        WindowManager.LayoutParams params3 = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                0,
//              WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                      | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);



        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        floating_layout = (FrameLayout) inflater.inflate(R.layout.cutin_layout, null);



        //wm.addView(myImage,params);

        SpinningImage = floating_layout.findViewById(R.id.imageView2);

        final ImageView topleft = floating_layout.findViewById(R.id.topLeft);

        final ImageView bottomLeft = floating_layout.findViewById(R.id.bottomLeft);

        final ImageView bottomRight = floating_layout.findViewById(R.id.bottomRight);


        wm.addView(floating_layout,params3);
        //SpinningImage.startAnimation(anim);



        Animation a = new AlphaAnimation(1.00f, 0.00f);
        a.setDuration(3000);
        a.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            public void onAnimationEnd(Animation animation) {
                SpinningImage.setVisibility(View.GONE);
                topleft.setVisibility(View.GONE);
                bottomLeft.setVisibility(View.GONE);
                bottomRight.setVisibility(View.GONE);

            }
        });

        Animation b = new AlphaAnimation(0.00f, 1.00f);

        b.setDuration(1000);

        AnimationSet s = new AnimationSet(false);
        s.addAnimation(b);
        s.addAnimation(anim);
        s.addAnimation(a);

        AnimationSet counterClockAnim = new AnimationSet(false);
        counterClockAnim.addAnimation(b);
        counterClockAnim.addAnimation(counterClockwise);
        counterClockAnim.addAnimation(a);




        SpinningImage.startAnimation(counterClockAnim);
        topleft.startAnimation(s);
        bottomLeft.startAnimation(s);
        bottomRight.startAnimation(counterClockAnim);

        myImage.startAnimation(anim);
        overlayedButton.startAnimation(anim);

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

        //if(myImage != null)
        //{
            //((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(myImage);
            //myImage = null;
        //}


        if(floating_layout!=null)
        {
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(floating_layout);
            floating_layout = null;
        }




    }


}


