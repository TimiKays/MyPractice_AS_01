package com.tangqi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.glandroid.smartimagedemo.SmartImageView;
import com.tangqi.domain.News;
import com.tangqi.service.XmlParserUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NewsClient extends AppCompatActivity {

    private ListView mListView;
    private List<News> mNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_client);
        //1、初始化界面
        mListView = (ListView) findViewById(R.id.lv_news);
        TextView title = (TextView) findViewById(R.id.commom_title);
        title.setText("今日新闻");
        //2、获取数据
        getWebNewsData();
        //3、解析数据，封装到实体类：XmlParserUtils
        //4、显示到页面：setAdapter

    }

    private void getWebNewsData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.1.102:8080/WebServer/news.xml");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        final InputStream is = conn.getInputStream();
                        mNewsList = XmlParserUtils.converse(is);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mListView.setAdapter(new NewsAdapter());
                            }
                        });
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }


    private class NewsAdapter extends BaseAdapter {

        private View mView;
        private TextView mTvTitle;
        private TextView mTvContent;
        private SmartImageView mSmartImageView;

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {

                mView = View.inflate(getApplicationContext(), R.layout.item_news, null);
            } else {
                mView = convertView;
            }

            mTvTitle = mView.findViewById(R.id.news_title);
            mTvTitle.setText(mNewsList.get(position).getTitle());

            mTvContent = mView.findViewById(R.id.news_content);
            mTvContent.setText(mNewsList.get(position).getDescription());

            //展示图片
            mSmartImageView = mView.findViewById(R.id.news_img);
            String imgUrl = mNewsList.get(position).getImage();
            mSmartImageView.setImageUrl(imgUrl);


            return mView;
        }
    }


}

