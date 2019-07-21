package publish.tang.common.commonutils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

public class ToastUtils {

    public static DialogInterface showDialog(Context context,String title,String content,AlertDialog.OnClickListener positionListener) {
       return showDialog(context,title,content,null,positionListener,null);
    }
    public static DialogInterface showDialog(Context context,String title,String content,int icon,AlertDialog.OnClickListener positionListener) {
        return showDialog(context,title,content,icon,null,positionListener,null);
    }
    public static DialogInterface showDialog(Context context,View customView,DialogInterface.OnClickListener listener){
        return showDialog(context,"提示","",customView,listener,null);
    }
    public static DialogInterface showDialog(Context context,View customView){
        return showDialog(context,"提示","",customView,null,null);
    }
    public static DialogInterface showDialog(Context context,View customView,boolean isHint){
        String hint = isHint ? "提示" : "";
        return showDialog(context,hint,"",customView,null,null);
    }
    public static DialogInterface showDialog(Context context,String title,String content,AlertDialog.OnClickListener positionListener,AlertDialog.OnClickListener cancle) {
        return showDialog(context,title,content,null,positionListener,cancle);
    }
    public static DialogInterface showDialog(Context context,String title,String content,View view,AlertDialog.OnClickListener positionListener,AlertDialog.OnClickListener cancle) {

        return showDialog(context,title,content,-1,view,positionListener,cancle);
    }

    public static DialogInterface showDialog(Context context,String title,String content,int icon,View view,AlertDialog.OnClickListener positionListener,AlertDialog.OnClickListener cancle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(!Utils.stringIsNull(title)) builder.setTitle(title);
        if(!Utils.stringIsNull(content)) builder.setMessage(content);
        if(view != null) builder.setView(view);
        if(positionListener != null) builder.setPositiveButton("确定",positionListener);
        if(positionListener != null) builder.setNegativeButton("取消",cancle);
        if(icon > 0)builder.setIcon(icon);
        AlertDialog dialog =  builder.create();
        dialog.show();
        return dialog;
    }


    public static void showMessage(Context context, String s) {
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}
