package com.continuesvoicerecognition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.continuesvoicerecognition.data.rest.ApiClient;
import com.continuesvoicerecognition.data.rest.ApiInterface;
import com.continuesvoicerecognition.data.retrofit.GetTools;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Android Docs say very clearly that SpeechRecognition is not intended to use as Continues Speech Recognition.
 * You should try PocketSphinx, a very good library that react to "magic" word and react. the combination between the pocket
 * Sphinx and this implementation is a very good idea.
 * check out PocketSphinx here: https://github.com/cmusphinx/pocketsphinx-android-demo
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private TextView result_tv;
    private Button start_listen_btn,stop_listen_btn,mute;
    private SpeechRecognizerManager mSpeechManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setClickListeners();
    }


    private void findViews()
    {
        result_tv=(TextView)findViewById(R.id.result_tv);

    }


    private void setClickListeners()
    {

    }

    @Override
    protected void onResume() {
        if(mSpeechManager==null)
        {
            SetSpeechListener();
        }
        else if(!mSpeechManager.ismIsListening())
        {
            mSpeechManager.destroy();
            SetSpeechListener();
        }
        result_tv.setText(getString(R.string.you_may_speak));
        super.onResume();
    }

    @Override
    public void onClick(View v) {

//        if(PermissionHandler.checkPermission(this,PermissionHandler.RECORD_AUDIO)) {
//
//            switch (v.getId()) {
//                case R.id.start_listen_btn:
//
//
//                    break;
//                case R.id.stop_listen_btn:
//                    if(mSpeechManager!=null) {
//                        result_tv.setText(getString(R.string.destroied));
//                        mSpeechManager.destroy();
//                        mSpeechManager = null;
//                    }
//                    break;
//                case R.id.mute:
//                    if(mSpeechManager!=null) {
//                        if(mSpeechManager.isInMuteMode()) {
//                            mute.setText(getString(R.string.mute));
//                            mSpeechManager.mute(false);
//                        }
//                        else
//                        {
//                            mute.setText(getString(R.string.un_mute));
//                            mSpeechManager.mute(true);
//                        }
//                    }
//                    break;
//            }
//        }
//        else
//        {
//            PermissionHandler.askForPermission(PermissionHandler.RECORD_AUDIO,this);
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode)
        {
            case PermissionHandler.RECORD_AUDIO:
                if(grantResults.length>0) {
                    if(grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                        onResume();
                    }
                }
                break;

        }
    }

    private void SetSpeechListener()
    {
        mSpeechManager=new SpeechRecognizerManager(this, new SpeechRecognizerManager.onResultsReady() {
            @Override
            public void onResults(ArrayList<String> results) {



                if(results!=null && results.size()>0)
                {

                    if(results.size()==1)
                    {
                        mSpeechManager.destroy();
                        mSpeechManager = null;
                        result_tv.setText(results.get(0));
                        for (String result : results) {
                            Log.d("Judul","==++>>>>>"+result);

                            if(result.toString().equalsIgnoreCase("kitchen on")){
//                                hasil.setText("The lamp turn on bedroom");
                                lamp_on("1","1");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("kitchen off")){
//                                hasil.setText("The lamp turn off bedroom");
                                getar();
                                lamp_on("1","0");
                            }else if(result.toString().equalsIgnoreCase("toilet on")){
//                                hasil.setText("The lamp turn on kitchen");
                                lamp_on("2","1");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("toilet off")) {
//                                hasil.setText("The lamp turn off kitchen");
                                lamp_on("2","0");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("living room on")){
//                                hasil.setText("The lamp turn on living room");
                                lamp_on("3","1");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("living room off")) {
//                                hasil.setText("The lamp turn off living room");
                                lamp_on("3","0");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("bedroom on")){
//                                hasil.setText("The lamp turn on living room");
                                lamp_on("4","1");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("bedroom off")) {
//                                hasil.setText("The lamp turn off living room");
                                lamp_on("4","0");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("open the door")) {
//                                hasil.setText("The lamp turn off living room");
                                lamp_on("5","1");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("close the door")) {
//                                hasil.setText("The lamp turn off living room");
                                lamp_on("5","0");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("jarvis")) {
//                                hasil.setText("Yes master");

                                // Do something after 5s = 5000ms
                                finish();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);

                            }
//                            if(result.toString().equalsIgnoreCase("help")){
//                                hasil.setText("emergency 1");
//                            }else if(result.toString().equalsIgnoreCase("help help")){
//                                hasil.setText("emergency 2");
//                            }else if(result.toString().equalsIgnoreCase("help help help")){
//                                hasil.setText("emergency 3");
//
//                            }
                        }
                    }
                    else {
                        StringBuilder sb = new StringBuilder();
                        if (results.size() > 5) {
                            results = (ArrayList<String>) results.subList(0, 5);
                        }
                        for (String result : results) {
                            sb.append(result).append("\n");
                        }
                        for (String result : results) {
                            Log.d("Judul","==++>>>>>"+result);

                            if(result.toString().equalsIgnoreCase("kitchen on")){
//                                hasil.setText("The lamp turn on bedroom");
                                lamp_on("1","1");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("kitchen off")){
//                                hasil.setText("The lamp turn off bedroom");
                                getar();
                                lamp_on("1","0");
                            }else if(result.toString().equalsIgnoreCase("toilet on")){
//                                hasil.setText("The lamp turn on kitchen");
                                lamp_on("2","1");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("toilet off")) {
//                                hasil.setText("The lamp turn off kitchen");
                                lamp_on("2","0");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("living room on")){
//                                hasil.setText("The lamp turn on living room");
                                lamp_on("3","1");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("living room off")) {
//                                hasil.setText("The lamp turn off living room");
                                lamp_on("3","0");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("bedroom on")){
//                                hasil.setText("The lamp turn on living room");
                                lamp_on("4","1");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("bedroom off")) {
//                                hasil.setText("The lamp turn off living room");
                                lamp_on("4","0");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("open the door")) {
//                                hasil.setText("The lamp turn off living room");
                                lamp_on("5","1");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("close the door")) {
//                                hasil.setText("The lamp turn off living room");
                                lamp_on("5","0");
                                getar();
                            }else if(result.toString().equalsIgnoreCase("jarvis")) {
//                                hasil.setText("Yes master");

                                // Do something after 5s = 5000ms
                                finish();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);

                            }
//                            if(result.toString().equalsIgnoreCase("help")){
//                                hasil.setText("emergency 1");
//                            }else if(result.toString().equalsIgnoreCase("help help")){
//                                hasil.setText("emergency 2");
//                            }else if(result.toString().equalsIgnoreCase("help help help")){
//                                hasil.setText("emergency 3");
//
//                            }
                        }
                        result_tv.setText(sb.toString());
                    }
                }
                else
                    result_tv.setText(getString(R.string.no_results_found));
            }
        });
    }

    @Override
    protected void onPause() {
        if(mSpeechManager!=null) {
            mSpeechManager.destroy();
            mSpeechManager=null;
        }
        super.onPause();
    }
    protected void lamp_on(String id, String status){
        ApiInterface mApiInterface = ApiClient.GetTools().create(ApiInterface.class);
        final Call<GetTools> userCall = mApiInterface.user_insert(
                id.toString(),
                status.toString());
        userCall.enqueue(new Callback<GetTools>() {

            @Override
            public void onResponse(Call<GetTools> call, Response<GetTools> response) {

                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<GetTools> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed "+t.getCause(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getar(){
//        ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM, ToneGenerator.MAX_VOLUME);
//        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }
}
