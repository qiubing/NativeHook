package com.example.nativehook;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.initXHook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load xhook
                com.qiyi.xhook.XHook.getInstance().init(getApplication());
                if (!com.qiyi.xhook.XHook.getInstance().isInited()) {
                    return;
                }
//                com.qiyi.xhook.XHook.getInstance().enableDebug(true); //default is false
                com.qiyi.xhook.XHook.getInstance().enableSigSegvProtection(false); //default is true
                Toast.makeText(MainActivity.this, "初始化xHook成功", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.threadHookInit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThreadHook.enableThreadHook();
                Toast.makeText(MainActivity.this, "开启Thread Hook成功", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.hookThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("qiubing", "thread name:" + Thread.currentThread().getName());
                        Log.e("qiubing", "thread id:" + Thread.currentThread().getId());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("qiubing", "inner thread name:" + Thread.currentThread().getName());
                                Log.e("qiubing", "inner thread id:" + Thread.currentThread().getId());

                            }
                        }).start();
                    }
                }).start();
            }

        });

        findViewById(R.id.logHookInit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load and run your biz lib (for register hook points)
                com.qiyi.biz.Biz.getInstance().init();
                com.qiyi.biz.Biz.getInstance().start();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //xhook do refresh
                Toast.makeText(MainActivity.this, "开启日志Hook成功", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.hookLog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // load and run the target lib
                com.qiyi.test.Test.getInstance().init();
                com.qiyi.test.Test.getInstance().start();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // xhook do refresh again
                com.qiyi.xhook.XHook.getInstance().refresh(false);

                //xhook do refresh again for some reason,
                //maybe called after some System.loadLibrary() and System.load()
                //*
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            com.qiyi.xhook.XHook.getInstance().refresh(true);
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

        findViewById(R.id.refreshXHook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xhook do refresh
                com.qiyi.xhook.XHook.getInstance().refresh(false);
                Toast.makeText(MainActivity.this, "开启Hook成功", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.closeXHook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.qiyi.xhook.XHook.getInstance().clear();
                Toast.makeText(MainActivity.this, "关闭Hook成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
