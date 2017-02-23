package cn.ml.saddhu.bihudaily.mvp.presenter.imp;

import cn.ml.saddhu.bihudaily.engine.commondListener.OnNetRefreshListener;
import cn.ml.saddhu.bihudaily.engine.domain.StoryDetailExtra;
import cn.ml.saddhu.bihudaily.mvp.model.StoryActDetailModel;
import cn.ml.saddhu.bihudaily.mvp.model.impl.StoryActDetailModelImpl;
import cn.ml.saddhu.bihudaily.mvp.presenter.StoryActDetailPresetner;
import cn.ml.saddhu.bihudaily.mvp.view.StoryDetailView;

/**
 * Created by sadhu on 2017/2/23.
 * Email static.sadhu@gmail.com
 * Describe:
 */
public class StoryActDetailPresenterImpl implements StoryActDetailPresetner, OnNetRefreshListener<StoryDetailExtra> {
    private StoryDetailView mView;
    private StoryActDetailModel mModel;

    public StoryActDetailPresenterImpl(StoryDetailView view) {
        this.mView = view;
        this.mModel = new StoryActDetailModelImpl(this);
    }

    @Override
    public void getStoryInfoExtra(String storyId) {
        mModel.getStoryInfoExtral(storyId);
    }


    @Override
    public void onRefreshSuccess(StoryDetailExtra storyDetailExtra) {
        mView.setToolBarInfo(storyDetailExtra);
    }

    @Override
    public void onRefreshError(int code) {

    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel.onDestroy();
        mModel = null;
    }
}
