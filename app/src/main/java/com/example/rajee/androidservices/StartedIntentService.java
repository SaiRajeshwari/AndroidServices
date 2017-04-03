package com.example.rajee.androidservices;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;


public class StartedIntentService extends IntentService {

    public StartedIntentService() {
        super("StartedIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        Set<String> keys = bundle.keySet();
        Iterator<String> iterator = keys.iterator();

        HttpURLConnection conn;
        String path;
        String filename;
        String writePath;
        byte data[];
        InputStream is = null;
        FileOutputStream fos = null;
        int count;

        while(iterator.hasNext()){
            try{
                URL url = new URL(bundle.get(iterator.next()).toString());
                conn = (HttpURLConnection) url.openConnection();
                is = conn.getInputStream();
                path = url.getPath();
                filename = path.substring(path.lastIndexOf('/')+1);
                writePath = Environment.getExternalStorageDirectory() + "/" + filename;
                Log.v("Write Path: ", writePath);
                fos = new FileOutputStream(writePath);
                data = new byte[1024];
                while((count = is.read(data)) != -1){
                    fos.write(data, 0, count);
                }
                fos.flush();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
