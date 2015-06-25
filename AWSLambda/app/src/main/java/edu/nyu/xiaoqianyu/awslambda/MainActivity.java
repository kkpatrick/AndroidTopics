package edu.nyu.xiaoqianyu.awslambda;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;

public class MainActivity extends Activity {

    private static final String IDENTITY_POOL_ID = "us-east-1:924da877-ab64-4465-ac19-64bd2ac4e143";
    private static final Regions REGION = Regions.US_EAST_1;
    private MyInterface myInterface;
    private static final String TAG = "AWS_Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Create an instance of CognitoCachingCredentialsProvider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                this.getApplicationContext(),
                IDENTITY_POOL_ID,
                REGION);

        // Create a LambdaInvokerFactory, to be used to instantiate the Lambda proxy
        LambdaInvokerFactory factory = new LambdaInvokerFactory(
                this.getApplicationContext(),
                REGION,
                credentialsProvider);

        // Create the Lambda proxy object with default Json data binder.
        // You can provide your own data binder by implementing
        // LambdaDataBinder
        myInterface = factory.build(MyInterface.class);

        NameInfo nameInfo = new NameInfo("John", "Doe");

        // The Lambda function invocation results in a network call
        // Make sure it is not called from the main thread
        new AsyncTask<NameInfo, Void, String>() {
            @Override
            protected String doInBackground(NameInfo... params) {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                try {
                    String res = myInterface.javaTestFunction(params[0]);
                    System.out.println("After invoke, the first name is " + params[0].getFirstName());
                    return res;
                } catch (LambdaFunctionException lfe) {
                    Log.e(TAG, "Failed to invoke echo", lfe);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result == null) {
                    return;
                }

                // Do a toast
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }
        }.execute(nameInfo);
    }



}
