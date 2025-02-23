package com.hackx.usermain.bot;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;


import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.HashMap;
import java.util.Map;

public class TranslateHelper {

    private static final String TAG = "TranslateHelper";

    public static void translateText(Context context, RequestQueue queue, String text, String targetLang, TranslationCallback callback) {
        // Construct API URL with proper encoding
        String apiUrl = "https://translate.googleapis.com/translate_a/single?client=gtx&sl="+targetLang+"&tl=en&dt=t&q=" + Uri.encode(text);

        // Create a StringRequest with GET method
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Log the full API response
                            Log.d(TAG, "API Response: " + response);

                            // Parse JSON response
                            JSONArray jsonResponse = new JSONArray(response);
                            String translatedText = jsonResponse.getJSONArray(0)
                                    .getJSONArray(0)
                                    .getString(0);

                            // Callback with success result
                            callback.onSuccess(translatedText);
                        } catch (JSONException e) {
                            Log.e(TAG, "Parsing error: " + e.getMessage());
                            callback.onError("Parsing error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "API request failed: " + error.toString());
                        callback.onError("API request failed: " + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Set request headers
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Mozilla/5.0");
                headers.put("Accept", "application/json");
                headers.put("Accept-Encoding", "identity"); // Avoid compressed response
                return headers;
            }
        };

        // Add request to queue
        queue.add(stringRequest);
    }

    public interface TranslationCallback {
        void onSuccess(String translatedText);
        void onError(String error);
    }
}
