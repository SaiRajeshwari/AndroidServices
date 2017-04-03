package com.example.rajee.androidservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PDFDownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfdownload);

        final String book_one = ((TextView) findViewById(R.id.pdf_one)).getText().toString();
        final String book_two = ((TextView) findViewById(R.id.pdf_two)).getText().toString();
        final String book_three = ((TextView) findViewById(R.id.pdf_three)).getText().toString();
        final String book_four = ((TextView) findViewById(R.id.pdf_four)).getText().toString();
        final String book_five = ((TextView) findViewById(R.id.pdf_five)).getText().toString();

        Button btnStartDownload = (Button) findViewById(R.id.start_download);
        btnStartDownload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PDFDownloadActivity.this, StartedIntentService.class);
                intent.putExtra("book_one", book_one);
                intent.putExtra("book_two", book_two);
                intent.putExtra("book_three", book_three);
                intent.putExtra("book_four", book_four);
                intent.putExtra("book_five", book_five);
                startService(intent);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
