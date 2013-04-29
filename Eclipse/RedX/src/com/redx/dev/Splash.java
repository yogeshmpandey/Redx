package com.redx.dev;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle bdle) {
		// TODO Auto-generated method stub
		
		super.onCreate(bdle);
		setContentView(R.layout.splash);   
		Thread timer = new Thread(){
			@Override
			public void run(){
				try{
					sleep(5000);
				} catch(InterruptedException e){
					e.printStackTrace();
				} finally{
					Intent openLogin = new Intent(Splash.this,Login.class);
					startActivity(openLogin);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
	
	