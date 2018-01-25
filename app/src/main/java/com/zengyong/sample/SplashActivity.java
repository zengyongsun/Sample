package com.zengyong.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zengyong.lib.BaseActivity;

/**
 * <pre>
 *    @author : Zeyo
 *     e-mail : zengyongsun@163.com
 *     time   : 2018/01/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);
    }
}
