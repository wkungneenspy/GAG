package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class RestService {

    private final String USER_AGENT = "Mozilla/5.0";

    public static JSONObject sendPost(String url, List<Object> key, List<Object> value) {

        if (key.size() == value.size()) {
            try {
                String urlParameters = "";
                for (int i = 0; i < key.size(); i++) {
                    if (i != key.size() - 1) {
                        urlParameters = urlParameters + key.get(i) + "=" + value.get(i) + "&";
                    } else {
                        urlParameters = urlParameters + key.get(i) + "=" + value.get(i);
                    }
                }

                System.out.println("***  " + urlParameters);
                //String encodedURL=url+"?"+urlParameters;
                String encodedURL=/*java.net.URLEncoder.encode(*/urlParameters.trim()/*,"UTF-8")*/;
                URL obj = new URL(url+"?"+encodedURL);

                //HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                //add request header
                con.setRequestMethod("GET");
               // con.setRequestProperty("User-Agent", USER_AGENT);
                			//con.setRequestProperty("user", "willyk");
                //con.setRequestProperty("password", "willyk");

                // Send post request
                /*con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);
                */
                System.out.println("\nSending 'GET' request to URL : " + encodedURL);
                
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println("Response "+response.toString());

                //Treatment of the JSON response.
                JSONObject obj1;
                obj1 = new JSONObject(response.toString());

                return obj1;
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {

            try {
                JSONObject obj1;
                obj1 = new JSONObject("{ status : false, description : 'code wrong, Please Ckeck', subscriber_message : '' }");
                return obj1;
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void main(String[] args) throws Exception {

        RestService http = new RestService();

	//	System.out.println("Testing 1 - Send Http GET request");
        //	http.sendGet();
        /*System.out.println("\nTesting 2 - Send Http POST request");

        String url = "http://192.168.1.4:8080/HomeTrack.Restfull/resources/userService/logout";
        List<Object> key = new ArrayList<>();
        key.add("sessionID");

        List<Object> value = new ArrayList<>();
        value.add("");

        JSONObject resp = http.sendPost(url, key, value);*/

        /*Boolean o = (Boolean) resp.get("status");
         String o1 = (String) resp.get("description");
         String o2 = (String) resp.get("subscriber_message");
		
         System.out.println(o.toString());
         System.out.println(o1.toString());*/
    }
}
