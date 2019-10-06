package net.systemsstars.crm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import net.systemsstars.crm.helper.MyApplication;
import net.systemsstars.crm.model.User;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handler.postDelayed(runnable, 2000);
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run()
        {

            Intent i;
            if (MyApplication.getInstance().getPrefManager().getUser()!=null) {
                if (MyApplication.getInstance().getPrefManager().getUser().getType().equals("admin")) {
                    i = new Intent(SplashScreen.this, MainActivity.class);
                }else {
                    i = new Intent(SplashScreen.this, UserDashBoard.class);
                }
            }else {
                i = new Intent(SplashScreen.this, Login.class);
            }
            startActivity(i);
            finish();
        }
    };
}
