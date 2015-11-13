package com.liuzhuangzhuang.bbarrage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private RootView rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootView = RootView.attach2Window(MainActivity.this);
                rootView.addText("弹幕开启");
            }
        });

        this.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RootView.removeFromWindow(MainActivity.this);
            }
        });

        this.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rootView == null) {
                    return;
                }
                rootView.addText("HAHAHAHA");
            }
        });
    }
}
