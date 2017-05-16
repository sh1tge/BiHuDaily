package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import java.util.ArrayList;
import java.util.List;

import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetLoadMoreListener;
import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.domain.Story;
import cn.ml.saddhu.bihudaily.engine.domain.StoryInfo;
import cn.ml.saddhu.bihudaily.engine.domain.TopStory;
import cn.ml.saddhu.bihudaily.mvp.model.StoryListModel;
import cn.ml.saddhu.bihudaily.mvp.model.impl.StoryListModelImpl;
import cn.ml.saddhu.bihudaily.mvp.presenter.StoryListPresenter;
import cn.ml.saddhu.bihudaily.mvp.view.StoryListView;

/**
 * Created by sadhu on 2016/11/15.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class StoryListPresenterImpl implements StoryListPresenter, OnNetRefreshListener<StoryInfo>, OnNetLoadMoreListener<List<Story>> {
    private StoryListView mView;
    private StoryListModel mModel;
    private StoryInfo mStoryInfo;

    public StoryListPresenterImpl(StoryListView view) {
        this.mView = view;
        mModel = new StoryListModelImpl(this, this);
    }

    @Override
    public void setData(StoryInfo storyInfo) {
        this.mStoryInfo = storyInfo;
    }

    @Override
    public ArrayList<String> getLooperIdList() {
        ArrayList<String> looperIds = new ArrayList<>();
        for (TopStory story : mStoryInfo.topStories) {
            looperIds.add(story.id);
        }
        return looperIds;
    }

    @Override
    public ArrayList<String> getNormalIdList() {
        ArrayList<String> normalIds = new ArrayList<>();
        for (Story story : mStoryInfo.stories) {
            normalIds.add(story.id);
        }
        return normalIds;
    }

    @Override
    public void setItemRead(int position) {
        if (!mStoryInfo.stories.get(position).isRead) {
            mStoryInfo.stories.get(position).setIsRead(true);
            mModel.setItemRead(mStoryInfo.stories.get(position).getId());
            mView.notifyItemChange(position);
        }

    }

    @Override
    public void getHomePageList() {
        mModel.getHomePageList();
    }

    @Override
    public void loadMoreHomePageList() {
        mModel.loadMoreHomePageList(mStoryInfo.stories.get(mStoryInfo.stories.size() - 1).date);
    }

    @Override
    public String getTagName(int position, boolean hasLooper) {
        return mStoryInfo.stories.get(hasLooper ? position - 1 : position).tagName;
    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel.onDestroy();
        mModel = null;
    }

    @Override
    public void onRefreshSuccess(StoryInfo t) {
        mStoryInfo = t;
        mView.setFirstPageData(t);
    }

    @Override
    public void onRefreshError(int code) {

    }

    @Override
    public void onLoadMoreSuccess(List<Story> stories) {
        mView.onLoadMoreSuccess(stories);
    }

    @Override
    public void onLoadMoreError(int code) {

    }
}
