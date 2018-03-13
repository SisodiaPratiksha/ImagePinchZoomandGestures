package com.example.dell.veview;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btn;
    private static final String TAG = "touch";
    float Xstart, Xend, Ystart, Yend, Xswipe;
    String path;
    File filepath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btnnext);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        LinearLayout view = (LinearLayout) findViewById(R.id.linview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("https://panoroo.com/tours/oBdXElBd");
        touchListener(view);
        path = getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()).getAbsolutePath();
        filepath = new File(path + "touch.csv");
        Log.d(TAG, "onCreate: Initializing touch services");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(i);
            }
        });
    }

    private void touchListener(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
                    Xstart  = event.getX();
                    Ystart = event.getY();
                    Xswipe = Xstart;
                    String adown = "Xstart:"+String.valueOf(Xstart)+ ", Ystart: "+ String.valueOf(Ystart)+ ", Pressure: "+ event.getPressure()+"\n";

                    try {

                        FileWriter fw = new FileWriter(filepath,true);
                        fw.append(adown);
                        fw.flush();
                        fw.close();
                        Toast.makeText(MainActivity.this,"Ystart: "+Ystart,Toast.LENGTH_SHORT).show();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getActivity(), "start"+String.valueOf(Xstart)+"  "+String.valueOf(Ystart), Toast.LENGTH_SHORT).show();
                }
                if(event.getActionMasked() == MotionEvent.ACTION_UP){
                    Xend = event.getX();
                    Yend = event.getY();
                    Xswipe = Xend;

                    String aup = "Xend"+String.valueOf(Xend)+ ", Yend: "+ String.valueOf(Yend)+ ", Pressure: "+ event.getPressure()+ "\n";

                    try {

                        FileWriter fw = new FileWriter(filepath,true);
                        fw.append(aup);
                        fw.flush();
                        fw.close();
                        //Toast.makeText(getActivity(),"X end: "+Xend,Toast.LENGTH_SHORT).show();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    // Toast.makeText(getActivity(),"Pressure:" +event.getPressure(),Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(getContext() ,"The x and Y are:"++" "+,Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

//    private class MyWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
//            return false;
//        }
//    }
}
