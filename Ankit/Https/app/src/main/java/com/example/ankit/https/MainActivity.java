package com.example.ankit.https;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HashMap<String, String> map = new HashMap<>();
        map.put("controlVar","55");
        map.put("full_name","relsell global");
        new FetchDataUserTask(map).execute();
    }




    public class FetchDataUserTask extends AsyncTask<Void, Integer, Boolean> {


        StringBuffer responseString = new StringBuffer("");
        int mConnectionCode;
        HashMap<String, String> hmVars;


        public FetchDataUserTask(HashMap<String, String> hm) {
            this.hmVars = hm;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                String server = "relsellglobal.in";
                String port = "80";
                String urlToHit = "http://" + server + ":" + port + "/DeliveryS/CRKInsightsServer/mobilecheck.php";
                String urlData = getUrlStringData(hmVars);

                if (urlData.substring(urlData.length() - 1).equalsIgnoreCase("&")) {
                    urlData = urlData.substring(0, (urlData.length() - 1));
                }
                urlToHit += "?" + urlData;

                URL url = new URL(urlToHit);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                connection.setDoInput(true);


                mConnectionCode = connection.getResponseCode();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (mConnectionCode == HttpURLConnection.HTTP_OK) {

                    InputStream inputStream = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));

                    String line = "";

                    while ((line = rd.readLine()) != null) {
                        responseString.append(line);
                    }

                    return true;
                }
            } catch (IOException e) {
                Log.v("Message", e.getMessage());
                e.printStackTrace();
                return false;

            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success)
        {

            if (success) {
                System.out.println("Response String " + responseString);


            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);


        }


        public String getUrlStringDataStr(HashMap<String, String> map) {
            String result = "";

            Set<String> myset = map.keySet();
            int setSize = myset != null ? myset.size() : 0;
            int i = 0;

            for (String key : myset) {
                String value = map.get(key);
                result += key + "=" + value;
                if (i <= setSize - 1)
                    result += "&";
                i++;
            }
            return result;
        }

        public String getUrlStringData(HashMap<String, String> incomingHm) throws UnsupportedEncodingException {

            String result = "";

            Set<String> keys = incomingHm.keySet();
            HashMap<String, String> map = new HashMap<String, String>();
            for (String key : keys) {
                map.put(key, URLEncoder.encode(incomingHm.get(key), "UTF-8"));
            }
            result = getUrlStringDataStr(map);

            System.out.println(result);

            return result;
        }
    }
}