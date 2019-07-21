package publish.tang.common.commonutils.mediautils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import publish.tang.common.commonutils.mediautils.bean.MediaResultBean;

import java.util.ArrayList;
import java.util.List;

/***
 * 媒体工具类，获取本地音乐，视频等
 */
public class MediaUtils {
    /**
     @author 汤洪斌
     @time 2019/7/2 0002 9:26
     @version 1.0
     @describe 检索本地视频,这个是耗时操作，调用者处理异步问题
     */
    public static List<MediaResultBean> getLocalVideoFromDataBase(
            Context context ,
            int pageNo,
            int pageSize,
            String keywords) {

        if (pageSize <= 0) {
            pageSize = 10;
        }
        String selectionStr = MediaStore.Audio.Media.TITLE + " LIKE ? ";
        String [] selections = new String[]{
                "%"+ keywords + "%"
        };
            List<MediaResultBean> list = null;
            int offsetCount = (pageNo - 1) * pageSize;
            String queryOrderStr = MediaStore.Video.Media._ID + "  LIMIT " + pageSize + " OFFSET " + offsetCount;

            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, selectionStr,
                    selections, queryOrderStr);
        list = composeQueryResult(list, cursor);
        return list;
     }


    /**
     @author 汤洪斌
     @time 2019/7/2 0002 9:26
     @version 1.0
     @describe 检索本地音乐,这个是耗时操作，调用者处理异步问题
     */
    public static List<MediaResultBean> getLocalAudioFromDataBase(
            Context context ,
            int pageNo,
            int pageSize,
            String keywords) {

        if (pageSize <= 0) {
            pageSize = 10;
        }
        String selectionStr = MediaStore.Audio.Media.TITLE + " LIKE ? ";
        String [] selections = new String[]{
                "%"+ keywords + "%"
        };
        List<MediaResultBean> list = null;
        int offsetCount = (pageNo - 1) * pageSize;
        String queryOrderStr = MediaStore.Audio.Media._ID + "  LIMIT " + pageSize + " OFFSET " + offsetCount;


        Cursor cursor =context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                selectionStr,  selections,
                queryOrderStr
        );

        list = composeQueryResult(list, cursor);
        return list;
    }

    private static List<MediaResultBean> composeQueryResult(List<MediaResultBean> list, Cursor cursor) {
        if (cursor != null) {
            list = new ArrayList<>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(
                        cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                    );
                String title = cursor
                        .getString(
                        cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)
                        );
                String album = cursor
                        .getString(
                        cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM)
                        );
                String artist = cursor
                        .getString(
                        cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST)
                        );
                String displayName = cursor
                        .getString(
                        cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                        );
                String mimeType = cursor
                        .getString(
                        cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)
                        );
                String path = cursor
                        .getString(
                        cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                        );
                long duration = cursor
                        .getLong(
                        cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                        );
                long size = cursor
                        .getLong(
                        cursor
                                .getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
                        );
                MediaResultBean video =new MediaResultBean();
                video.setId(id);
                video.setArtist(artist);
                video.setName(title);
                video.setSize(size);
                video.setUrl(path);
                video.setAlbum(album);
                video.setDuration(duration);
                list.add(video);
            }
            cursor.close();
        }
        return list;
    }

}
