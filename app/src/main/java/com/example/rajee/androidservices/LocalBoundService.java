package com.example.rajee.androidservices;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LocalBoundService extends Service {

    private final IBinder sBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return sBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(LocalBoundService.this, "UNBINDING", Toast.LENGTH_LONG).show();
        return super.onUnbind(intent);
    }


    public void downloadFiles(String... urlStr){
        BackTask backTask = new BackTask();
        backTask.execute(urlStr);
    }


    ////////////////////////////////////////
    public class LocalBinder extends Binder{
        LocalBoundService getService(){
            return LocalBoundService.this;
            //for client to get current instance of service; client can call public access methods using this.
        }
    }


    ////////////////////////////////////////
    private class BackTask extends AsyncTask<String, Integer, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection conn;
            String path;
            String filename;
            String writePath;
            byte data[];
            InputStream is = null;
            FileOutputStream fos = null;
            int count;
            for(int i=0; i<params.length; i++){
                try{
                    URL url = new URL(params[i]);
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
                    if(is != null){
                        try{
                            is.close();
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if(fos != null){
                        try{
                            fos.close();
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


}
