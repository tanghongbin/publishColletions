package com.yiqihudong.imageutil;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yiqihudong.myutils.R;
import com.yiqihudong.imageutil.utils.Constant;
import com.yiqihudong.imageutil.utils.Utils;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * GalleryImagesActivity
 *
 * @author weiwu.song
 */
public class GalleryImagesActivity extends Activity implements View.OnClickListener {
    //开始扫描
    protected final int SCAN_PHOTO_BEGIN = 0;
    //扫描完成
    protected final int SCAN_PHOTO_COMPLETE = 1;
    //改变目录
    protected final int CHANGE_PHOTO_DIR = 2;
    //改变目录 第一个特殊
    protected final int CHANGE_PHOTO_DIR_S = 3;
    //拍照ICON
    //protected final String CAMERA_ICON = "android.resource://com.wenjie.jiazhangtong/drawable/photo";
    //最多能选择图片数量
    public static int IMAGE_MAX = 99;
    //取值key
    public static final String RESULT_LIST = "ResultList";
    //拍照后返回REQUEST
    protected final int REQUEST_CAMERA = 1;
    //目录
    protected HashMap<String, List<String>> dirMap = new HashMap<>();
    //存储图片的选中情况
    protected HashMap<String, Boolean> mSelectMap = new HashMap<>();
    //拍照按钮和扫描到的图片列表
    protected List<String> galleryList = new ArrayList<>();
    //扫描到的相册列表
    protected ArrayList<String> scanList = new ArrayList<>();
    protected GalleryImagesAdapter adapter;
    protected GridView mGridView;
    //选择文件夹
    protected TextView changeDirButton = null;
    //拍照后图片URI
    protected Uri imageUri = null;
    //切换目录列表
    protected ListView changeDirListView = null;
    protected PhotoPopAdapter photoPopAdapter = null;
    protected FrameLayout changeDirListContent = null;
    //切换目录背景
    protected FrameLayout changeDirBg = null;
    //当前还能选择的图片数
    protected int canSelect = IMAGE_MAX;
    //底部动画Y轴偏移量
    protected int mediaY = 570;
    //当前显示的目录
    protected String nowDir = "";
    protected ArrayList<String> chooseList = null;
    protected boolean isCut = false;

    protected static  int SIGN_BOARD ;
    protected TextView common_head_title_tv;
    protected ImageButton common_head_title_img_bt;
    protected Button common_head_title_right;

    protected Options mOptions;//颜色背景等配置
    private ViewGroup head_container;
    private ViewGroup whole_view;

    /**
     * 被选中的图片列表 key
     */


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.imagescan_images_activity);
        initSelectPic((ArrayList) getIntent().getSerializableExtra(Constant.ALREADY_SELECT_KEY));
        SIGN_BOARD = (getIntent().getIntExtra("signBoard",0));
        IMAGE_MAX = getIntent().getIntExtra("image_max",IMAGE_MAX);
        mOptions = (Options) getIntent().getSerializableExtra("options");
        canSelect = IMAGE_MAX;
        initWidget();

        mHandler.sendEmptyMessage(SCAN_PHOTO_BEGIN);
    }

    protected void initSelectPic(ArrayList<String> pics) {
        if (null != pics) {
            chooseList = pics;
        } else {
            chooseList = new ArrayList<>();
        }

        if (pics == null || pics.size() <= 0) {
//            setTitleText("选择图片", "确定");
            return;
        }
//        setTitleText("选择图片", "确定" +
//                "(" + pics.size() + "/" + IMAGE_MAX + ")");
        for (String path : pics) {
            mSelectMap.put(path, true);
        }
    }

    /**
     * init widget
     */
    protected void initWidget() {
        common_head_title_tv = (TextView) findViewById(R.id.common_head_title_tv);
        common_head_title_img_bt = (ImageButton) findViewById(R.id.common_head_title_img_bt);
        common_head_title_right = (Button) findViewById(R.id.common_head_title_right);
        head_container = (ViewGroup)findViewById(R.id.head_container);
        whole_view = (ViewGroup)findViewById(R.id.whole_view);
        common_head_title_img_bt.setOnClickListener(this);
        common_head_title_tv.setOnClickListener(this);
        common_head_title_right.setOnClickListener(this);
        changeDirButton = (TextView) findViewById(R.id.photo_gallery_dir);
        changeDirButton.setOnClickListener(this);

        changeDirListView = (ListView) findViewById(R.id.change_dir_list);
        changeDirListContent = (FrameLayout) findViewById(R.id.change_dir_list_content);
        changeDirBg = (FrameLayout) findViewById(R.id.change_dir_bg);
        changeDirBg.setOnClickListener(this);

        mGridView = (GridView) findViewById(R.id.child_grid);
//        galleryList.add(0, "");
//        scanList.add(0, "");
        adapter = new GalleryImagesAdapter(galleryList, canSelect, mSelectMap, mGridView);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 && adapter.isFirstSpecial()) {//判断是否是拍照按钮
                    ArrayList<String> selected = getSelectItems();
                    if (null != selected && selected.size() < IMAGE_MAX) {
                        chooseList.clear();
                        chooseList.addAll(selected);
                        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        String filename = timeStampFormat.format(System.currentTimeMillis());
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, filename);
                        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        openCameraIntent.addCategory(Intent.CATEGORY_DEFAULT);
                        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(openCameraIntent, REQUEST_CAMERA);
                    } else {
                        Toast.makeText(GalleryImagesActivity.this, getResources()
                                .getString(R.string.can_select_photo_max)+IMAGE_MAX + "",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Intent intent = new Intent(GalleryImagesActivity.this, PhotoWallActivity.class);
                    ArrayList<String> simplePic = new ArrayList<>();
                    simplePic.add(galleryList.get(position));
                    intent.putStringArrayListExtra(PhotoWallActivity.KEY_LIST, simplePic);
                    startActivity(intent);
                }
            }
        });
        initOptions();
    }

    /**
     * 初始化配置信息
     */
    protected void initOptions() {
        if (mOptions == null) {
            return;
        }
        try {
            if (!TextUtils.isEmpty(mOptions.getTitleBgColor())){
                head_container.setBackgroundColor(Color.parseColor(mOptions.getTitleBgColor()));
            }
            if (!TextUtils.isEmpty(mOptions.getTitleColor())){
                common_head_title_tv.setTextColor(Color.parseColor(mOptions.getTitleColor()));
            }
            if (!TextUtils.isEmpty(mOptions.getConfirmColor())){
                common_head_title_right.setTextColor(Color.parseColor(mOptions.getConfirmColor()));
            }
            if (!TextUtils.isEmpty(mOptions.getWindowBgColor())){
                whole_view.setBackgroundColor(Color.parseColor(mOptions.getWindowBgColor()));
            }

        }catch (Exception e){
            Log.d(GalleryImagesActivity.class.getName(),"颜色配置解析异常"+e);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {//拍照完成
                Uri uri = null;
                if (data != null) {
                    uri = data.getData();
                }
                if (uri == null) {
                    if (imageUri != null) {
                        uri = imageUri;
                    } else {
                        return;
                    }
                }
                String path = ScanPhoto_V1.getPath(this, uri);
                chooseList.add(path);
                chooseImageFinish(chooseList, true);
            }
        } else if (resultCode == RESULT_CANCELED) {//取消拍照 删除之前存入相册的占位图片
            try {
                getContentResolver().delete(imageUri, null, null);
            } catch (Exception e) {
                e.printStackTrace();
                if (imageUri != null) {
                    String path = ScanPhoto_V1.getPath(this, imageUri);
                    if (!Utils.stringIsNull(path)) {
                        File file = new File(path);
                        if (file != null && file.exists()) {
                            file.delete();
                        }
                    }
                }
            }
        }
    }

    /**
     * 显示改变目录view
     */
    protected void showChangeDirView() {
        doBottomSlidAnimations(changeDirListContent, -mediaY);
        changeDirBg.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏改变目录view
     */
    protected void hideChangeDirView() {
        doBottomSlidAnimations(changeDirListContent, mediaY);
        changeDirBg.setVisibility(View.GONE);
    }

    /**
     * 执行Y轴平移动画，并保持动画结束时状态
     *
     * @param moveLayout 要执行动画的view
     * @param deltaY     Y轴偏移量
     */
    protected void doBottomSlidAnimations(View moveLayout, float deltaY) {
        if (Build.VERSION.SDK_INT < 11) {
            com.nineoldandroids.animation.ObjectAnimator animY
                    = com.nineoldandroids.animation.ObjectAnimator.ofFloat(moveLayout, "translationY", deltaY);
            animY.setDuration(300);
            animY.start();
        } else {
            ObjectAnimator animY = ObjectAnimator.ofFloat(moveLayout, "translationY", deltaY);
            animY.setDuration(300);
            animY.start();
        }
    }

    /**
     * 初始化change ListView
     */
    protected void initChangeDirList() {
        final List<PhotoDirModel> dirLN = new ArrayList<>();
        PhotoDirModel allPDM = new PhotoDirModel();
        allPDM.setDirName(getString(R.string.all_photo));
        allPDM.setPaths(getImages(scanList));
        dirLN.add(allPDM);
        for (Map.Entry<String, List<String>> entry : dirMap.entrySet()) {
            List<String> tempL = entry.getValue();
            if (entry != null && tempL != null && tempL.size() > 0) {
                PhotoDirModel tempPDM = new PhotoDirModel();
                tempPDM.setDirName(entry.getKey());
                tempPDM.setPaths((ArrayList) tempL);
                if (!entry.getKey().contains("CGImage") || !entry.getKey().equals("CGImage")) {
                    dirLN.add(tempPDM);
                }
            }
        }
        photoPopAdapter = new PhotoPopAdapter(this, dirLN);
        changeDirListView.setAdapter(photoPopAdapter);
        changeDirListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = new Message();
                message.what = CHANGE_PHOTO_DIR;
                if (position == 0) {
                    message.obj = scanList;
                    message.what = CHANGE_PHOTO_DIR_S;
                    mHandler.sendMessage(message);
                    nowDir = getString(R.string.all_photo);
                } else {
                    PhotoDirModel photoDirModel = dirLN.get(position);
                    message.obj = dirMap.get(photoDirModel.getDirName());
                    mHandler.sendMessage(message);
                    nowDir = photoDirModel.getDirName();
                }
                changeDirButton.setText(nowDir);
                photoPopAdapter.notifyDataSetChanged();
                hideChangeDirView();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.photo_gallery_dir){
            if (scanList == null || scanList.size() <= 0) {
                return;
            }
            if (mediaY == 570) {
                int tempH = changeDirListView.getMeasuredHeight();
                if (tempH > 380) {
                    mediaY = tempH;
                }
            }

            if (changeDirBg.getVisibility() == View.VISIBLE) {
                hideChangeDirView();
            } else {
                if (photoPopAdapter == null) {
                    initChangeDirList();
                }
                showChangeDirView();
            }
        }

        if(v.getId() == R.id.change_dir_bg)
        hideChangeDirView();
        if(v.getId() == R.id.common_head_title_img_bt){
            finish();
        }
        if(v.getId() == R.id.common_head_title_right){
            titleRightButtonListener();
        }




    }

    protected Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCAN_PHOTO_BEGIN://开始扫描
                    scanPhoto_v2();
                    break;
                case SCAN_PHOTO_COMPLETE://扫描结束
                    galleryList.clear();
                    galleryList.addAll(getImages(scanList));
                    Collections.reverse(galleryList);
                    galleryList.add(0, "");
                    adapter.notifyDataSetChanged();
                    nowDir = getString(R.string.all_photo);
                    break;
                case CHANGE_PHOTO_DIR_S://改变目录 第一个为拍照按钮
                    List<String> showListS = (ArrayList) msg.obj;
                    galleryList.clear();
                    galleryList.addAll(getImages(showListS));
                    Collections.reverse(galleryList);
                    galleryList.add(0, "");
                    adapter.setFirstSpecial(true);
                    adapter.notifyDataSetChanged();
                    break;
                case CHANGE_PHOTO_DIR://改变目录
                    List<String> showList = (ArrayList) msg.obj;
                    galleryList.clear();
                    galleryList.addAll(getImages(showList));
                    Collections.reverse(galleryList);
                    adapter.setFirstSpecial(false);
                    adapter.notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    protected ArrayList<String> getImages(List<String> list) {
        ArrayList<String> imgs = new ArrayList<>();
        for (String path : list) {
            if (!Utils.stringIsNull(path) && !path.contains("CGImage")) {
                imgs.add(path);
            }
        }
        return imgs;
    }

    /**
     * 获取当前显示目录名称
     *
     * @return 当前显示目录名称
     */
    public String getShowWitchDir() {
        return nowDir;
    }

    public void titleRightButtonListener() {
        List<String> selected = getSelectItems();
        chooseImageFinish(selected, false);
    }

    public void selectChange(HashMap<String, Boolean> mSelectMap) {
        if(IMAGE_MAX == 99){
            setTitleText("选择图片","确定"+
                    "(" + mSelectMap.size() + ")"
            );
        }else {
            setTitleText("选择图片", "确定" +
                    "(" + mSelectMap.size() + "/" + IMAGE_MAX + ")");
        }
        // 根据数量变换做的操作
        opreationByCountChange(mSelectMap.size());
    }

    /**
     * 数量变换时
     * @param size
     */
    private void opreationByCountChange(int size) {
        if (mOptions == null) return;
        try {
            if (size == 0) {
                if (!TextUtils.isEmpty(mOptions.getConfirmColor())) {
                    common_head_title_right.setTextColor(Color.parseColor(mOptions.getConfirmColor()));
                }
            }else {
                if (!TextUtils.isEmpty(mOptions.getSelectedConfirmColor())) {
                    common_head_title_right.setTextColor(Color.parseColor(mOptions.getSelectedConfirmColor()));
                }
            }
        }catch (Exception e){
            Log.i(GalleryImagesActivity.class.getName(),"选中时变换颜色出错");
            e.printStackTrace();
        }
    }

    protected void setTitleText(String s1, String s) {
        common_head_title_tv.setText(s1+s);
    }

    public ArrayList<String> getSelectItems() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (Iterator<Map.Entry<String, Boolean>> it = mSelectMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, Boolean> entry = it.next();
            if (entry.getValue()) {
                arrayList.add(entry.getKey());
            }
        }
        return arrayList;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            needBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    protected void needBack() {
        if (changeDirBg != null && changeDirBg.getVisibility() == View.VISIBLE) {
            hideChangeDirView();
        } else {
            finish();
        }
    }

    /**
     * 返回图片到上一页
     *
     * @param images   需要返回上一页的图片数组
     * @param isCamera 是否是拍照
     */
    protected void chooseImageFinish(List<String> images, boolean isCamera) {
        if (null != images && images.size() > 0) {
            if (isCut) {

            } else {

                Intent intent = new Intent();
                intent.putStringArrayListExtra(RESULT_LIST, (ArrayList) images);
                setResult(RESULT_OK, intent);
                finish();

                if(SelectCallbackManager.getInstance().getSelectPicCallback() != null){
                    SelectCallbackManager.getInstance().getSelectPicCallback().selectPic(images);
                    SelectCallbackManager.getInstance().clearCallback();
                }
            }
        } else {
            if (isCamera) {

            } else {
                Toast.makeText(GalleryImagesActivity.this, getString(R.string.please_choose_image), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 扫描图片
     */
    protected void scanPhoto_v2() {
        ScanPhoto_V2.scanMediaDir(this, scanList, dirMap);

        //通知Handler扫描图片完成
        mHandler.sendEmptyMessage(SCAN_PHOTO_COMPLETE);
    }

    /**
     * 获取view高度
     *
     * @param view 要获取高度的view
     *
     */
    protected void getViewHeight(final View view) {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                mediaY = view.getHeight() + 2;
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    /**
     * 扫描图片 不再使用
     */
    @Deprecated
    protected void scanPhoto_v1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                scanList = new ArrayList<>();
                String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
                ScanPhoto_V1.getImageFileFromDir(dir, scanList);

                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(SCAN_PHOTO_COMPLETE);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        File appDir = new File(Environment.getExternalStorageDirectory() + "/CGImage");
        if (appDir.exists()) {
            Utils.deleteFolder(appDir);
        }
    }

    /**
     * 标题颜色，背景等配置 #666666
     */
    public static class Options implements Serializable{
        protected String titleColor;//标题颜色
        protected String confirmColor;//没有选中照片时的确认颜色
        protected String selectedConfirmColor; // 选中照片时的确认颜色
        protected String titleBgColor; //标题背景色
        protected String windowBgColor; // 页面背景


        public String getTitleColor() {
            return titleColor;
        }

        public Options titleColor(String titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public String getConfirmColor() {
            return confirmColor;
        }

        public Options setConfirmColor(String confirmColor) {
            this.confirmColor = confirmColor;
            return this;
        }

        public String getSelectedConfirmColor() {
            return selectedConfirmColor;
        }

        public Options setSelectedConfirmColor(String selectedConfirmColor) {
            this.selectedConfirmColor = selectedConfirmColor;
            return this;
        }

        public String getTitleBgColor() {
            return titleBgColor;
        }

        public Options setTitleBgColor(String titleBgColor) {
            this.titleBgColor = titleBgColor;
            return this;
        }

        public String getWindowBgColor() {
            return windowBgColor;
        }

        public Options setWindowBgColor(String windowBgColor) {
            this.windowBgColor = windowBgColor;
            return this;
        }
    }
}