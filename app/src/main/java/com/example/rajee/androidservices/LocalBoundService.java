package com.example.rajee.androidservices;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class LocalBoundService extends Service {

    private final IBinder sBinder = new LocalBinder();
    private final Random sRandomGenerator = new Random();
    private String book_one;

    @Override
    public IBinder onBind(Intent intent) {
        book_one = intent.getStringExtra("book_one");
        return sBinder;
    }

    public int getRandomNumber(){
        return sRandomGenerator.nextInt();
    }

    public String getBookName(){
        return book_one;
    }


    public class LocalBinder extends Binder{
        LocalBoundService getService(){
            return LocalBoundService.this;
            //for client to get current instance of service; client can call public access methods using this.
        }
    }

}
