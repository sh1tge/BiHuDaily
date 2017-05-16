package cn.ml.saddhu.bihudaily.mvp.view;

import java.util.List;

import cn.ml.saddhu.bihudaily.engine.domain.Story;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;

/**
 * Created by sadhu on 2016/11/15.
 * Email static.sadhu@gmail.com
 * Describe: 文章列表
 */
public interface StoryListView {
    void setFirstPageData(StoryInfo info);

    void onLoadMoreSuccess(List<Story> info);

    void notifyItemChange(int position);
}
