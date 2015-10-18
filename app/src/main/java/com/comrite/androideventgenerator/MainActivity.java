package com.comrite.androideventgenerator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;



public class MainActivity extends AppCompatActivity {


    public static final String AWS_ACCOUNT_ID = "509113609899";
    public static final String COGNITO_POOL_ID = "us-east-1:24e83ef6-e582-45c6-abeb-217a609071f4";
    public static final String COGNITO_ROLE_AUTH = "arn:aws:iam::509113609899:role/Cognito_comriteAuth_Role";
    public static final String COGNTIO_ROLE_UNAUTH = "arn:aws:iam::509113609899:role/Cognito_comriteUnauth_Role";




    // To use Lambda, first define methods in an interface (a placeholder)
    public interface LambdaFunctionsInterface {

        /**
         * Invoke lambda function "cloudFunction" (name of the function to be called in this app: localFunction
         */
      @LambdaFunction(functionName="cloudFunction") //this is defined in Lambda in the cloud using the Management Console.
       String localFunction(String nameInfo);

        //Add more Lambda functions here
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


// Create an instance of CognitoCachingCredentialsProvider
        CognitoCachingCredentialsProvider cognitoProvider = new CognitoCachingCredentialsProvider(
                this.getApplicationContext(), COGNITO_POOL_ID, Regions.US_EAST_1);

// Create LambdaInvokerFactory, to be used to instantiate the Lambda proxy.
        LambdaInvokerFactory factory = new LambdaInvokerFactory(this.getApplicationContext(),
                Regions.US_EAST_1, cognitoProvider);

// Create the Lambda proxy object with a default Json data binder.
// You can provide your own data binder by implementing
// LambdaDataBinder.

        final LambdaFunctionsInterface myInterface = factory.build(LambdaFunctionsInterface.class);

// The Lambda function invocation results in a network call.
// Make sure it is not called from the main thread.
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                try {
                    return myInterface.localFunction(params[0]);

                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke echo", lfe);
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
        }.execute("Hello");




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }






}
