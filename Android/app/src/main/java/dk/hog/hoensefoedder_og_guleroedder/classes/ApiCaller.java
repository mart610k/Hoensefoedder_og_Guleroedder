package dk.hog.hoensefoedder_og_guleroedder.classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import dk.hog.hoensefoedder_og_guleroedder.Datamodel.APIResponse;

public class ApiCaller {

    // Gets the data from the API based on the endpoint
    public JSONArray GetAPIData(String methodType, String endPoint){
        JSONArray response = null;
        HttpURLConnection connection = null;

        try {
            // Trying to make the connection
            String baseUrl = "http://192.168.1.5:5000/api";
            URL urlObject = new URL(baseUrl + endPoint);
            connection = (HttpURLConnection) urlObject.openConnection();

            if(connection != null) {
                Log.i("Connection", "connection: Open");
            }
            else {
                Log.i("Connection", "connection: Closed");
            }

            // Defines if it's a Post, Get and such
            connection.setRequestMethod(methodType);

            int responseCode = connection.getResponseCode();
            StringBuffer stringBuffer = new StringBuffer();

            // checks if the responsecode is okay, and reads data if it is
            try {
                BufferedReader in = null;
                if(responseCode >=200 && responseCode <=300) {
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                }

                String input;
                // if reader isn't null, add the response
                while ((input = in.readLine()) != null){
                    stringBuffer.append(input);
                    response = new JSONArray(stringBuffer.toString());
                }



            } catch (JSONException e) {
                Log.i("Exception", e.toString());
            }
        }
        catch (Exception e) {
            Log.i("Exception", e.toString());
        }

        return response;
    }
}
