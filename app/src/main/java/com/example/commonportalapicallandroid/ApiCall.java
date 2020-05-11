package com.example.commonportalapicallandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class ApiCall {

    private static List<String> cookies;
    //private  static HttpsURLConnection conn;
    private static final String USER_AGENT = "Mozilla/5.0";
    private static HttpURLConnection conn;

  public String GetContent ( ) throws IOException {


      //conn.setDoOutput(true);

      String page="";
      try {
           page = GetPageContent("http://jyang/MetaSwitchWrapperAPI/api/API?APIToCall=Meta_BusinessGroup_ChildrenList_Subscriber&UserIdentity=Granite-NYLA-CFS-1/Test%20Business%20Group");
      } catch (Exception e) {

          page =  e.toString();
      }
      return page;
  }

    public  static void setCookies(List<String> cookies) {
        ApiCall.cookies = cookies;
    }


    public static String getSessionID (){


      return "";
    }

    private static String GetPageContent(String url) throws Exception {

        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();

        // default is GET
        conn.setRequestMethod("GET");

        conn.setUseCaches(false);

        // act like a browser
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.9,zh-TW;q=0.8,zh;q=0.7");
        conn.setRequestProperty("sec-fetch-dest", "document");
        conn.setRequestProperty("sec-fetch-mode",  "navigate");
        conn.setRequestProperty("sec-fetch-site",  "none");
        conn.setRequestProperty("sec-fetch-user",  "?1");
        conn.setRequestProperty("upgrade-insecure-requests",  "1");

        conn.setRequestProperty("User-Agent", USER_AGENT);
        if (cookies != null) {
            for (String cookie : cookies) {
                conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
            }
        }
//        int responseCode = conn.getResponseCode();
//        System.out.println("\nSending 'GET' request to URL : " + url);
//        System.out.println("Response Code : " + responseCode);

        BufferedReader in =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Get the response cookies
        setCookies(conn.getHeaderFields().get("Set-Cookie"));
       String res= response.toString();
        return res;

    }

}
