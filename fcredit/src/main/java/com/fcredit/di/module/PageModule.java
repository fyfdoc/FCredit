package com.fcredit.di.module;

import com.fcredit.di.scope.PerActivity;
import com.fcredit.di.scope.PerFragment;
import com.fcredit.ui.bangumi.BangumiFragment;
import com.fcredit.ui.live.LiveFragment;
import com.fcredit.ui.main.MainFragment;
import com.fcredit.ui.recommed.RecommendFragment;
import com.fcredit.ui.region.RegionFragment;
import com.fcredit.ui.test.fragment.NewsFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jiayiyang on 17/4/14.
 */

@Module
public class PageModule {

    //Test
    @Provides
    @PerActivity
    NewsFragment provideNewsFragment(){
        return new NewsFragment();
    }

    //main
    @Provides
    @PerActivity
    MainFragment provideMainFragment() {
        return new MainFragment();
    }

    @Provides
    @PerFragment
    LiveFragment provideLiveFragment() {
        return new LiveFragment();
    }

    @Provides
    @PerFragment
    RecommendFragment provideRecommendFragment() {
        return new RecommendFragment();
    }

    @Provides
    @PerFragment
    BangumiFragment provideBangumiFragment() {
        return new BangumiFragment();
    }

    @Provides
    @PerActivity
    RegionFragment provideRegionFragment() {
        return new RegionFragment();
    }
}
