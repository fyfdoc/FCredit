package com.fcredit.di.component;

import com.fcredit.di.module.FragmentModule;
import com.fcredit.di.module.PageModule;
import com.fcredit.di.scope.PerFragment;
import com.fcredit.ui.bangumi.BangumiFragment;
import com.fcredit.ui.live.LiveFragment;
import com.fcredit.ui.main.MainFragment;
import com.fcredit.ui.recommed.RecommendFragment;
import com.fcredit.ui.region.RegionFragment;
import com.fcredit.ui.test.fragment.NewsFragment;
import com.fcredit.ui.test.fragment.NewsPageFragment;
import com.fcredit.ui.test.fragment.NewsPageFragment2;

import dagger.Component;

/**
 * Created by jiayiyang on 17/4/14.
 */

@Component(dependencies = ApiComponent.class, modules = {FragmentModule.class, PageModule.class})
@PerFragment
public interface FragmentComponent {

    void inject(NewsFragment newsFragment);

    void inject(NewsPageFragment newsPageFragment);

    void inject(NewsPageFragment2 newsPageFragment2);

    void inject(MainFragment mainFragment);

    void inject(LiveFragment liveFragment);

    void inject(BangumiFragment bangumiFragment);

    void inject(RecommendFragment recommendFragment);

    void inject(RegionFragment regionFragment);
}
