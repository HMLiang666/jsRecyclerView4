package com.mumu.jsrecyclerview4;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_test)
    RecyclerView rvTest;
    @BindView(R.id.srl_test)
    SmartRefreshLayout srlTest;
    private TestAdapter mTestAdapter;
    private ArrayList<TestEntity.ResultBean.ListBean> mTestList=new ArrayList<>();
    private ArrayList<TestEntity.ResultBean> mTestList1=new ArrayList<TestEntity.ResultBean>();
    private TestEntity mTestEntity=new TestEntity();
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        //初始化的时候默认没有数据，显示空的布局
        getData(1);
        refreshView();
        smartRefreshView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    /**
     * 刷新消息列表
     */
    private void refreshView() {
        //1,加载空布局文件，便于第五步适配器在没有数据的时候加载
        View emptyView = View.inflate(this, R.layout.empty_view, null);
        //2，设置LayoutManager,LinearLayoutManager表示竖直向下
        rvTest.setLayoutManager(new LinearLayoutManager(this));
        //3，初始化一个无数据的适配器
        mTestAdapter = new TestAdapter(R.layout.item_test_list,R.layout.item_test_head,null);
        //4，绑定recyclerView和适配器
        rvTest.setAdapter(mTestAdapter);
        //5，给recyclerView设置空布局
        mTestAdapter.setEmptyView(emptyView);
        //6，给recyclerView的每一个子列表添加点击事件
        mTestAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TestEntity.ResultBean resultBean = mTestEntity.getResult().get(position);
                if(resultBean.isHeader){
                    Toast.makeText(MainActivity.this, "我点击了第" + position + "个header",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "我点击了第" + position + "个子view",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * MainActivity中增加下拉刷新和上拉加载的监听方法
     */
    private void smartRefreshView() {
        srlTest.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉刷新,一般添加调用接口获取数据的方法
                getData(2);
                //结束下拉刷新
                refreshLayout.finishRefresh();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载，一般添加调用接口获取更多数据的方法
                getData(3);
                //结束上拉加载，展示没有更多数据
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        });
    }

    /**
     * 获取数据的方法
     * 该方法纯属展示各种效果，实际应用时候请自己根据需求做判断即可
     *
     * @param mode 模式：1为刚开始进来加载数据 空数据 2为下拉刷新 3为上拉加载
     */
    private void getData(int mode) {
        //添加临时数据，一般直接从接口获取
        switch (mode) {
            case 1:
                break;
            case 2:
                mTestList1.clear();
                mTestList1.add(new TestEntity.ResultBean(true,null,"我有一只小狗"));
                mTestList1.add(new TestEntity.ResultBean(new TestEntity.ResultBean.ListBean("我有一个小狗我有一个小狗我有一个小狗我有一个小狗我有一个小狗")));
                mTestList1.add(new TestEntity.ResultBean(new TestEntity.ResultBean.ListBean("我有一个小狗我有一个小狗我有一个小狗我有一个小狗我有一个小狗")));
                mTestList1.add(new TestEntity.ResultBean(new TestEntity.ResultBean.ListBean("我有一个小狗我有一个小狗我有一个小狗我有一个小狗我有一个小狗")));
                mTestList1.add(new TestEntity.ResultBean(true,null,"我有一只小狗"));
                mTestList1.add(new TestEntity.ResultBean(new TestEntity.ResultBean.ListBean("我有一个小狗我有一个小狗我有一个小狗我有一个小狗我有一个小狗")));
                mTestList1.add(new TestEntity.ResultBean(new TestEntity.ResultBean.ListBean("我有一个小狗我有一个小狗我有一个小狗我有一个小狗我有一个小狗")));
                mTestEntity.setResult(mTestList1);
                //更新数据
                mTestAdapter.setNewData(mTestEntity.getResult());
                break;
            case 3:
                mTestList1.add(new TestEntity.ResultBean(true,null,"我有一只小狗"));
                mTestList1.add(new TestEntity.ResultBean(new TestEntity.ResultBean.ListBean("我有一个小狗我有一个小狗我有一个小狗我有一个小狗我有一个小狗")));
                mTestList1.add(new TestEntity.ResultBean(new TestEntity.ResultBean.ListBean("我有一个小狗我有一个小狗我有一个小狗我有一个小狗我有一个小狗")));
                mTestList1.add(new TestEntity.ResultBean(new TestEntity.ResultBean.ListBean("我有一个小狗我有一个小狗我有一个小狗我有一个小狗我有一个小狗")));
                mTestList1.add(new TestEntity.ResultBean(true,null,"我有一只小狗"));
                mTestList1.add(new TestEntity.ResultBean(new TestEntity.ResultBean.ListBean("我有一个小狗我有一个小狗我有一个小狗我有一个小狗我有一个小狗")));
                mTestList1.add(new TestEntity.ResultBean(new TestEntity.ResultBean.ListBean("我有一个小狗我有一个小狗我有一个小狗我有一个小狗我有一个小狗")));
                mTestEntity.setResult(mTestList1);
                //更新数据
                mTestAdapter.setNewData(mTestEntity.getResult());
                break;
        }
    }
}