package edu.nyu.xiaoqianyu.ipcclient.client;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import edu.nyu.xiaoqianyu.ipcclient.IDownload;
import edu.nyu.xiaoqianyu.ipcclient.R;


public class DownloadFragment extends Fragment implements
        View.OnClickListener, ServiceConnection {
    private static final String TO_DOWNLOAD="https://commonsware.com/Android/excerpt.pdf";
    private IDownload binding=null;
    private Button btn=null;
    private Application appContext=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.fragment_download, container, false);

        btn=(Button)result.findViewById(R.id.go);
        btn.setOnClickListener(this);
        btn.setEnabled(binding!=null);

        return(result);
    }

    @Override
    public void onAttach(Activity host) {
        super.onAttach(host);

        if (appContext==null) {
            appContext=(Application)host.getApplicationContext();

            Intent implicit=new Intent(IDownload.class.getName());
            List<ResolveInfo> matches=host.getPackageManager()
                    .queryIntentServices(implicit, 0);

            if (matches.size() == 0) {
                Toast.makeText(host, "Cannot find a matching service!",
                        Toast.LENGTH_LONG).show();
            }
            else if (matches.size() > 1) {
                Toast.makeText(host, "Found multiple matching services!",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Intent explicit=new Intent(implicit);
                ServiceInfo svcInfo=matches.get(0).serviceInfo;
                ComponentName cn=new ComponentName(svcInfo.applicationInfo.packageName,
                        svcInfo.name);

                explicit.setComponent(cn);
                appContext.bindService(explicit, this, Context.BIND_AUTO_CREATE);
            }
        }
    }

    @Override
    public void onDestroy() {
        appContext.unbindService(this);
        disconnect();

        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        try {
            binding.download(TO_DOWNLOAD);
        }
        catch (RemoteException e) {
            Log.e(getClass().getSimpleName(), "Exception requesting download", e);
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onServiceConnected(ComponentName className, IBinder binder) {
        binding=IDownload.Stub.asInterface(binder);
        btn.setEnabled(true);
    }

    @Override
    public void onServiceDisconnected(ComponentName className) {
        disconnect();
    }

    private void disconnect() {
        binding=null;
        btn.setEnabled(false);
    }
}
