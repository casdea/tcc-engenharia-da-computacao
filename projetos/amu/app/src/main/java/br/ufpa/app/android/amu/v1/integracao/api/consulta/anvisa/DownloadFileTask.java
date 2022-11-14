package br.ufpa.app.android.amu.v1.integracao.api.consulta.anvisa;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class DownloadFileTask extends AsyncTask<String, Void, Void> {


    private static final String TAG = "MainActivity";
    private static final String[] PERMISSIONS = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected Void doInBackground(String... strings) {
        Log.v(TAG, "doInBackground() Method invoked ");

        String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
        String fileName = strings[1];  // -> maven.pdf
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        File pdfFile = new File(folder, fileName);
        Log.v(TAG, "doInBackground() pdfFile invoked " + pdfFile.getAbsolutePath());
        Log.v(TAG, "doInBackground() pdfFile invoked " + pdfFile.getAbsoluteFile());

        try {
            pdfFile.createNewFile();
            Log.v(TAG, "doInBackground() file created" + pdfFile);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "doInBackground() error" + e.getMessage());
            Log.e(TAG, "doInBackground() error" + e.getStackTrace());


        }
        FileDownloader.downloadFile(fileUrl, pdfFile);
        Log.v(TAG, "doInBackground() file download completed");

        return null;
    }
}
