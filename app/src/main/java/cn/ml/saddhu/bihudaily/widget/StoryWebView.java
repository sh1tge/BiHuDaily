package cn.ml.saddhu.bihudaily.widget;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.ml.saddhu.bihudaily.engine.domain.Story;
import cn.ml.saddhu.bihudaily.engine.imageloader.ImageDownloadManager;
import cn.ml.saddhu.bihudaily.engine.util.ActivityUtils;
import cn.ml.saddhu.bihudaily.engine.util.HTMLUtils;
import cn.ml.saddhu.bihudaily.engine.util.ConfigurationManager;
import cn.ml.saddhu.bihudaily.mvp.view.impl.activity.ImageViewerActiviy_;

/**
 * Created by sadhu on 2016/12/5.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class StoryWebView extends WebView {
    private Context mContext;
    private Story c;
    private static final int WHAT_IMAGE_LOADING_COMPLETE = 1;
    private static final int WHAT_IMAGE_LOADING_ERROR = 2;
    private static final int WHAT_IMAGE_START_LOADING = 3;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_IMAGE_LOADING_COMPLETE:
                    try {
                        ImageUrlInfo obj = (ImageUrlInfo) msg.obj;
                        loadJsMethod("onImageLoadingComplete", new String[]{URLEncoder.encode(obj.oldUrl, "UTF-8"), obj.newFile});
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case WHAT_IMAGE_START_LOADING:
                    try {
                        String oldUrl = (String) msg.obj;
                        loadJsMethod("onImageLoadingComplete", new String[]{URLEncoder.encode(oldUrl, "UTF-8"), HTMLUtils.DEFAULT_IMAGE_URI});
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case WHAT_IMAGE_LOADING_ERROR:
                    try {
                        String oldUrl = (String) msg.obj;
                        loadJsMethod("onImageLoadingComplete", new String[]{URLEncoder.encode(oldUrl, "UTF-8"), HTMLUtils.DEFAULT_DOWNLOAD_IMAGE_URI});
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public StoryWebView(Context context) {
        this(context, null);
    }

    public StoryWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StoryWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        // 是否是夜间模式
        if (ConfigurationManager.isDark(mContext)) {
            this.setBackgroundColor(Color.argb(1, 0, 0, 0));
        }
        this.getSettings().setAllowFileAccess(true);
        this.getSettings().setAppCacheEnabled(true);
        this.getSettings().setAppCachePath(context.getApplicationContext().getDir("cache", 0).getPath());
        this.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        this.getSettings().setLoadWithOverviewMode(true);
        this.getSettings().setDomStorageEnabled(true);
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.getSettings().setBuiltInZoomControls(true);
        this.getSettings().setDisplayZoomControls(false);
        this.getSettings().setLoadsImagesAutomatically(true);
        this.setWebViewClient(new MyWebViewClient(this, context));
        this.addJavascriptInterface(this, "ZhihuDaily");
    }

    private void loadJsMethod(String methodName, String[] arguments) {
        this.loadUrl("javascript:" + methodName + "(" + ("\'" + TextUtils.join("\',\'", arguments) + "\'") + ");");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        Logger.d("onDetachedFromWindow");
    }

    @JavascriptInterface
    public void clickToLoadImage(final String path) {
        if (!TextUtils.isEmpty(path)) {
            post(new Runnable() {
                @Override
                public void run() {
//                    Message message = Message.obtain();
//                    message.what = WHAT_IMAGE_START_LOADING;
//                    message.obj = path;
//                    mHandler.sendMessage(message);
                    try {
                        loadJsMethod("onImageLoadingComplete", new String[]{URLEncoder.encode(path, "UTF-8"), HTMLUtils.DEFAULT_IMAGE_URI});
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    ImageDownloadManager.getInstance().addTask(path, new ImageDownloadManager.DownloadListener() {
                        @Override
                        public void onSuccuss(String oldUrl, String newFile) {
                            Message message = Message.obtain();
                            message.what = WHAT_IMAGE_LOADING_COMPLETE;
                            message.obj = new ImageUrlInfo(oldUrl, newFile);
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onError(String oldUrl) {
                            Message message = Message.obtain();
                            message.what = WHAT_IMAGE_LOADING_ERROR;
                            message.obj = oldUrl;
                            mHandler.sendMessage(message);
                        }
                    });
                }
            });
        }
    }

    @JavascriptInterface
    public void loadImage(final String url) {
        if (!TextUtils.isDigitsOnly(url)) {
            post(new Runnable() {
                @Override
                public void run() {
                    ImageDownloadManager.getInstance().addTask(url, new ImageDownloadManager.DownloadListener() {
                        @Override
                        public void onSuccuss(String oldUrl, String newFile) {
                            Message message = Message.obtain();
                            message.what = WHAT_IMAGE_LOADING_COMPLETE;
                            message.obj = new ImageUrlInfo(oldUrl, newFile);
                            mHandler.sendMessage(message);
                        }

                        @Override
                        public void onError(String oldUrl) {
                            Message message = Message.obtain();
                            message.what = WHAT_IMAGE_LOADING_ERROR;
                            message.obj = oldUrl;
                            mHandler.sendMessage(message);
                        }
                    });
                }
            });
        }
    }

    @JavascriptInterface
    public void openImage(String url) {
        ImageViewerActiviy_.intent(getContext()).extra_image_url(url).start();
        Logger.i("openImage :%s", url);
    }

    public final class MyWebViewClient extends WebViewClient {
        private Context mContext;

        public MyWebViewClient(StoryWebView webView, Context context) {
            super();
            this.mContext = context;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            long id = 1;
            boolean result = false;
            if (!TextUtils.isEmpty(url)) {
                Uri schemeUri = Uri.parse(url);
                if (url.contains("zhihu.com")) {
                    ActivityUtils.startActivityByUrl(this.mContext, url);
                    result = true;
                } else if (url.contains("zhihu-theme-subscribe")) {
                    result = true;
                } else {
                    if (url.startsWith("zhihudaily")) {
                        if (url.contains("theme")) {
                            //统计进入方式
                            //com.zhihu.daily.android.randomByte.a.a("User Behavior", "Enter Theme From News Bottom", StoryWebView.c(this.a).getAnalyticsLabel(), Long.valueOf(id));
                        } else if (url.contains("section")) {
                            //统计进入方式
                            //com.zhihu.daily.android.randomByte.a.a("User Behavior", "Enter Section From News Bottom", StoryWebView.c(this.a).getAnalyticsLabel(), Long.valueOf(id));
                        }
                    }

                    HitTestResult hitTestResult = view.getHitTestResult();
                    if (hitTestResult == null) {
                        return result;
                    }

                    if (hitTestResult.getType() == HitTestResult.SRC_ANCHOR_TYPE) {
                        ActivityUtils.startActivityByUrl(this.mContext, url);
                        return true;
                    }

                    if (hitTestResult.getType() == HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                        ActivityUtils.startActivityByUrl(this.mContext, url);
                        return true;
                    }

                    if (hitTestResult.getType() != 0) {
                        return result;
                    }

                    if (TextUtils.isEmpty(view.getOriginalUrl())) {
                        return result;
                    }

                    if (schemeUri.getScheme() == null) {
                        return result;
                    }

                    if (!schemeUri.getScheme().equals("http") && !schemeUri.getScheme().equals("https")) {
                        return result;
                    }

                    if (url.contains(view.getOriginalUrl())) {
                        return result;
                    }
                    ActivityUtils.startActivityByUrl(this.mContext, url);
                    result = true;
                }
            }

            return result;
        }
    }

    private class ImageUrlInfo {
        public String oldUrl;
        public String newFile;

        public ImageUrlInfo(String oldUrl, String newFile) {
            this.oldUrl = oldUrl;
            this.newFile = newFile;
        }
    }

}
