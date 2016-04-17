package com.app.webviewswiperefresh;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView webView;
    private SwipeRefreshLayout swipeRefresh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		webView=(WebView) findViewById(R.id.webview);
		swipeRefresh=(SwipeRefreshLayout) findViewById(R.id.sw_container);
		
		swipeRefresh.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				//重新刷新页面
				webView.loadUrl(webView.getUrl());
			}
		});
		
		swipeRefresh.setColorSchemeColors(Color.RED,Color.BLUE,Color.GRAY);
		
		webView.loadUrl("http://www.baidu.com");
		webView.getSettings().setJavaScriptEnabled(true);
		//取消滚动条
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		//触摸焦点起作用
		//webView.requestFocus();
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		
		//设置进度条
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if(newProgress==100){
					//取消进度条
					swipeRefresh.setRefreshing(false);
				}else{
					if(!swipeRefresh.isRefreshing()){
						swipeRefresh.setRefreshing(true);
					}
				}
				
				super.onProgressChanged(view, newProgress);
			}
		});
		
		
	}
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK&&webView.canGoBack()){
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
