package com.yiqihudong.imageutil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yiqihudong.myutils.R;

import java.util.ArrayList;

/**
 * PhotoWallActivity
 *
 * @author weiwu.song
 */
public class PhotoWallActivity extends Activity {
    public static final String RESULT_LIST = "ResultList";
    public static final String KEY_POSITION = "position";
    public static final String KEY_LIST = "list";
    public static final String KEY_TYPE = "type";
    private String titleRightT = "";
    private HackyViewPager mPager = null;
    private int preSize = 0;
    private PhotoWallAdapter photoWallAdapter = null;
    private ArrayList<String> list = null;
    private RelativeLayout photo_wall_back;
    private boolean parseQR;
    private TextView title;

    public boolean isParseQR() {
        return parseQR;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_photo_wall);
        parseQR = getIntent().getBooleanExtra("parse",true);

        photo_wall_back = (RelativeLayout) findViewById(R.id.photo_wall_back);
        photo_wall_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int position = getIntent().getIntExtra(KEY_POSITION, 0);
        list = getIntent().getStringArrayListExtra(KEY_LIST);
        titleRightT = getIntent().getStringExtra(KEY_TYPE);
        title = (TextView)findViewById(R.id.title);
        setTitle(position,list.size());
//        if (list != null && list.size() > 0) {
//            if (Utils.stringIsNull(titleRightT)) {
//                setTitleText((position + 1) + "/" + list.size());
//            } else {
//                setTitleText((position + 1) + "/" + list.size(), titleRightT);
//            }
//        } else {
//            setTitleText("");
//        }
        preSize = list.size();
        mPager = (HackyViewPager) findViewById(R.id.photo_wall_pager);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
//                refreshPhotoWallTitle();
                setTitle(i,list.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        photoWallAdapter = new PhotoWallAdapter(list,this);
        mPager.setAdapter(photoWallAdapter);
        mPager.setCurrentItem(position, false);
    }



    private void clickBack() {
        if (list.size() < preSize) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra(RESULT_LIST, list);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clickBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setTitle(int position,int size){
        if(size == 0){
            title.setText("");
            return;
        }
        title.setText("("+(position+1)+"/"+size+")");
    }
}
