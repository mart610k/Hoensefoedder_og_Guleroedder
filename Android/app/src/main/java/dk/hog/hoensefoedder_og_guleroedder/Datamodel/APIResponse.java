package dk.hog.hoensefoedder_og_guleroedder.Datamodel;

import org.json.JSONArray;

public class APIResponse {
    private int responseCode;
    private JSONArray responseBody;

    public APIResponse(JSONArray responseBody, int responseCode) {
        this.responseBody = responseBody;
        this.responseCode = responseCode;
    }

    public JSONArray getResponseBody(){
        return responseBody;
    }

    public int getResponseCode() {
        return responseCode;
    }

}
