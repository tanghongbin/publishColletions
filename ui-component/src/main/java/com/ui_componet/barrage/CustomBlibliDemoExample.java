package com.ui_componet.barrage;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
//import master.flame.danmaku.controller.IDanmakuView;
//import master.flame.danmaku.danmaku.loader.ILoader;
//import master.flame.danmaku.danmaku.loader.IllegalDataException;
//import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
//import master.flame.danmaku.danmaku.model.BaseDanmaku;
//import master.flame.danmaku.danmaku.model.DanmakuTimer;
//import master.flame.danmaku.danmaku.model.IDanmakus;
//import master.flame.danmaku.danmaku.model.IDisplayer;
//import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
//import master.flame.danmaku.danmaku.model.android.DanmakuContext;
//import master.flame.danmaku.danmaku.model.android.Danmakus;
//import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
//import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
//import master.flame.danmaku.danmaku.parser.IDataSource;
//import master.flame.danmaku.ui.widget.DanmakuView;

import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
@author 汤洪斌
@time 2019/6/21 0021 10:10
@version 1.0
@describe b站弹幕帮助类，使用说明:
             1.var helper = CustomBlibliDemoExample(context,mDanmakuView)
*                 helper.initBlibliBarrage()
 *           2.分别在对应的activity中的onResume，onPause,   onDestroy, onBackpressed, onConfigratonChanged
 *            方法中调用对应的方法
 *
 *           3.如果有图片，drawable 大小设置为100，100
*/
public class CustomBlibliDemoExample {

//    private IDanmakuView mDanmakuView;
//    private Context mAppContext;
//    private ExecutorService executor;
//
//
//    public CustomBlibliDemoExample(Context mAppContext,IDanmakuView mDanmakuView) {
//        this.mDanmakuView = mDanmakuView;
//        this.mAppContext = mAppContext;
//        this.executor = Executors.newCachedThreadPool();
//    }
//
//    private BaseDanmakuParser mParser;
//    private DanmakuContext mContext;
//    private BaseCacheStuffer.Proxy mCacheStufferAdapter = new BaseCacheStuffer.Proxy() {
//
//        private Drawable mDrawable;
//
//        @Override
//        public void prepareDrawing(final BaseDanmaku danmaku, boolean fromWorkerThread) {
//            if (danmaku.text instanceof Spanned) { // 根据你的条件检查是否需要需要更新弹幕
//                // FIXME 这里只是简单启个线程来加载远程url图片，请使用你自己的异步线程池，最好加上你的缓存池
//                executor.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        Drawable drawable = mDrawable;
////                        if(drawable == null) {
////                            drawable = mAppContext.getResources().getDrawable(R.drawable.image_holder);
////                        }
////                        if (drawable != null) {
////                            drawable.setBounds(0, 0, 100, 100);
////                            SpannableStringBuilder spannable = createSpannable(drawable);
////                            danmaku.text = spannable;
////                            if(mDanmakuView != null) {
////                                mDanmakuView.invalidateDanmaku(danmaku, false);
////                            }
////                            return;
////                        }
//                    }
//                });
//            }
//        }
//
//        @Override
//        public void releaseResource(BaseDanmaku danmaku) {
//            // TODO 重要:清理含有ImageSpan的text中的一些占用内存的资源 例如drawable
//        }
//    };
//
//    /**
//     * 绘制背景(自定义弹幕样式)
//     */
//    private static class BackgroundCacheStuffer extends SpannedCacheStuffer {
//        // 通过扩展SimpleTextCacheStuffer或SpannedCacheStuffer个性化你的弹幕样式
//        final Paint paint = new Paint();
//
//        @Override
//        public void measure(BaseDanmaku danmaku, TextPaint paint, boolean fromWorkerThread) {
//            danmaku.padding = 10;  // 在背景绘制模式下增加padding
//            super.measure(danmaku, paint, fromWorkerThread);
//        }
//
//        @Override
//        public void drawBackground(BaseDanmaku danmaku, Canvas canvas, float left, float top) {
//            paint.setColor(0x8125309b);
//            canvas.drawRect(left + 2, top + 2, left + danmaku.paintWidth - 2, top + danmaku.paintHeight - 2, paint);
//        }
//
//        @Override
//        public void drawStroke(BaseDanmaku danmaku, String lineText, Canvas canvas, float left, float top, Paint paint) {
//            // 禁用描边绘制
//        }
//    }
//
//    private BaseDanmakuParser createParser(InputStream stream) {
//
//        if (stream == null) {
//            return new BaseDanmakuParser() {
//
//                @Override
//                protected Danmakus parse() {
//                    return new Danmakus();
//                }
//            };
//        }
//
//        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);
//
//        try {
//            loader.load(stream);
//        } catch (IllegalDataException e) {
//            e.printStackTrace();
//        }
//        BaseDanmakuParser parser = new BiliDanmukuParser();
//        IDataSource<?> dataSource = loader.getDataSource();
//        parser.load(dataSource);
//        return parser;
//
//    }
//
//    /**
//    @author 汤洪斌
//    @time 2019/6/21 0021 9:58
//    @version 1.0
//    @describe 初始化弹幕加载
//    */
//    public void initBlibliBarrage() {
//        // DanmakuView
//
//        // 设置最大显示行数
//        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
//        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 2); // 滚动弹幕最大显示5行
//        // 设置是否禁止重叠
//        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
//        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
//        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);
//
//        mContext = DanmakuContext.create();
//        mContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3)
//                .setDuplicateMergingEnabled(false).setScrollSpeedFactor(2.0f).setScaleTextSize(1.2f)
//        .setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer
////        .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
//        .setMaximumLines(maxLinesPair)
//        .preventOverlapping(overlappingEnablePair).setDanmakuMargin(40);
//        if (mDanmakuView != null) {
//            mParser = createParser(null);
//            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
//                @Override
//                public void updateTimer(DanmakuTimer timer) {
//                }
//
//                @Override
//                public void drawingFinished() {
//
//                }
//
//                @Override
//                public void danmakuShown(BaseDanmaku danmaku) {
////                    Log.d("DFM", "danmakuShown(): text=" + danmaku.text);
//                }
//
//                @Override
//                public void prepared() {
//                    mDanmakuView.start();
//                }
//            });
//            mDanmakuView.setOnDanmakuClickListener(new IDanmakuView.OnDanmakuClickListener() {
//
//                @Override
//                public boolean onDanmakuClick(IDanmakus danmakus) {
//                    Log.d("DFM", "onDanmakuClick: danmakus size:" + danmakus.size());
//                    BaseDanmaku latest = danmakus.last();
//                    if (null != latest) {
//                        Log.d("DFM", "onDanmakuClick: text of latest danmaku:" + latest.text);
//                        return true;
//                    }
//                    return false;
//                }
//
//                @Override
//                public boolean onDanmakuLongClick(IDanmakus danmakus) {
//                    return false;
//                }
//
//                @Override
//                public boolean onViewClick(IDanmakuView view) {
//                    return false;
//                }
//            });
//            mDanmakuView.prepare(mParser, mContext);
//            mDanmakuView.showFPS(false);
//            mDanmakuView.enableDanmakuDrawingCache(true);
//        }
//
//    }
//
//    public void onPause() {
//        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
//            mDanmakuView.pause();
//        }
//    }
//
//    public void onResume() {
//        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
//            mDanmakuView.resume();
//        }
//    }
//
//
//    public void onDestroy() {
//        if (mDanmakuView != null) {
//            // dont forget release!
//            mDanmakuView.release();
//            mDanmakuView = null;
//        }
//        executor.shutdownNow();
//    }
//
//    public void onBackPressed() {
//        if (mDanmakuView != null) {
//            // dont forget release!
//            mDanmakuView.release();
//            mDanmakuView = null;
//        }
//    }
//
//    public void addDanmaku(boolean islive,SpannableStringBuilder builder) {
//        BaseDanmaku danmaku = mContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
//        if (danmaku == null || mDanmakuView == null) {
//            return;
//        }
//        danmaku.text = builder;
//        danmaku.padding = 5;
//        danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
//        danmaku.isLive = islive;
//        danmaku.setTime(mDanmakuView.getCurrentTime() + 3000);
//        danmaku.textSize = 16 * (mParser.getDisplayer().getDensity() - 0.6f);
//        danmaku.textColor = Color.parseColor("#222222");
//        danmaku.textShadowColor = Color.WHITE;
//        // danmaku.underlineColor = Color.GREEN;
//        danmaku.borderColor = Color.TRANSPARENT;
//        mDanmakuView.addDanmaku(danmaku);
//
//    }
//
//    public void addDanmaKuShowTextAndImage(boolean islive,SpannableStringBuilder spannable) {
//        BaseDanmaku danmaku = mContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
//        danmaku.text = spannable;
//        danmaku.padding = 5;
//        danmaku.priority = 1;  // 一定会显示, 一般用于本机发送的弹幕
//        danmaku.isLive = islive;
//        danmaku.setTime(mDanmakuView.getCurrentTime() + 3000);
//        danmaku.textSize = 16f * (mParser.getDisplayer().getDensity() - 0.6f);
//        danmaku.textColor = Color.parseColor("#222222");
//        danmaku.textShadowColor = 0; // 重要：如果有图文混排，最好不要设置描边(设textShadowColor=0)，否则会进行两次复杂的绘制导致运行效率降低
//        danmaku.underlineColor = Color.TRANSPARENT;
//        mDanmakuView.addDanmaku(danmaku);
//    }
//
//
//    public void onConfigurationChanged(Configuration newConfig) {
//        DanmakuView danmakuView = (DanmakuView) mDanmakuView;
//        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            danmakuView.setVisibility(View.VISIBLE);
//        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            danmakuView.setVisibility(View.INVISIBLE);
//        }
//    }
}
