package cn.ml.saddhu.bihudaily.engine.domain;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sadhu on 2016/11/15.
 * Email static.sadhu@gmail.com
 * Describe: 文章列表顶部轮播
 */
@Entity
public class TopStory implements Parcelable {
    public String image;
    public int type;
    @Id
    public String id;
    public String ga_prefix;
    public String title;
    @Generated(hash = 1112519787)
    public TopStory(String image, int type, String id, String ga_prefix,
            String title) {
        this.image = image;
        this.type = type;
        this.id = id;
        this.ga_prefix = ga_prefix;
        this.title = title;
    }
    @Generated(hash = 1380504053)
    public TopStory() {
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getGa_prefix() {
        return this.ga_prefix;
    }
    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeInt(this.type);
        dest.writeString(this.id);
        dest.writeString(this.ga_prefix);
        dest.writeString(this.title);
    }

    protected TopStory(Parcel in) {
        this.image = in.readString();
        this.type = in.readInt();
        this.id = in.readString();
        this.ga_prefix = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<TopStory> CREATOR = new Parcelable.Creator<TopStory>() {
        @Override
        public TopStory createFromParcel(Parcel source) {
            return new TopStory(source);
        }

        @Override
        public TopStory[] newArray(int size) {
            return new TopStory[size];
        }
    };
}
