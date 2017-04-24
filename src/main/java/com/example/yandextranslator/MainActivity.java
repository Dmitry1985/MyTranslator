package com.example.yandextranslator;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yandextranslator.utils.Languages;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView OutputText;
    Button bTranslate;
    Button  fromLangBut;
    Button  toLangBut;
    EditText etUserText;
    String etText;
    String fromLang;
    String toLang ;
    String fromLangButText;
    String toLangButText;
    Languages x = new Languages();
    Map<String, String> hashMap = x.getMyHash();
    private static final String TAG = "MainLog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar topToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        OutputText = (TextView) findViewById(R.id.OutputText);
        etUserText = (EditText) findViewById(R.id.etUserText);
        fromLangBut = (Button) findViewById(R.id.fromLang);
        toLangBut = (Button) findViewById(R.id.toLang);

        setSupportActionBar(topToolbar);


            fromLangButText = fromLangBut.getText().toString();
            toLangButText = toLangBut.getText().toString();

        fromLang = hashMap.get(fromLangBut.getText());
         toLang = hashMap.get(toLangBut.getText());
            Log.d(TAG, fromLang);
            Log.d(TAG, toLang);
            Log.d(TAG, fromLangButText + toLangButText);






            etUserText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    etText = etUserText.getText().toString();
                    TranslatedText trText = new TranslatedText();

                    trText.execute();

                }


            });


    }

    public void chooseLangFrom(View view){

        Intent intent = new Intent(this, LangList.class);
        startActivityForResult(intent, 1);

    }
    public void chooseLangTo(View view){

        Intent intent = new Intent(this, LangList.class);
        startActivityForResult(intent, 2);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String nameN = data.getStringExtra("name");

        if (requestCode == 1) {
            fromLangBut.setText(nameN);
            fromLang = hashMap.get(nameN);
        } else {

            toLangBut.setText(nameN);
            toLang = hashMap.get(nameN);
        }


    }

    public void changeLang(View view){

        String a;

        a = fromLang;
        fromLang = toLang;
        toLang = a;

        fromLangButText = toLangBut.getText().toString();
        toLangButText = fromLangBut.getText().toString();
        fromLangBut.setText(fromLangButText);
        toLangBut.setText(toLangButText);



    }
    private class TranslatedText extends AsyncTask <String, Void, String> {

        String text;


        String yaUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
        String yaKey = "key=trnsl.1.1.20170412T084032Z.e1ed3849addfc061.1ccf22240beeee6c2e0a7dd50f4c584376a107a3&text=";
        String urls = yaUrl + yaKey + etText + "&lang=" + fromLang + "-" + toLang;


        @Override
        protected String doInBackground(String ... params) {

            String line;
            try{

                URL url = new URL(urls);
                HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
                InputStream in = new BufferedInputStream(con.getInputStream());
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while((line = reader.readLine())!=null) {
                    sb.append(line + "\n");
                }
                 JSONObject json = new JSONObject(sb.toString());
                 text = json.getString("text");
                 text = text.substring(2, text.length()-2) ;


            }
            catch(MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch(IOException e){

                e.printStackTrace();

            }
            catch(JSONException e){

                e.printStackTrace();
            }

            return text;
        }

        @Override
        protected void onPostExecute(String s) {

            OutputText.setText(s);
        }
    }

}





