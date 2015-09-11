package com.example.lishuai.baidupush;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //界面元素
    private Button start;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = (Button) findViewById(R.id.button1);
        stop = (Button) findViewById(R.id.button2);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
    }

    @Override
    public void onClick(View v) {
        DataInputStream dis = null;
        HttpURLConnection conn = null;
        TextView lblTitle=(TextView)findViewById(R.id.textView);

        switch (v.getId()) {
            case R.id.button1:
                lblTitle.setText("first");
                break;
            case R.id.button2:
                lblTitle.setText("second");

                String str1="";
                EditText editText1 =(EditText)findViewById(R.id.editText);
                str1=editText1.getText().toString();
                String uriAPI = "http://10.128.38.132:8080/index.jsp?first_name="+str1+"@string/app_name";
                try {

                    URL url = new URL(uriAPI);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000); //设置连接超时为5秒
                    conn.setRequestMethod("GET"); //设定请求方式
                    conn.connect(); //建立到远程对象的实际连接
                    dis = new DataInputStream(conn.getInputStream());
                    String s;
                    while ((s = dis.readUTF()) != null)
                        lblTitle.setText(s);

                    //判断是否正常响应数据
                    if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        lblTitle.setText("网络错误异常！!!!");
                    }
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                    lblTitle.setText("second55555");
                } catch (IOException e) {
                    e.printStackTrace();
                    lblTitle.setText(e.toString());
                }
                //lblTitle.setText("second11111");
                break;
            default:
                break;
        }
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
