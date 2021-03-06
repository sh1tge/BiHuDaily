package cn.ml.saddhu.bihudaily.engine.util;

import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sadhu on 2017/2/24.
 * Email static.sadhu@gmail.com
 */
public class HTMLUtils {
    public static String DEFAULT_IMAGE_URI = "file:///android_asset/default_pic_content_image_loading_light.png";
    public static String DEFAULT_DOWNLOAD_IMAGE_URI = "file:///android_asset/default_pic_content_image_download_light.png";

    public static String parseBody(Context context, String body) {
        boolean isNoPicMode = ConfigurationManager.isNoPicMode(context);
        Document document = Jsoup.parse(body);
        for (Element next : document.getElementsByTag("img")) {
            String src = next.attr("src");
            next.attr("zhimg-src", src);
            String md5 = MD5Utils.getMD5(src);
            File file = new File(FileUtils.getStoryImageCacheFile(), md5 + 1);
            boolean inDiskCacheSync = file.exists();
            // 无图模式并且图片没有缓存
            if (isNoPicMode && !inDiskCacheSync) {
                // 点击下载
                next.attr("src", DEFAULT_DOWNLOAD_IMAGE_URI);
            } else {
                // 进入详情
                next.attr("src", DEFAULT_IMAGE_URI);
            }
            next.attr("onclick", "onImageClick(this)");
        }

        return document.html();
    }

    public static HashSet<String> parseAttribute(String htmlContent, String attribute) {
        HashSet<String> arrayList = new HashSet<>();
        if (htmlContent == null || htmlContent.length() == 0) {
            return arrayList;
        }
        Iterator it = Jsoup.parse(htmlContent).getElementsByTag("img").iterator();
        while (it.hasNext()) {
            String attr = ((Element) it.next()).attr(attribute);
            if (attr != null && attr.length() > 0) {
                arrayList.add(attr);
            }
        }
        return arrayList;
    }

}
