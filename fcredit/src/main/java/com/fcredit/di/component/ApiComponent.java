package com.fcredit.di.component;

import com.fcredit.di.module.ApiModule;
import com.fcredit.di.scope.GlobalApis;
import com.fcredit.model.api.ApiLiveApis;
import com.fcredit.model.api.AppApis;
import com.fcredit.model.api.BangumiApis;
import com.fcredit.model.api.LiveApis;
import com.fcredit.model.api.RecommendApis;
import com.fcredit.model.api.RegionApis;
import com.fcredit.model.api.WeChatApis;
import com.fcredit.model.api.ZhihuApis;
import com.common.app.AppComponent;

import dagger.Component;

/**
 * Created by Android_ZzT on 17/6/15.
 */

@Component(dependencies = AppComponent.class, modules = ApiModule.class)
@GlobalApis
public interface ApiComponent {

    AppApis appApis();

    LiveApis liveApis();

    RecommendApis recommendApis();

    BangumiApis biliBiliApis();

    RegionApis regionApis();

    ApiLiveApis apiLiveApis();

    //Test
    ZhihuApis zhihuApis();

    WeChatApis weChatApis();
}
