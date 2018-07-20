package com.dlsmartercity.testwifiprint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText ipaddress;
    public EditText port;
    public EditText text;
    public byte[] data;

    public RadioGroup RadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipaddress = findViewById(R.id.ipaddress);
        port = findViewById(R.id.port);
        text = findViewById(R.id.text);

        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        RadioGroup = findViewById(R.id.RadioGroup);
        RadioButton GBK = findViewById(R.id.GBK);
        RadioButton UTF8 = findViewById(R.id.UTF8);
        RadioButton GB2312 = findViewById(R.id.GB2312);
        RadioButton ANSI = findViewById(R.id.ANSI);
        RadioButton UNICODE = findViewById(R.id.UNICODE);
        RadioButton ASCII = findViewById(R.id.ASCII);
        RadioButton DBCS = findViewById(R.id.DBCS);
        RadioButton UCS = findViewById(R.id.UCS);

        button.setOnClickListener(this);

        RadioGroup.check(GBK.getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                print();
                break;
            default:
        }
    }

    public void setCharsetName() {
        switch (RadioGroup.getCheckedRadioButtonId()) {
            case R.id.GBK:
                setCodedFormat("GBK");
                break;
            case R.id.UTF8:
                setCodedFormat("UTF-8");
                break;
            case R.id.GB2312:
                setCodedFormat("GB2312");
                break;
            case R.id.ANSI:
                setCodedFormat("ANSI");
                break;
            case R.id.UNICODE:
                setCodedFormat("UNICODE");
                break;
            case R.id.ASCII:
                setCodedFormat("ASCII");
                break;
            case R.id.DBCS:
                setCodedFormat("DBCS");
                break;
            case R.id.UCS:
                setCodedFormat("UCS");
                break;
            default:
        }
    }

    public void setCodedFormat(String charsetName) {
        try {
            data = text.getText().toString().getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket mSocket = new Socket();
                try {
                    //通过主机名 获取ip地址
                    //InetAddress inetAddress = InetAddress.getByName(name);
                    InetSocketAddress inetSocketAddress = new InetSocketAddress(ipaddress.getText().toString(), Integer.parseInt(port.getText().toString()));
                    //连接socket
                    mSocket.connect(inetSocketAddress, 2000);
                    mSocket.setKeepAlive(true);
                    if (mSocket.isConnected() && mSocket.getKeepAlive()) {
                        //获取服务端的输入流,这里可用可不用，主要看产品
                        InputStream inputStream = mSocket.getInputStream();
                        //获取服务端的输出流，这个就一定要取到，因为这个关系到能不能向服务端发送出消息的操作
                        OutputStream os = mSocket.getOutputStream();
                        try {
                            setCharsetName();
                            os.write(data, 0, data.length);
                            os.flush();
                            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                            String mess = br.readLine();
                            Log.d("xxxx", "服务器：" + mess);
                        } catch (IOException e) {
                            Log.e("xxxx", "PrintText " + e);
                        }
                    } else {
                        Log.d("xxxx", "Client 连接失败");
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
