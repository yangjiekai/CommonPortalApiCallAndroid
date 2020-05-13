package com.example.commonportalapicallandroid;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class ApiCall {

    private static List<String> cookies;
    private static HttpURLConnection conn;

    private static final String USER_AGENT = "Mozilla/5.0";


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String GetContent() throws IOException {


        //conn.setDoOutput(true);

        String page = "";
        try {

            CookieHandler.setDefault(new CookieManager());
            page = GetPageContent("http://jyang/MetaSwitchWrapperAPI/api/API?APIToCall=Meta_BusinessGroup_ChildrenList_Subscriber&UserIdentity=Granite-NYLA-CFS-1/Test%20Business%20Group");
            //  page = GetPageContent("https://commportal.granitevoip.com");

            String testsessionid = getSessionIDD("https://commportal.granitevoip.com/login");
        } catch (Exception e) {

            page = e.toString();
        }
        return page;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String GetCommonPortalContent() throws IOException {

        String page = "";
        try {

            CookieHandler.setDefault(new CookieManager());

            String sessionid = getSessionIDD("https://commportal.granitevoip.com/login");

           // page =  getData("https://commportal.granitevoip.com/session"+sessionid+"/line/data?version=9.4.20&callback=dataObjectManager.callback&data=Meta_Subscriber_UC9000_Contacts");
            page =  GetPageContent("https://commportal.granitevoip.com/session"+sessionid+"/line/data?version=9.4.20&callback=dataObjectManager.callback&data=Meta_Subscriber_UC9000_Contacts");


        } catch (Exception e) {

            page = e.toString();
        }
        return page;

    }

    public static void setCookies(List<String> cookies) {
        ApiCall.cookies = cookies;
    }

    public static String EstablishlonglivedCookie() {


        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);

        HttpCookie cookie = new HttpCookie("lang", "fr");
        cookie.setDomain("twitter.com");
        cookie.setPath("/");
        cookie.setVersion(0);
        //  cookieManager.getCookieStore().add(new URI("http://twitter.com/"), cookie);

        return "";
    }

    public static void getCookieUsingCookieHandler(String URL) {

        URL = "https://commportal.granitevoip.com/#line/main.html";
        try {
            // Instantiate CookieManager;
            // make sure to set CookiePolicy
            CookieManager manager = new CookieManager();
            manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(manager);

            // get content from URLConnection;
            // cookies are set by web site
            URL url = new URL(URL);
            URLConnection connection = url.openConnection();
            connection.getContent();

            // get cookies from underlying
            // CookieStore
            CookieStore cookieJar = manager.getCookieStore();
            List<HttpCookie> cookies =
                    cookieJar.getCookies();
            for (HttpCookie cookie : cookies) {
                System.out.println("CookieHandler retrieved cookie: " + cookie);
            }
        } catch (Exception e) {
            System.out.println("Unable to get cookie using CookieHandler");
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getSessionIDD(String url) {


//        SIP Username = 6174059719
//        SIP Password = [J7}27o}
//
//https://commportal.granitevoip.com/#login.html
        HttpsURLConnection connn = null;
        URL obj = null;
        try {
            obj = new URL(url);

            try {
                connn = (HttpsURLConnection) obj.openConnection();
                connn.setInstanceFollowRedirects(true);
                HttpURLConnection.setFollowRedirects(false);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // default is GET
            connn.setRequestMethod("POST");

            connn.setUseCaches(false);

            // act like a browser
            connn.setRequestProperty("User-Agent", USER_AGENT);
            connn.setRequestProperty("Accept",
                    "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            connn.setRequestProperty("Accept-Language", "en-US,en;q=0.9,zh-TW;q=0.8,zh;q=0.7");
            connn.setRequestProperty("sec-fetch-dest", "iframe");
            connn.setRequestProperty("sec-fetch-mode", "navigate");
            connn.setRequestProperty("sec-fetch-site", "same-origin");
            connn.setRequestProperty("sec-fetch-user", "?1");
            connn.setRequestProperty("upgrade-insecure-requests", "1");

            connn.setRequestProperty("User-Agent", USER_AGENT);
            if (cookies != null) {
                for (String cookie : cookies) {
                    connn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
                }
            }
            connn.setRequestProperty("Connection", "keep-alive");
            connn.setRequestProperty("Origin", "https://commportal.granitevoip.com");
            connn.setRequestProperty("Referrer", "https://commportal.granitevoip.com/login.html?error=sessionExpired&redirectTo=%2Fline%2Fcallmanager.html");
            connn.setRequestProperty("referrerPolicy", "no-referrer-when-downgrade");
            connn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


//            conn.setRequestProperty("body",
//                    "version=9.4.20&errorRedirectTo=%2Flogin.html%3FredirectTo%3D%25252Fline%25252Fcallmanager.html&redirectTo=%2Fline%2Fcallmanager.html%3FjustLoggedIn%3D1587991307&ApplicationID=MS_WebClient&ContextInfo=version%3D9.4.20&DirectoryNumber=6174059719&Password=%5BJ7%7D27o%7D"
//            );
            connn.setDoOutput(true);
            connn.setChunkedStreamingMode(0);


            // String postParams = "DirectoryNumber="+ URLEncoder.encode("6174059719", "UTF-8")+"&Password="+URLEncoder.encode("[J7}27o}", "UTF-8");
            String postParams = "version=9.4.20&errorRedirectTo=%2Flogin.html%3FredirectTo%3D%25252Fline%25252Fcallmanager.html&redirectTo=%2Fline%2Fcallmanager.html%3FjustLoggedIn%3D1587991307&ApplicationID=MS_WebClient&ContextInfo=version%3D9.4.20&DirectoryNumber=6174059719&Password=%5BJ7%7D27o%7D";


            //        int responseCode = conn.getResponseCode();
            //        System.out.println("\nSending 'GET' request to URL : " + url);
            //        System.out.println("Response Code : " + responseCode);

//                BufferedReader in =
//                        new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();


            DataOutputStream wr = new DataOutputStream(connn.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();

            int responseCode = connn.getResponseCode();


            String res = "";


            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                // res=response.toString();
            }


            res = connn.toString().substring(54);
            URL aURL = new URL(res);

            String path=aURL.getPath();

            String[] arrOfStr = path.split("/", 3);

            String sessionID=arrOfStr[1].substring(7);

            getCookieUsingCookieHandler(res);
            // Get the response cookies
            setCookies(connn.getHeaderFields().get("Set-Cookie"));
            return sessionID;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getData(String url) {

        String res = "";
//        SIP Username = 6174059719
//        SIP Password = [J7}27o}
//
//https://commportal.granitevoip.com/#login.html
        HttpsURLConnection connn = null;
        URL obj = null;
        try {
            obj = new URL(url);

            try {
                connn = (HttpsURLConnection) obj.openConnection();
//                connn.setInstanceFollowRedirects(true);
//                HttpURLConnection.setFollowRedirects(false);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // default is GET
            connn.setRequestMethod("GET");

            connn.setUseCaches(false);

            // act like a browser
            connn.setRequestProperty("User-Agent", USER_AGENT);
            connn.setRequestProperty("Accept",
                    "*/*");
            connn.setRequestProperty("Accept-Language", "en-US,en;q=0.9,zh-TW;q=0.8,zh;q=0.7");
            connn.setRequestProperty("sec-fetch-dest", "script");
            connn.setRequestProperty("sec-fetch-mode", "no-cors");
            connn.setRequestProperty("sec-fetch-site", "same-origin");



            connn.setRequestProperty("User-Agent", USER_AGENT);
            if (cookies != null) {
                for (String cookie : cookies) {
                    connn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
                }
            }
            connn.setRequestProperty("Connection", "keep-alive");
//            connn.setRequestProperty("Origin", "https://commportal.granitevoip.com");
//            connn.setRequestProperty("Referrer", "https://commportal.granitevoip.com/login.html?error=sessionExpired&redirectTo=%2Fline%2Fcallmanager.html");
            connn.setRequestProperty("referrerPolicy", "no-referrer-when-downgrade");
            connn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


//            conn.setRequestProperty("body",
//                    "version=9.4.20&errorRedirectTo=%2Flogin.html%3FredirectTo%3D%25252Fline%25252Fcallmanager.html&redirectTo=%2Fline%2Fcallmanager.html%3FjustLoggedIn%3D1587991307&ApplicationID=MS_WebClient&ContextInfo=version%3D9.4.20&DirectoryNumber=6174059719&Password=%5BJ7%7D27o%7D"
//            );
            connn.setDoOutput(true);



            // String postParams = "DirectoryNumber="+ URLEncoder.encode("6174059719", "UTF-8")+"&Password="+URLEncoder.encode("[J7}27o}", "UTF-8");
        //    String postParams = "version=9.4.20&errorRedirectTo=%2Flogin.html%3FredirectTo%3D%25252Fline%25252Fcallmanager.html&redirectTo=%2Fline%2Fcallmanager.html%3FjustLoggedIn%3D1587991307&ApplicationID=MS_WebClient&ContextInfo=version%3D9.4.20&DirectoryNumber=6174059719&Password=%5BJ7%7D27o%7D";


            //        int responseCode = conn.getResponseCode();
            //        System.out.println("\nSending 'GET' request to URL : " + url);
            //        System.out.println("Response Code : " + responseCode);

//                BufferedReader in =
//                        new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();


//            DataOutputStream wr = new DataOutputStream(connn.getOutputStream());
//            wr.writeBytes(postParams);
//            wr.flush();
//            wr.close();

            int responseCode = connn.getResponseCode();





            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                res=response.toString();
            }




            getCookieUsingCookieHandler(res);
            // Get the response cookies
            setCookies(connn.getHeaderFields().get("Set-Cookie"));
            //return res;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }


    private static String GetPageContent(String url) throws Exception {

        URL obj = new URL(url);
        if (url.startsWith("https")) {


            conn = (HttpsURLConnection) obj.openConnection();

        } else {

            conn = (HttpURLConnection) obj.openConnection();
        }

        // default is GET
        conn.setRequestMethod("GET");

        conn.setUseCaches(false);

        // act like a browser
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.9,zh-TW;q=0.8,zh;q=0.7");
        conn.setRequestProperty("sec-fetch-dest", "document");
        conn.setRequestProperty("sec-fetch-mode", "navigate");
        conn.setRequestProperty("sec-fetch-site", "none");
        conn.setRequestProperty("sec-fetch-user", "?1");
        conn.setRequestProperty("upgrade-insecure-requests", "1");

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
        String res = response.toString();
        return res;

    }

}
