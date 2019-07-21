package publish.tang.common.commonutils.mediautils.bean;

/***
 * 音乐和视频对象
 */
public class MediaResultBean {
    String name;
    long size;
    String url;
    long duration;
    String cover;
    private String album;
    private int id;
    private String artist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "MediaResultBean{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", url='" + url + '\'' +
                ", duration=" + duration +
                ", cover='" + cover + '\'' +
                ", album='" + album + '\'' +
                ", id=" + id +
                ", artist='" + artist + '\'' +
                '}';
    }
}
