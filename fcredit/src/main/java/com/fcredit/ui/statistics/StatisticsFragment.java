package com.fcredit.ui.statistics;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.common.base.BaseFragment;
import com.fcredit.R;
import com.fcredit.model.event.TabSelectedEvent;
import com.fcredit.model.event.ToggleDrawerEvent;
import com.fcredit.ui.housecoast.HouseCoastListActivity;
import com.fcredit.ui.main.MainActivity;
import com.fcredit.widget.textview.AlwaysCenterTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 统计
 */
public class StatisticsFragment extends BaseFragment {

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    AlwaysCenterTextView tvTitle;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistics;
    }

    @Override
    protected void initViewAndEvent() {
        tvTitle.setText(getString(R.string.section_statistics));
    }



    @OnClick({R.id.ll_top_menu_nav})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_top_menu_nav:
                ToggleDrawerEvent event = new ToggleDrawerEvent();
                EventBus.getDefault().post(event);
        }
    }

    @OnClick({R.id.layout_house_coast})
    public void onClickHouseCoast(View view)
    {
        Intent intent = new Intent(getActivity(), HouseCoastListActivity.class);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TabSelectedEvent event){
        if(event.getPosition() == MainActivity.SECOND){
            setUpToolBar(mToolbar);
        }
    }

    private void myToast(String strMsg)
    {
        Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
    }
}
