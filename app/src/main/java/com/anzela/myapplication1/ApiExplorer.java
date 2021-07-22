package com.anzela.myapplication1;

import android.util.Log;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiExplorer {

    private static String mTemperature = "";
    private static String mWeather = "";

    public void lookupWeather() throws IOException, JSONException {

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat simpleTime = new SimpleDateFormat("kkmm");
        SimpleDateFormat timecheck = new SimpleDateFormat("kk");

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int Ch = Integer.parseInt(timecheck.format(cal.getTime()));

        int minus = (Ch - 2)%3;
        cal.add(Calendar.HOUR, -minus);

        String getDate = simpleDate.format(cal.getTime());
        String getTime = simpleTime.format(cal.getTime());

        Log.e("Check", "Date: " + getDate + ", Time: " + getTime);
        
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/  //getUltraSrtNcst getVilageFcst getUltraSrtFcst
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=6rhwGEsfLBipb8RoJ7psQT%2BRRpmvIhwcyJlUIZVIOIw3u0%2FSmzGa9K%2FaQxHKRKwEHB1ThylHyKS4eqOv%2Bv4JGQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(getDate, "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(getTime, "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/
        URL url = new URL(urlBuilder.toString());
        //Log.e("Check-url", "url=" + urlBuilder.toString() );
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String result = sb.toString();

        // response 키를 가지고 데이터를 파싱
        JSONObject jsonObj_1 = new JSONObject(result);
        String response = jsonObj_1.getString("response");

        // response 로 부터 body 찾기
        JSONObject jsonObj_2 = new JSONObject(response);
        String body = jsonObj_2.getString("body");

        // body 로 부터 items 찾기
        JSONObject jsonObj_3 = new JSONObject(body);
        String items = jsonObj_3.getString("items");
        //Log.i("ITEMS",items);

        // items로 부터 itemlist 를 받기
        JSONObject jsonObj_4 = new JSONObject(items);
        JSONArray jsonArray = jsonObj_4.getJSONArray("item");

        for(int i=0;i<jsonArray.length();i++) {
            jsonObj_4 = jsonArray.getJSONObject(i);

            String fcstValue = jsonObj_4.getString("fcstValue");
            String category = jsonObj_4.getString("category");

            String weather = null;
            if (category.equals("SKY")) {
                //weather = " ";
                if (fcstValue.equals("1")) {
                    weather = "맑음";
                } else if (fcstValue.equals("2")) {
                    weather = "비";
                } else if (fcstValue.equals("3")) {
                    weather = "구름";
                } else if (fcstValue.equals("4")) {
                    weather = "흐림";
                } else {
                    weather = "error";
                }
                mWeather = weather;
                break;
            }
        }

        for(int i=0;i<jsonArray.length();i++) {
            jsonObj_4 = jsonArray.getJSONObject(i);

            String fcstValue = jsonObj_4.getString("fcstValue");
            String category = jsonObj_4.getString("category");

            String temperature = null;
            if(category.equals("TMP")) {
                temperature = fcstValue;
            }
            mTemperature = temperature;
            break;
        }
    }
    public String getResult() {
        return mTemperature + "° " + mWeather;
    }
}

