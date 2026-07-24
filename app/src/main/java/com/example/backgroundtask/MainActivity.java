package com.example.backgroundtask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public class Bg extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("amit","This is Pre  Execution");
        }

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection conn;
            try {

            url = new URL(urls[0]);
            conn = (HttpURLConnection) url.openConnection();
            InputStream inp = conn.getInputStream();
            InputStreamReader reader = new InputStreamReader(inp);
            int data = reader.read();
            while(data!=-1){
                char current = (char)data;
                result+=current;
                data = reader.read();
            }
            }catch (Exception e){
                e.printStackTrace();
                return "Something went wrong";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("amit","After bg post execute");
            Log.d("amit",s);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bg myTask = new Bg();
        myTask.execute("https://www.codewithharry.com/");

    }
    public void clickButton(View view){
        Toast.makeText(this, "Selfie Clicked!", Toast.LENGTH_SHORT).show();

    }
}