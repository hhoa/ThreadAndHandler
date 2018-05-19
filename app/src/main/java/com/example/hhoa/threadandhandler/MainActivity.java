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
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
                            implements View.OnClickListener{

    private static final String TAG = "Thread";
    private static final String STRINGKEY = "STRING_KEY";
    private ScrollView scrollView;
    private TextView txtView;
    private Button btnAdd;
    private ProgressBar progressBar;
    private Handler mHandler;

    ExecutorService myExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView = findViewById(R.id.scroll_view);
        txtView = findViewById(R.id.txt_view);
        btnAdd = findViewById(R.id.btn_add);
        progressBar = findViewById(R.id.progress_bar);

        btnAdd.setOnClickListener(this);

        myExecutor = Executors.newFixedThreadPool(3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myExecutor.shutdown();  
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "See log for details", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < 6; i++) {
            Runnable r = new Task(i);
            myExecutor.execute(r);
        }
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
