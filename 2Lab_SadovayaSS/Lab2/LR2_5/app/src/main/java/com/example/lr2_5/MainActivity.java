package com.example.lr2_5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        //String url = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/data/techs.ruleset.json";

        MyTask t = new MyTask();
        t.execute(getString(R.string.JSONURL));
    }



    class MyTask extends AsyncTask<String, Void, Void> {
        private OkHttpClient client = new OkHttpClient();
        private String[] names;
        private String[] imgs;
        private String[] texts;

        @Override
        protected Void doInBackground(String... urls) {
            String jstr;
            Request request = new Request.Builder()
                    .url(urls[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                jstr =  response.body().string();

                JSONArray jsonF = new JSONArray(jstr);

                names = new String[jsonF.length()-1];
                imgs = new String[jsonF.length()-1];
                texts = new String[jsonF.length()-1];

                for(int i=1; i<jsonF.length(); i++){
                    JSONObject t = jsonF.getJSONObject(i);
                    names[i-1] = t.getString("name");
                    imgs[i-1] = t.getString("graphic");
                    if(t.has("helptext")) texts[i-1] = t.getString("helptext");
                    else texts[i-1] = "";
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            Bundle bundle = new Bundle();
            bundle.putStringArray("names", names);
            bundle.putStringArray("imgs", imgs);
            bundle.putStringArray("texts", texts);

            ListFragment listFragment = new ListFragment();
            listFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, listFragment);
            fragmentTransaction.commit();
        }
    }
}
