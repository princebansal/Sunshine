package com.example.sunshine;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Prince Bansal Local on 14-07-2016.
 */

public class DataLayerListenerService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        Log.i("msg", "onMessageReceived: ");
        if(messageEvent.getPath().equals("/sunshine")){
            final String message=new String(messageEvent.getData());
            if(!TextUtils.isEmpty(message)) {
                try {
                    Log.i("watch0", "onMessageReceived: "+message);
                    JSONObject jsonObject=new JSONObject(message);
                    if(jsonObject.has("id")&&jsonObject.has("low")&&jsonObject.has("high")&&jsonObject.has("desc")) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("id",jsonObject.getInt("id"));
                        editor.putString("low", String.valueOf(jsonObject.getDouble("low")));
                        editor.putString("high", String.valueOf(jsonObject.getDouble("high")));
                        editor.putString("desc", String.valueOf(jsonObject.getDouble("desc")));
                        editor.commit();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
