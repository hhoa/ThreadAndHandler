package com.example.hhoa.threadandhandler;

import android.os.AsyncTask;
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
    private DemoAsyncTask myTask;
    private boolean isRunning;

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
        if (myTask != null && isRunning)
        {
            myTask.cancel(true);
            isRunning = false;
        } else {
            myTask = new DemoAsyncTask();
            myTask.execute(1, 2, 3);
            isRunning = true;
        }
    }

    public void writeIndexToTextView(Object index) {
        if (index instanceof Integer || index instanceof String)
            txtView.append(String.valueOf(index) + '\n');
        scrollTextToEnd();
    }

    private void scrollTextToEnd() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    class DemoAsyncTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for (int i: integers) {
                if (isCancelled())
                {
                    break;
                }
                publishProgress(i);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Done";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            for (int i: values)
                writeIndexToTextView(i);
        }

        @Override
        protected void onPostExecute(String s) {
            writeIndexToTextView(s);
        }

        @Override
        protected void onCancelled() {
            writeIndexToTextView("Cancel AsyncTask");
        }
    }
}
