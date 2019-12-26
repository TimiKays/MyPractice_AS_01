package com.tangqi.safecenter.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tangqi.safecenter.R;
import com.tangqi.safecenter.db.dao.BlackNumberDao;
import com.tangqi.safecenter.db.domain.BlackNumberBean;
import com.tangqi.safecenter.utils.ToastUtil;

import java.util.ArrayList;

public class BlackNumberActivity extends BaseActivity {

    private ImageView iv_add_black_number;
    private ListView lv_black_list;
    private Context mc;
    private ArrayList<BlackNumberBean> mBlackNumberList;
    private BlackNumberDao mBlackNumberDao;
    private int mode = 0;
    private BlackNumberAdapter mBlackNumberAdapter;
    private boolean isload = false;
    private int mCount;
    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mBlackNumberAdapter == null) {
                mBlackNumberAdapter = new BlackNumberAdapter();
                lv_black_list.setAdapter(mBlackNumberAdapter);
            } else {
                mBlackNumberAdapter.notifyDataSetChanged();
                isload = false;
            }

        }
    };

    @Override
    public void initView() {
        setContentView(R.layout.activity_black_number);
        mc = this;
        iv_add_black_number = findViewById(R.id.iv_add_black_number);
        lv_black_list = findViewById(R.id.lv_black_list);

    }

    @Override
    public void showNextView() {

    }

    @Override
    public void showPrevious() {

    }

    @Override
    public void initData() {
        new Thread() {
            @Override
            public void run() {
                mBlackNumberDao = BlackNumberDao.getInstance(mc);
                mBlackNumberList = mBlackNumberDao.find(0);
                mCount = mBlackNumberDao.getCount();
                mHandler.sendEmptyMessage(0);
            }
        }.start();

    }

    @Override
    public void initEvent() {


        /**
         * 添加一个黑名单的点击事件
         */
        iv_add_black_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mc);
                final View dialog_add_blacknumber = View.inflate(mc, R.layout
                        .dialog_add_blacknumber, null);
                builder.setView(dialog_add_blacknumber);
                //获取拦截类型
                RadioGroup rg_mode = dialog_add_blacknumber.findViewById(R.id
                        .rg_blacknumber_mode);
                rg_mode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener
                        () {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.rb_sms:
                                mode=0;
                                break;
                            case R.id.rb_tel:
                                mode=1;
                                break;
                            case R.id.rb_all:
                                mode=2;
                                break;
                        }
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //获取拦截号码
                        EditText et_add_blacknumber = dialog_add_blacknumber.findViewById(R.id
                                .et_add_blacknumber);

                        String number = et_add_blacknumber.getText().toString().trim();
                        if (!TextUtils.isEmpty(number)) {
                            //输入了号码，就存入数据库，并刷新列表
                            mBlackNumberDao.insert(number,mode+"");
                            mBlackNumberList = mBlackNumberDao.findAll();
                            mHandler.sendEmptyMessage(0);

                        } else {
                            ToastUtil.show(mc, "您还没有输入号码哦~");
                        }

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();

                //初始化数据用的
//                BlackNumberDao mBlackNumberDao = BlackNumberDao.getInstance(mc);
//                for (int i = 0; i < 100; i++) {
//                    if (i < 10) {
//
//                        mBlackNumberDao.insert("1390000000" + i, i % 3 + "");
//                    } else {
//                        mBlackNumberDao.insert("139000000" + i, i % 3 + "");
//                    }
//                }
//                mBlackNumberList = mBlackNumberDao.find(mBlackNumberList.size());
//                mHandler.sendEmptyMessage(0);


            }
        });

        /**
         * 设置滚动监听器,到最后的时候就继续加载后面20条
         */
        lv_black_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //当滚动条为空闲状态，
                // 且最后一个条目为可见（得到最后一个条目的索引值，如果大于等于总个数-1，就是到底了）
                //且之前的全部加载完毕
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lv_black_list.getLastVisiblePosition() >= mBlackNumberList.size() - 1 && !isload) {

                    if(mCount>mBlackNumberList.size()){  //还剩数据没有加载的时候才加载
                        new Thread() {
                            public void run() {
                                ArrayList<BlackNumberBean> NewTwelve = mBlackNumberDao.find(mBlackNumberList.size());
                                mBlackNumberList.addAll(NewTwelve);
                                mHandler.sendEmptyMessage(0);
                            }
                        }.start();
                    }

                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private static class ViewHolder {
        private TextView tv_number;
        private TextView tv_mode;
        private ImageView iv_del_black_number;

    }

    class BlackNumberAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mBlackNumberList.size();
        }

        @Override
        public Object getItem(int position) {
            return mBlackNumberList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mc, R.layout.item_black_list, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_mode = convertView.findViewById(R.id.tv_mode);
                viewHolder.tv_number = convertView.findViewById(R.id.tv_number);
                viewHolder.iv_del_black_number = convertView.findViewById(R.id.iv_del_black_number);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            BlackNumberBean blackNumberBean = mBlackNumberList.get(position);
            viewHolder.tv_number.setText(blackNumberBean.getNumber());
            String mode = blackNumberBean.getMode();
            switch (mode) {
                case "0":
                    viewHolder.tv_mode.setText("拦截短信");
                    break;
                case "1":
                    viewHolder.tv_mode.setText("拦截电话");
                    break;
                case "2":
                    viewHolder.tv_mode.setText("拦截短信和电话");
                    break;
            }

            viewHolder.iv_del_black_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBlackNumberDao.delete(mBlackNumberList.get(position).number);
                    mBlackNumberList = mBlackNumberDao.findAll();
                    mBlackNumberAdapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }
}
