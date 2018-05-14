package com.e.k.m.a.financial.discounts;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.e.k.m.a.financial.R;

public class DiscountsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discounts);
    }
    public void open1(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.qasralawani.com/index.php?route=product/category&path=117&gclid=EAIaIQobChMI6fyckvaq2gIV5RXTCh1rkAAiEAMYAiAAEgKP3PD_BwE"));
        startActivity(intent);
    }public void open2(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://promotions.panda.com.sa/"));
        startActivity(intent);
    }public void open3(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://ar-sa.namshi.com"));
        startActivity(intent);
    }public void open4(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://m.shein.com/pwa/ar-en/?utm_source=admitad.com&utm_medium=affiliate&admitad_uid=bbe33b3e1139295222ad299ec9ecd46b&affiliateID=620215&url_from=admitad.com&ref=ar&rep=dir&ret=mar&ref=mar&rep=dir&ret=pwaren"));
        startActivity(intent);
    }
    public void open5(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.homecentre.com/sa/ar/?gclid=EAIaIQobChMIgKDD3Paq2gIVKCjTCh1_xAvLEAAYASAAEgIuSPD_BwE"));
        startActivity(intent);
    }

}
