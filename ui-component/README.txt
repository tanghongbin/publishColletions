android 常用的view

1.headerview

2.customwheelview

3.banner    -  implementation "com.youth.banner:banner:1.4.10"

4.switchbutton    -  api 'com.github.zcweng:switch-button:0.0.3@aar'

5.CustomClearEdit

6.CustomSearchEdit

7.CustomCircleImage -这个有时候测量会发生问题
            CircleImageView -推荐使用这个        -implementation 'de.hdodenhof:circleimageview:3.0.0'

8.BottomTab  - implementation 'me.majiajie:pager-bottom-tab-strip:2.3.0'\

9.ShadowUtils   - 给view添加阴影背景的  implementation 'com.github.JuHonggang:ShadowDrawable:0.1'

example:
      ShadowDrawable.setShadowDrawable(mNameEdit, resources.getColor(R.color.white),
                  dip2px(this,6f),
                  resources.getColor(R.color.third_transparent), dip2px(this,6f), 0, 0)


