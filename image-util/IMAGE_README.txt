/**
 *
 * 图片选择的帮助类
 * !!!!!!!!!!使用框架前必读此说明
 * 1.上下文环境的初始化 - 在application的onCreate方法中调用
 *
 * ContextManager.init(this)
 *
 *
 *
 * 2.可使用的文件地址授权，在application的onCreate方法中调用
 *
 * ImageSelectedHelper.initProviderAuth(this,authroies)方法
 *
 *
 * 授权的authroies必须以ImageSingleChooseActivity.SPECIAL_CUSTOM_URL_HEADER为前缀
 * 再加上自定义的字符串如：
 *
 *        authroies = ImageSingleChooseActivity.SPECIAL_CUSTOM_URL_HEADER
 *             + (自定义字符串如) com.example
 *
 *
 *
 * 3.同时还必须要在app主工程的AndroidManifest.xml文件下进行如下声明,
 *
 *
 *

       <provider
          android:name=".module.CustomFileProvider"
          android:authorities="com.publishgallery.custom.auth.mgbapp.image"
          android:exported="false"
          android:grantUriPermissions="true">
      <meta-data
          android:name="android.support.FILE_PROVIDER_PATHS"
          android:resource="@xml/file_paths"></meta-data>
       </provider>


 *       <provider>标签内容定义如下:
 *          1. 这里的authorities必须和initProviderAuth()方法的authroies保持一致,
 *             如  authroies = ImageSingleChooseActivity.SPECIAL_CUSTOM_URL_HEADER
 * *             + (自定义字符串如) com.example
 * *         2.name为自定义的FileProvider的子类,是个空类，example:
 * <p>
 *            public class CustomFileProvider extends FileProvider {
 * <p>
 *           }
 *
 *
 *        <meta>标签内的内容不用更改
 */