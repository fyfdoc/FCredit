package com.fcredit.di.component;

import com.fcredit.di.module.ActivityModule;
import com.fcredit.di.module.PageModule;
import com.fcredit.di.scope.PerActivity;
import com.fcredit.ui.live.liveplay.LivePlayActivity;
import com.fcredit.ui.main.MainActivity;
import com.fcredit.ui.test.activity.NewsActivity;
import com.fcredit.ui.test.activity.ScrollGradientActivity;
import com.fcredit.ui.test.activity.StatusWithPictureActivity;
import com.fcredit.ui.test.activity.TestApiActivity;
import com.fcredit.ui.test.activity.TestNoBaseActivity;
import com.fcredit.ui.test.activity.TestNoBaseMvpActivity;
import com.fcredit.ui.test.activity.ToolbarBehaviorActivity;

import dagger.Component;

/**
 * Created by jiayiyang on 17/3/23.
 */

@Component(dependencies = {ApiComponent.class}, modules = {ActivityModule.class, PageModule.class})
@PerActivity
public interface ActivityComponent {
    //Bilibili
    void inject(MainActivity mainActivity);
    void inject(LivePlayActivity livePlayActivity);

    //Test
    void inject(NewsActivity newsActivity);
    void inject(ToolbarBehaviorActivity toolbarBehaviorActivity);
    void inject(StatusWithPictureActivity statusWithPictureActivity);
    void inject(ScrollGradientActivity scrollGradientActivity);
    void inject(TestApiActivity testApiActivity);
    void inject(TestNoBaseActivity testNoBaseActivity);
    void inject(TestNoBaseMvpActivity testNoBaseMvpActivity);


}
