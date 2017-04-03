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
import java.util.Random;

public class LocalBoundService extends Service {

    private final IBinder sBinder = new LocalBinder();
    private final Random sRandomGenerator = new Random();
    private String book_one;
    private String book_two;
    private String book_three;
    private String book_four;
    private String book_five;

    @Override
    public IBinder onBind(Intent intent) {
        return sBinder;
    }

    public int getRandomNumber(){
        return sRandomGenerator.nextInt();
    }


    public void downloadFiles(String... urlStr){
        BackTask backTask = new BackTask();
        backTask.execute(urlStr[0]);
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
            InputStream is = null;
            FileOutputStream fos = null;
            int count;
            try{
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                is = conn.getInputStream();
                String path = url.getPath();
                String filename = path.substring(path.lastIndexOf('/')+1);
                String writePath = Environment.getExternalStorageDirectory() + "/" + filename;
                Log.v("Write Path: ", writePath);
                fos = new FileOutputStream(writePath);
                int lengthOfFile = conn.getContentLength();
                byte data[] = new byte[1024];
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(LocalBoundService.this, "Download finished", Toast.LENGTH_LONG).show();
        }
    }


}
