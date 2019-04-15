package Server;

import android.util.Log;

import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Model.User;

public class Communicator {
    final static String HOST = "10.0.2.2";
    final static String PORT = "8080";
    private ServerData serverData = ServerData.getInstance();

    public class LoginFailedException extends Exception {}

    public JSONObject sendLoginRequest() throws LoginFailedException{
        String inputUrl = "http://" + serverData.getServerHost() + ":" + serverData.getServerPort();
        User clientUser = serverData.getNewUser();
        try {
            JSONObject requestBodyJson = new JSONObject();
            requestBodyJson.put("userName", clientUser.getUserName());
            requestBodyJson.put("password", clientUser.getPassword());

            URL finalUrl = new URL(inputUrl + "/user/login/");
            HttpURLConnection connection = connectAndSend(finalUrl, requestBodyJson.toString());

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                JSONObject responseBodyJson = new JSONObject(responseBodyData);
//                if (responseBodyJson.has("message")) {
//                    responseBodyJson = null;
//                }
                return responseBodyJson;
            }
            else {
                throw new LoginFailedException();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject sendRegisterRequest() throws LoginFailedException{
        String inputUrl = "http://" + serverData.getServerHost() + ":" + serverData.getServerPort();
        User clientUser = serverData.getNewUser();
        try {
            JSONObject requestBodyJson = new JSONObject();
            requestBodyJson.put("userName", clientUser.getUserName());
            requestBodyJson.put("password", clientUser.getPassword());
            requestBodyJson.put("email", clientUser.getEmail());
            requestBodyJson.put("firstName", clientUser.getFirstName());
            requestBodyJson.put("lastName", clientUser.getLastName());
            requestBodyJson.put("gender", clientUser.getGender());

            URL finalUrl = new URL(inputUrl + "/user/register/");
            HttpURLConnection connection = connectAndSend(finalUrl, requestBodyJson.toString());

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                JSONObject responseBodyJson = new JSONObject(responseBodyData);

                return responseBodyJson;
            }
            else {
                throw new LoginFailedException();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject requestFillUser() {
        try {
            URL url = new URL( "http://" + serverData.getServerHost() + ":" + serverData.getServerPort() + "/fill/" + serverData.getUser().getUserName());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseBody = connection.getInputStream();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                String responseBodyData = baos.toString();
                JSONObject responseBodyJson = new JSONObject(responseBodyData);
                return responseBodyJson;
            } else {
                Log.e("HTTP", "The event http request did not work.");
                return null;
            }
        } catch (Exception e) {
            Log.e("IO", "The event io http did not work");
            return null;
        }
    }
    public JSONObject getEvents() {
        try {
            URL url = new URL("http://" + serverData.getServerHost() + ":" + serverData.getServerPort() + "/event");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Set HTTP request header
            connection.addRequestProperty("Authorization", serverData.getToken().getAuthorization());
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get HTTP response headers, if necessary
                // Map<String, List<String>> headers = connection.getHeaderFields();
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                JSONObject responseBodyJson = new JSONObject(responseBodyData);
                return responseBodyJson;
            } else {
                Log.e("HTTP", "The event http request did not work.");
                return null;
            }
        } catch (Exception e) {
            Log.e("IO", "The event io http did not work");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public JSONObject getPeople()  {
        try {
            URL url = new URL("http://" + serverData.getServerHost() + ":" + serverData.getServerPort() + "/person");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Set HTTP request header
            connection.addRequestProperty("Authorization", serverData.getToken().getAuthorization());
            connection.connect();

            System.out.println(connection.getResponseCode());
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get HTTP response headers, if necessary
                // Map<String, List<String>> headers = connection.getHeaderFields();
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                String responseBodyData = baos.toString();
                JSONObject responseBodyJson = new JSONObject(responseBodyData);
                return responseBodyJson;
            } else {
                Log.e("HTTP", "The people http request did not work.");
                return null;
            }
        } catch (Exception e) {
            Log.e("IO", "The people io http did not work");
            e.printStackTrace();
            return null;
        }
    }


    private HttpURLConnection connectAndSend(URL finalUrl, String requestBodyString) throws IOException{
        HttpURLConnection connection = (HttpURLConnection)finalUrl.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.connect();

        // Write post data to request body
        OutputStream requestBody = connection.getOutputStream();
        requestBody.write(requestBodyString.getBytes());
        requestBody.close();
        return connection;
    }
}