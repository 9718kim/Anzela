package com.anzela.myapplication1;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpConnection {

    private String result = "";
    private ArrayList<Post> PostList = new ArrayList<>();
    private ArrayList<Post> PostListAround = new ArrayList<>();
    private ArrayList<Post> PostListSoon = new ArrayList<>();
    private ArrayList<Post> PostListDetail = new ArrayList<>();


    // 모임 리스트
    public void getServer(int page) throws JSONException {
        ServerList("/api/v1/posts", "?page=" + page,"GET");

        // 파싱
        JSONObject jsonObj_1 = new JSONObject(result);
        String data = jsonObj_1.getString("data");
        Log.e("getServer_result", result);

        JSONObject jsonObj_2 = new JSONObject(data);
        JSONArray jsonArray = jsonObj_2.getJSONArray("content");

        for(int i = 0; i < jsonArray.length(); i++) {
            jsonObj_2 = jsonArray.getJSONObject(i);

            int id = jsonObj_2.getInt("id");
            String title = jsonObj_2.getString("title");
            String content = jsonObj_2.getString("content");
            int cruCnt = jsonObj_2.getInt("cruCnt");
            String startDate = jsonObj_2.getString("startDate");
            String startPoint = jsonObj_2.getString("startPoint");
            String startLat = jsonObj_2.optString("startLat", "text on no value");
            String startLng = jsonObj_2.optString("startLng", "text on no value");
            String endPoint = jsonObj_2.optString("endPoint", "text on no value");
            String endLat = jsonObj_2.optString("endLat", "text on no value");
            String endLng = jsonObj_2.optString("endLng", "text on no value");
            int cmtCnt = jsonObj_2.getInt("cmtCnt");
            String regDate = jsonObj_2.getString("regDate");

            PostList.add(new Post(id, title, content, cruCnt, startDate, startPoint, startLat, startLng, endPoint, endLat, endLng, cmtCnt, regDate));
        }
    }

    // 내근처 리스트
    public void getServerAround(int page, double lat, double lng) throws JSONException {
        ServerList("/api/v1/posts/around", "?page="+ page +"&lat=" + lat + "&lng=" + lng,"GET");

        JSONObject jsonObj_1 = new JSONObject(result);
        String data = jsonObj_1.getString("data");
        Log.e("getServerAround_result", result);

        JSONObject jsonObj_2 = new JSONObject(data);
        JSONArray jsonArray = jsonObj_2.getJSONArray("content");

        for(int i = 0; i < jsonArray.length(); i++) {
            jsonObj_2 = jsonArray.getJSONObject(i);

            int id = jsonObj_2.getInt("id");
            String title = jsonObj_2.getString("title");
            String content = jsonObj_2.getString("content");
            int cruCnt = jsonObj_2.getInt("cruCnt");
            String startDate = jsonObj_2.getString("startDate");
            String startPoint = jsonObj_2.getString("startPoint");
            String startLat = jsonObj_2.optString("startLat", "text on no value");
            String startLng = jsonObj_2.optString("startLng", "text on no value");
            String endPoint = jsonObj_2.optString("endPoint", "text on no value");
            String endLat = jsonObj_2.optString("endLat", "text on no value");
            String endLng = jsonObj_2.optString("endLng", "text on no value");
            int cmtCnt = jsonObj_2.getInt("cmtCnt");
            String regDate = jsonObj_2.getString("regDate");

            PostListAround.add(new Post(id, title, content, cruCnt, startDate, startPoint, startLat, startLng, endPoint, endLat, endLng, cmtCnt, regDate));
        }
    }

    // 모임 일정이 가까운 리스트
    public void getServerSoon(int page, int soondt) throws JSONException {
        ServerList("/api/v1/posts/soon", "?page="+ page +"&soondt=" + soondt,"GET");

        JSONObject jsonObj_1 = new JSONObject(result);
        String data = jsonObj_1.getString("data");
        Log.e("getServerSoon_result", result);

        JSONObject jsonObj_2 = new JSONObject(data);
        JSONArray jsonArray = jsonObj_2.getJSONArray("content");

        for(int i = 0; i < jsonArray.length(); i++) {
            jsonObj_2 = jsonArray.getJSONObject(i);

            int id = jsonObj_2.getInt("id");
            String title = jsonObj_2.getString("title");
            String content = jsonObj_2.getString("content");
            int cruCnt = jsonObj_2.getInt("cruCnt");
            String startDate = jsonObj_2.getString("startDate");
            String startPoint = jsonObj_2.getString("startPoint");
            String startLat = jsonObj_2.optString("startLat", "text on no value");
            String startLng = jsonObj_2.optString("startLng", "text on no value");
            String endPoint = jsonObj_2.optString("endPoint", "text on no value");
            String endLat = jsonObj_2.optString("endLat", "text on no value");
            String endLng = jsonObj_2.optString("endLng", "text on no value");
            int cmtCnt = jsonObj_2.getInt("cmtCnt");
            String regDate = jsonObj_2.getString("regDate");

            PostListSoon.add(new Post(id, title, content, cruCnt, startDate, startPoint, startLat, startLng, endPoint, endLat, endLng, cmtCnt, regDate));
        }
    }

    // 모임 상세
    public void getServerDetail(int id) throws JSONException {
        ServerList("/api/v1/posts/", "" + id, "GET");

        JSONObject jsonObj_1 = new JSONObject(result);
        Log.e("getServerDetail_result", result);
        JSONObject jsonObj_2 = jsonObj_1.getJSONObject("data");

        int id1 = jsonObj_2.getInt("id");
        String title = jsonObj_2.getString("title");
        String content = jsonObj_2.getString("content");
        int cruCnt = jsonObj_2.getInt("cruCnt");
        String startDate = jsonObj_2.getString("startDate");
        String startPoint = jsonObj_2.getString("startPoint");
        String startLat = jsonObj_2.optString("startLat", "text on no value");
        String startLng = jsonObj_2.optString("startLng", "text on no value");
        String endPoint = jsonObj_2.optString("endPoint", "text on no value");
        String endLat = jsonObj_2.optString("endLat", "text on no value");
        String endLng = jsonObj_2.optString("endLng", "text on no value");
        int cmtCnt = jsonObj_2.getInt("cmtCnt");
        String regDate = jsonObj_2.getString("regDate");
        String comments = jsonObj_2.optString("comments", "text on no value");

        //PostListDetail.add(new PostComments(id1, title, content, cruCnt, startDate, startPoint, startLat, startLng, endPoint, endLat, endLng, cmtCnt, regDate, comments));

        Log.e("getServerDetail", PostListDetail.get(0).title);
    }

    //받은 url과 method 서버 접속
    public void ServerList(String uri, String req, String method){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW9ra2kiLCJpYXQiOjE2MjUwNDE2MjcsImV4cCI6MTY1NjU3NzYyN30.WkvbmVouJNTkdP9ckqYrAlufLbSL09354NKAT6F8ZIw";
        String header = "Bearer " + token;
        try {
            StringBuilder urlBuilder = new StringBuilder("http://15.165.35.47" + uri); //"http://15.165.35.47/api/v1/posts"
            urlBuilder.append(req); /*페이지번호*/
            URL url = new URL(urlBuilder.toString());
            //Log.e("URL-TAG", "url=" + urlBuilder.toString() );
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod(method); // "GET" or "POST"
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                Log.e("TAG200", urlBuilder.toString());
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                Log.e("Error", urlBuilder.toString() + " ResponseCode : " + responseCode);
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            result = response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getPost() {
        return PostList;
    }

    public Object getPostAround() {
        return PostListAround;
    }
    public Object getPostSoon() {
        return PostListSoon;
    }

}
