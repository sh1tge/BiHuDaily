package cn.ml.saddhu.bihudaily.engine.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sadhu on 2017/2/11.
 * Email static.sadhu@gmail.com
 * Describe: 主编
 */
public class Editor {
    /**
     * tag : http://www.zhihu.com/people/moheng-esther
     * bio : 树上的女爵
     * id : 79
     * avatar : http://pic1.zhimg.com/0a6456810_m.jpg
     * name : 刘柯
     */
    public String url;
    public String title;
    public String bio;
    public long id;
    public String avatar;
    public String name;
    @SerializedName("zhihu_url_token")
    public String zhihuUrlToken;

    @Override
    public String toString() {
        return "Editor{" +
                "tag='" + url + '\'' +
                ", title='" + title + '\'' +
                ", bio='" + bio + '\'' +
                ", id=" + id +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", zhihuUrlToken='" + zhihuUrlToken + '\'' +
                '}';
    }
}
