package com.fcredit.ui.statistics;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.common.base.BaseFragment;
import com.fcredit.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 统计项
 */
public class StatisticsItemFragment extends BaseFragment {


    @BindView(R.id.textView2)
    TextView textView2;
    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statistics_item;
    }

    @Override
    protected void initViewAndEvent() {
        //textView2.setText("---");
    }



    private void myToast(String strMsg)
    {
        Toast.makeText(getActivity(), strMsg, Toast.LENGTH_SHORT).show();
    }
}
