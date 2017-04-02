package com.example.rajee.androidservices;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PDFDownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfdownload);

        //Log.v("PDF 1:", ((TextView)findViewById(R.id.pdf_one)).getText().toString());
    }
}
