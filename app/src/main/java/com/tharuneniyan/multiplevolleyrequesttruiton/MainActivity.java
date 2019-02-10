package com.tharuneniyan.multiplevolleyrequesttruiton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements Response.Listener,
        Response.ErrorListener {
    public static final String REQUEST_TAG = "MainVolleyActivity";
    private TextView mTextView, mTextView2;
    private Button mButton;
    private RequestQueue mQueue;
    private Object REQUEST_TAG2 = "MainVolleyActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView);
        mTextView2 = findViewById(R.id.textView2);
        mButton = findViewById(R.id.button);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Instantiate the RequestQueue.
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        String url = "https://simplifiedcoding.net/demos/view-flipper/heroes.php";
        String url2 = "http://www.mocky.io/v2/5c5f1e44320000c00c40b47f";
        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method
                .GET, url,
                new JSONObject(), this, this);
        jsonRequest.setTag(REQUEST_TAG);
        final CustomJSONObjectRequest jsonRequest2 = new CustomJSONObjectRequest(Request.Method
                .GET, url2,
                new JSONObject(), this, this);
        jsonRequest.setTag(REQUEST_TAG2);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue.add(jsonRequest);
                mQueue.add(jsonRequest2);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
            mQueue.cancelAll(REQUEST_TAG2);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mTextView.setText(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {
        mTextView.setText("Response is: " + response);
        try {
            mTextView.setText(mTextView.getText() + "\n\n" + ((JSONObject) response).getString("name"));

            mTextView2.setText(mTextView2.getText()+"\n\n"+((JSONObject) response).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
