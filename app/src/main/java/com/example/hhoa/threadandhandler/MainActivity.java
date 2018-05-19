package com.example.hhoa.threadandhandler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
                            implements View.OnClickListener{

    private static final String TAG = "Thread";
    private static final String STRINGKEY = "STRING_KEY";
    private ScrollView scrollView;
    private TextView txtView;
    private Button btnAdd;
    private ProgressBar progressBar;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = findViewById(R.id.scroll_view);
        txtView = findViewById(R.id.txt_view);
        btnAdd = findViewById(R.id.btn_add);
        progressBar = findViewById(R.id.progress_bar);

        btnAdd.setOnClickListener(this);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                progressBar.setVisibility(View.INVISIBLE);
                Bundle b = message.getData();
                String s = (String) b.get(STRINGKEY);
                Log.i(TAG, "handleMessage: " + s);
            }
        };
    }

    @Override
    public void onClick(View v) {
        txtView.append(getString(R.string.add_more_text));
        scrollTextToEnd();
        progressBar.setVisibility(View.VISIBLE);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: start thread");
                // You can replace sleep with your task
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message myMessage = new Message();
                Bundle b = new Bundle();
                b.putString(STRINGKEY, "run: end thread");
                myMessage.setData(b);
                mHandler.sendMessage(myMessage);

            }
        };

        Thread t = new Thread(runnable);
        t.start();
    }

    private void scrollTextToEnd() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
