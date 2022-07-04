package com.mahza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.UnicodeSetSpanner;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {
    Button btn;
    WebView webview;
    public static AlertDialog dialog;
    private AlertDialog.Builder builder;
    private static final String LOG_TAG = "Otomatik internet Kontrol¸";
    private NetworkChangeReceiver receiver;
    private boolean isTouch = false;
    int konum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
        registerReceiver(receiver, filter);
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webview.loadUrl("about:blank");

            }
        });

/*
        if (isTouch == true) {

            Toast.makeText(this, "dokunuldu", Toast.LENGTH_SHORT).show();
        }

        for (int i = 0; i < 3000; i++) {


            System.out.println(i);
        }
*/

        String url = "https://mahza.org/";
        //String url = "https://docs.google.com/forms/u/0/";
        webview.loadUrl(url);
       // final ProgressDialog progress = ProgressDialog.show(this, "Lütfen Bekleyin", "Yükleniyor....", true);
         // progress.show();
        // if (url.startsWith("https://twitter.com/")){
        //   Toast.makeText(this, "twitter açılıyor", Toast.LENGTH_SHORT).show();
        //}
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("1");
        builder.setMessage("Lütfen internet bağlantınızı kontrol edin.");
        builder.setCancelable(false);
        // https://twitter.com/mahzatr
        // http://www.instagram.com/mahza.tr
        // http://mahzailetisim@aol.com/
        dialog = builder.create();

        konum = 0;
        //300000

        final CountDownTimer countDownTimer ;

             countDownTimer = new CountDownTimer(300000,1000) {
                 @Override
                 public void onTick(long millisUntilFinished) {

                 }

                 @Override
                 public void onFinish() {

                     System.exit(0);
                 }
             }.start();
             countDownTimer.cancel();

        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                countDownTimer.cancel();
                countDownTimer.start();

                return false;
            }
        });

        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);

            }
        });


        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("Sayfa Yüklendi");
                //Toast.makeText(getApplicationContext(), "Sayfa yüklendi", Toast.LENGTH_SHORT).show();
                //progress.dismiss();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //Toast.makeText(getApplicationContext(), "Bir hata oluştu", Toast.LENGTH_SHORT).show();
                //progress.dismiss();
            }
        });
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onDestroy() { //Activity Kapatıldığı zaman receiver durduralacak.Uygulama arka plana alındığı zamanda receiver çalışmaya devam eder
        Log.v(LOG_TAG, "onDestory");
        super.onDestroy();

        unregisterReceiver(receiver);//receiver durduruluyor

    }
}
