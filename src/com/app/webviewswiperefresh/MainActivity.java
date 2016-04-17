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
				//����ˢ��ҳ��
				webView.loadUrl(webView.getUrl());
			}
		});
		
		swipeRefresh.setColorSchemeColors(Color.RED,Color.BLUE,Color.GRAY);
		
		webView.loadUrl("http://www.baidu.com");
		webView.getSettings().setJavaScriptEnabled(true);
		//ȡ��������
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		//��������������
		//webView.requestFocus();
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		
		//���ý�����
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if(newProgress==100){
					//ȡ��������
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
