package com.tangqi;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 多线程下载的思路：
 * 1、获取文件大小
 * 2、在客户端创建一个大小和服务器一模一样的文件，提前申请好空间
 * 3、每个线程下载的开始位置和结束位置
 * 4、知道每个线程什么时候下载完毕了
 */
public class MultiThreadDownload extends AppCompatActivity {
    private static final String TAG = "test";
    private EditText download_site;
    private EditText download_thread;
    private Button bt_download;
    private LinearLayout ll_threads;
    private String mThread_count;
    private String mSite;
    private int mThread_count1;
    private Context mc;
    private int mContentLength;
    private int runningThread;
    private List<ProgressBar> mPb_list;
    private ProgressBar mPb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread_download);

        download_site = (EditText) findViewById(R.id.download_site);
        download_thread = (EditText) findViewById(R.id.download_thread);
        bt_download = (Button) findViewById(R.id.bt_download);
        ll_threads = (LinearLayout) findViewById(R.id.progressbar_ll);
        mc = this;
        mPb_list = new ArrayList<>();
//        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

    }


    /**
     * 按钮点击事件
     */
    public void download(View view) {
        Log.d(TAG, "点击了按钮");
        mSite = download_site.getText().toString().trim();//下载的地址
        mThread_count = download_thread.getText().toString().trim();
        mThread_count1 = Integer.parseInt(mThread_count);//线程数量
        runningThread = mThread_count1;

        //移除进度条
        ll_threads.removeAllViews();
        mPb_list.clear();


        //把进度条显示出来。
        for (int i = 0; i < mThread_count1; i++) {
            View view_pb = View.inflate(getApplicationContext(), R.layout.item_progressbar, null);
            ProgressBar pb = view_pb.findViewById(R.id.progressbar_item);

            mPb_list.add(pb);//添加到集合中
            ll_threads.addView(view_pb);
        }


        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(mSite);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(3000);
                    int responseCode = conn.getResponseCode();
                    if (responseCode == 200) {
                        //一 ★★★ 获取服务器文件大小
                        mContentLength = conn.getContentLength();
                        runningThread = mThread_count1;//把线程的数量赋值给正在运行的线程
                        Log.d(TAG, "文件大小: " + mContentLength);

                    } else {
                        Log.d(TAG, "连接失败，错误码：" + responseCode);
                    }

                    //二 ★★★ 在客户端创建一个大小和服务器一模一样的文件，提前申请好空间
                    RandomAccessFile raf = new RandomAccessFile(getFileName(mSite), "rw");
                    raf.setLength(mContentLength);

                    //三 ★★★ 计算每个线程下载的位置
                    int blockSize = mContentLength / mThread_count1;
                    for (int i = 0; i < mThread_count1; i++) {
                        int startIndex = i * blockSize;//每个线程下载的开始位置
                        int endIndex = (i + 1) * blockSize - 1;
                        //特殊情况 最后一个线程
                        if (i == mThread_count1 - 1) {
                            endIndex = mContentLength - 1;
                        }
                        Log.d(TAG, i + "线程的理论下载位置：" + startIndex + "---" + endIndex);

                        //到对应的线程中下载
                        DownLoadThread downLoadThread = new DownLoadThread(startIndex, endIndex, i);
                        downLoadThread.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();


        //算出每个线程下载的大小和下载的部分


    }

    //获取文件的名字
    public String getFileName(String path) {
        int start = path.lastIndexOf("/") + 1;

        return mc.getExternalFilesDir(null) + "/" + path.substring(start);
    }


    /**
     * 内部类，用于定义下载的线程和动作
     */

    private class DownLoadThread extends Thread {
        private int startIndex;
        private int endIndex;
        private int threadId;
        private int max_progress;//当前线程最大值
        private int lastposition_progress;//进度条上次的位置

        //构造方法
        public DownLoadThread(int startIndex, int endIndex, int threadId) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadId = threadId;
            this.max_progress = endIndex - startIndex;


        }

        /**
         * 真正实现下载的逻辑
         */
        @Override
        public void run() {
            try {

                URL url = new URL(mSite);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);

                //断点续传逻辑之读取上次下载到的位置，赋给开始位置
                File file = new File(mc.getExternalFilesDir(null) + "/" + threadId + ".txt");
                if (file.exists() && file.length() > 0) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(new
                            FileInputStream(file)));
                    String lastPostions = br.readLine();
                    int lastPosition = Integer.parseInt(lastPostions);
                    lastposition_progress = lastPosition - startIndex;//再次下载时，进度条从哪里开始。
                    startIndex = lastPosition + 1;
                    Log.d(TAG, threadId + "线程从这里继续下载：" + startIndex + "---" + endIndex);
                    br.close();
                }

                //设置请求头Range，告诉服务器每个线程下载的开始位置和结束位置。
                conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
                int code = conn.getResponseCode();
                if (code == 206) {
                    //随机读写文件对象
                    RandomAccessFile raf = new RandomAccessFile(getFileName(mSite), "rw");
                    raf.seek(startIndex);
                    InputStream is = conn.getInputStream();
                    int len = -1;
                    int total = 0;//表示已经下载的大小
                    byte[] bys = new byte[1024 * 1024 * 2];//缓冲区2M
                    while ((len = is.read(bys)) != -1) {
                        raf.write(bys, 0, len);

                        //实现断点续传逻辑之保存已下载的进度到文件中
                        total += len;
                        int currentThreadPosition = startIndex + total;
                        RandomAccessFile raff = new RandomAccessFile(mc.getExternalFilesDir(null)
                                + "/" + threadId + ".txt", "rwd");
                        raff.write(String.valueOf(currentThreadPosition).getBytes());//int转byte[]
                        raff.close();

                        //更新进度条
                        ProgressBar pb = mPb_list.get(threadId);
                        pb.setMax(max_progress);
                        pb.setProgress(lastposition_progress+total);

                    }
                    raf.close();//关闭流，释放资源
                    Log.d(TAG, threadId + "线程下载完毕");

                    //加锁，判断是否线程全部下载完毕，是就删除txt文件
                    synchronized (this) {
                        runningThread--;
                        if (runningThread == 0) {
                            for (int i = 0; i < mThread_count1; i++) {

                                File dele_file = new File(mc.getExternalFilesDir(null) + "/" + i
                                        + ".txt");
                                dele_file.delete();
                            }

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 使用开源项目xUtils下载
     * @param view bt_download_xutils
     */
    public void downloadByXutils(View view) {
        mPb = (ProgressBar) findViewById(R.id.pb_download_xutils);
        mSite = download_site.getText().toString().trim();//下载的地址
        int start = mSite.lastIndexOf("/") + 1;
        RequestParams params = new RequestParams(mSite);
        params.setSaveFilePath(mc.getExternalFilesDir(null) + File.separator + mSite.substring(start));//保存的位置
        Log.d(TAG, "保存的位置："+mc.getExternalFilesDir(null) + File.separator + mSite.substring(start));
        params.setAutoResume(true);//是否支持断点


        x.http().get(params, new Callback.ProgressCallback<File>() {

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
mPb.setMax((int) total);
mPb.setProgress((int) current);
            }

            @Override
            public void onSuccess(File result) {
                Toast.makeText(mc, "下载成功", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mc, "错误:", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onError: "+ex);

            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(mc, "取消", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
                Toast.makeText(mc, "结束", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
