package com.example.rajee.androidservices;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PDFDownloadActivity extends AppCompatActivity {

    LocalBoundService myBoundService;
    boolean bound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfdownload);

        Button btnStartDownload = (Button) findViewById(R.id.start_download);
        btnStartDownload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(bound == true){
                    int num = myBoundService.getRandomNumber();
                    String bo = myBoundService.getBookName();
                    //Toast.makeText(PDFDownloadActivity.this, "Bound: " + num, Toast.LENGTH_SHORT).show();
                    Toast.makeText(PDFDownloadActivity.this, "Bound: " + bo, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private ServiceConnection bConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalBoundService.LocalBinder binder = (LocalBoundService.LocalBinder) service;
            myBoundService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LocalBoundService.class);
        intent.putExtra("book_one", ((TextView) findViewById(R.id.pdf_one)).getText().toString());
        bindService(intent, bConnection, getApplicationContext().BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bound == true){
            unbindService(bConnection);
            bound = false;
        }
    }

}
