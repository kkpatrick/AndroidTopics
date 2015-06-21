package edu.nyu.xiaoqianyu.ipcservice.svc;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import edu.nyu.xiaoqianyu.ipcservice.IDownload;
/**
 * Created by abc on 6/21/15.
 */
public class DownloadService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return(new DownloadBinder());
    }

    private static class DownloadBinder extends IDownload.Stub {
        @Override
        public void download(String url) {
            new DownloadThread(url).start();
        }
    }

    private static class DownloadThread extends Thread {
        String url=null;

        DownloadThread(String url) {
            this.url=url;
        }

        @Override
        public void run() {
            try {
                File root=
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                root.mkdirs();

                File output=new File(root, Uri.parse(url).getLastPathSegment());

                if (output.exists()) {
                    output.delete();
                }

                HttpURLConnection c=(HttpURLConnection)new URL(url).openConnection();

                FileOutputStream fos=new FileOutputStream(output.getPath());
                BufferedOutputStream out=new BufferedOutputStream(fos);

                try {
                    InputStream in=c.getInputStream();
                    byte[] buffer=new byte[8192];
                    int len=0;

                    while ((len=in.read(buffer)) >= 0) {
                        out.write(buffer, 0, len);
                    }

                    out.flush();
                }
                finally {
                    fos.getFD().sync();
                    out.close();
                    c.disconnect();
                }
            }
            catch (IOException e2) {
                Log.e("DownloadJob", "Exception in download", e2);
            }
        }
    }
}
