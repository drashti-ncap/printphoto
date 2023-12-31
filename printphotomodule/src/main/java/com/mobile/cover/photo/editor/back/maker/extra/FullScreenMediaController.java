package com.mobile.cover.photo.editor.back.maker.extra;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;


public class FullScreenMediaController extends MediaController {

    private ImageButton fullScreen;
    private String isFullScreen;


    public FullScreenMediaController(Context context) {
        super(context);
    }

    @Override
    public void setAnchorView(View view) {

        super.setAnchorView(view);

        //image button for full screen to be added to media controller
        fullScreen = new ImageButton(super.getContext());

        LayoutParams params =
                new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        params.rightMargin = 80;
        addView(fullScreen, params);

        //fullscreen indicator from intent
        isFullScreen = ((Activity) getContext()).getIntent().
                getStringExtra("fullScreenInd");


//        if("y".equals(isFullScreen)){
//            fullScreen.setImageResource(R.drawable.ic_fullscreen_exit);
//        }else{
//            fullScreen.setImageResource(R.drawable.ic_fullscreen);
//        }

        //add listener to image button to handle full screen and exit full screen events
        fullScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(getContext(),VideoActivity.class);
//
//                if("y".equals(isFullScreen)){
////                    intent.putExtra("fullScreenInd", "");
//                }else{
////                    intent.putExtra("fullScreenInd", "y");
//                }
//                ((Activity)getContext()).startActivity(intent);
            }
        });
    }
}
