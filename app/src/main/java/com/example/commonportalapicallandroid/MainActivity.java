package com.example.commonportalapicallandroid;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
     TextView helloTextView;
    String result ;

    public MainActivity() {
        helloTextView = null;
        result = new String();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helloTextView=  (TextView) findViewById(R.id.text_view_id);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }




    }

    public void PostResultMethod(View view) {

        new Thread(new Runnable() {
            public void run() {
                // a potentially time consuming task
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {

                            result =new ApiCall().GetContent();
                            setText(  helloTextView, result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        helloTextView.setText(result);
                    }
                });


            }
        }).start();

    }

    private void setText(final TextView text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    text.setText(Html.fromHtml(value,Html.FROM_HTML_MODE_LEGACY));
                } else {
                    text.setText(Html.fromHtml(value));
                }

                //text.setText(   Html.fromHtml(value) );
            }
        });
    }

    public void GetResultMethod(View view) {


        new Thread(new Runnable() {
            public void run() {
                // a potentially time consuming task
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {

                            result =new ApiCall().GetContent();
                            setText(  helloTextView, result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        helloTextView.setText(result);
                    }
                });


            }
        }).start();



    }



    public void  GetCommonPortalDataResultMethod (View view) {

        new Thread(new Runnable() {
            public void run() {
                // a potentially time consuming task
                runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        try {

                            result =new ApiCall().GetCommonPortalContent();
                            setText(  helloTextView, result);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        helloTextView.setText(result);
                    }
                });


            }
        }).start();
    }

}
