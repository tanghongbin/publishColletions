package publish.tang.common.commonutils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by tangt on 2016/4/21.
 */
public class FragmentUtils {


    public static void add(FragmentActivity activity,Fragment fragment,int containerId,String tag){
        activity.getSupportFragmentManager().beginTransaction().add(containerId,fragment,tag).commit();
    }
    public static void add(FragmentActivity activity,Fragment fragment,int containerId){
        add(activity,fragment,containerId,"");
    }

    public static Fragment find(FragmentActivity activity,String tag){
       return activity.getSupportFragmentManager().findFragmentByTag(tag);
    }
    public static void replace(FragmentActivity activity,Fragment fragment,int containerId,String tag){
        activity.getSupportFragmentManager().beginTransaction().replace(containerId,fragment,tag).commit();
    }
    public static void replace(FragmentActivity activity,Fragment fragment,int containerId){
        replace(activity,fragment,containerId,"");
    }
    public static Fragment findFromFragment(Fragment parentFragment,String tag){
        return parentFragment.getChildFragmentManager().findFragmentByTag(tag);
    }
    public static void addToFragment(Fragment parentFragment,Fragment childFragment,int containerId,String tag){
        parentFragment.getChildFragmentManager().beginTransaction().add(containerId,childFragment,tag).commit();
    }
    public static void replaceToFragment(Fragment parentFragment,Fragment childFragment,int containerId,String tag){
        parentFragment.getChildFragmentManager().beginTransaction().replace(containerId,childFragment,tag).commit();
    }


}
