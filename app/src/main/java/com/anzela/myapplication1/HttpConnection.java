package com.anzela.myapplication1;

import android.util.Log;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

public class HttpConnection {

    private String result = "";
    private ArrayList<Post> PostList = new ArrayList<>();
    private ArrayList<Post> PostListAround = new ArrayList<>();
    private ArrayList<Post> PostListSoon = new ArrayList<>();
    private BoardDetail BoardDetail= new BoardDetail();
    private ArrayList<Comments> commentsList= new ArrayList();

    // 모임 글 작성
    public void WriteServer(Post post) throws UnsupportedEncodingException, JSONException {
//        JSONObject jsonObj = new JSONObject();
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("title", URLEncoder.encode(post.getTitle(), "utf-8"));
//        jsonObj.put("title", post.getTitle());
//        jsonObj.put("content", post.getContent());
//        jsonObj.put("cruCnt", post.getCruCnt());
//        jsonObj.put("startDate", post.getStartDate());
//        jsonObj.put("startPoint", post.getStartPoint());
//        jsonObj.put("endPoint", post.getEndPoint());
        jsonObj.addProperty("content", URLEncoder.encode(post.getContent(), "utf-8"));
        jsonObj.addProperty("cruCnt", post.getCruCnt());
        jsonObj.addProperty("startDate", URLEncoder.encode(post.getStartDate(), "utf-8"));
        jsonObj.addProperty("startPoint", URLEncoder.encode(post.getStartPoint(), "utf-8"));
        jsonObj.addProperty("endPoint", URLEncoder.encode(post.getEndPoint(), "utf-8"));

        String jsonReq = jsonObj.toString();
        Log.e("req-test", jsonReq);
        ServerList("/api/v1/posts", jsonReq, "POST");
        Log.e("WriteServer_result", result);
    }

    // 모임 리스트
    public void getServer(int page) throws JSONException, UnsupportedEncodingException {
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
            String title = URLDecoder.decode(jsonObj_2.getString("title"), "utf-8");
//            String title = jsonObj_2.getString("title");
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
    public void getServerAround(int page, double lat, double lng) throws JSONException, UnsupportedEncodingException {
        ServerList("/api/v1/posts/around", "?page="+ page +"&lat=" + lat + "&lng=" + lng,"GET");

        JSONObject jsonObj_1 = new JSONObject(result);
        String data = jsonObj_1.getString("data");
        Log.e("getServerAround_result", result);

        JSONObject jsonObj_2 = new JSONObject(data);
        JSONArray jsonArray = jsonObj_2.getJSONArray("content");

        for(int i = 0; i < jsonArray.length(); i++) {
            jsonObj_2 = jsonArray.getJSONObject(i);

            int id = jsonObj_2.getInt("id");
            String title = URLDecoder.decode(jsonObj_2.getString("title"), "utf-8");
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
            Log.d("TEST>>"  , "near list check size : " + PostListAround.size());
        }
    }

    // 모임 일정이 가까운 리스트
    public void getServerSoon(int page, int soondt) throws JSONException, UnsupportedEncodingException {
        ServerList("/api/v1/posts/soon", "?page="+ page +"&soondt=" + soondt,"GET");

        JSONObject jsonObj_1 = new JSONObject(result);
        String data = jsonObj_1.getString("data");
        Log.e("getServerSoon_result", result);

        JSONObject jsonObj_2 = new JSONObject(data);
        JSONArray jsonArray = jsonObj_2.getJSONArray("content");

        Log.d("TEST>>"  , "soon json array : " + jsonArray.length());
        for(int i = 0; i < jsonArray.length(); i++) {
            jsonObj_2 = jsonArray.getJSONObject(i);

            int id = jsonObj_2.getInt("id");
            String title = URLDecoder.decode(jsonObj_2.getString("title"), "utf-8");
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
            Log.d("TEST>>"  , "soon list check size : " + PostListSoon.size());

        }
    }

    // 모임 상세
    public void getServerDetail(int id) throws JSONException, UnsupportedEncodingException {
        ServerList("/api/v1/posts/", "" + id, "GET");

        JSONObject jsonObj_result = new JSONObject(result);
        Log.e("getServerDetail_result", result);
        String data = jsonObj_result.getString("data");

        JSONObject jsonObj_data = new JSONObject(data);
        String user = jsonObj_data.getString("user");

        JSONObject jsonObj_user = new JSONObject(user);

        JSONArray jsonArray_comments = jsonObj_data.getJSONArray("comments");

        int id1 = jsonObj_data.getInt("id");
        String title = URLDecoder.decode(jsonObj_data.getString("title"), "utf-8");
//        String title = jsonObj_data.getString("title");
        String content = URLDecoder.decode(jsonObj_data.getString("content"), "utf-8");
        int cruCnt = jsonObj_data.getInt("cruCnt");
        String startDate = URLDecoder.decode(jsonObj_data.getString("startDate"), "utf-8");
        String startPoint = URLDecoder.decode(jsonObj_data.getString("startPoint"), "utf-8");
        String startLat = URLDecoder.decode(jsonObj_data.getString("startLat"), "utf-8");
        String startLng = URLDecoder.decode(jsonObj_data.getString("startLng"), "utf-8");
        String endPoint = URLDecoder.decode(jsonObj_data.getString("endPoint"), "utf-8");
        String endLat = URLDecoder.decode(jsonObj_data.getString("endLat"), "utf-8");
        String endLng = URLDecoder.decode(jsonObj_data.getString("endLng"), "utf-8");
        int cmtCnt = jsonObj_data.getInt("cmtCnt");
        String regDate = URLDecoder.decode(jsonObj_data.getString("regDate"), "utf-8");

        String uid = jsonObj_user.getString("uid");
        String profileUrl = jsonObj_user.getString("profileUrl");

        for (int i = 0; i < jsonArray_comments.length(); i++){
            JSONObject jsonObj_com = jsonArray_comments.getJSONObject(i);

            String com_id = jsonObj_com.getString("id");
            String com_content = jsonObj_com.getString("content");
            String com_depth = jsonObj_com.getString("depth");
            String com_regDate = jsonObj_com.getString("regDate");

            JSONObject jsonObj_subuser = jsonObj_com.getJSONObject("user");
            String com_uid = jsonObj_subuser.getString("uid");
            String com_profileUrl = jsonObj_subuser.getString("profileUrl");

            commentsList.add(new Comments(com_id, com_content, com_depth, com_regDate, (new User(com_uid, com_profileUrl))));
        }

        //Log.e("Test", content);
        BoardDetail =(new BoardDetail(id1, title, content, cruCnt, startDate, startPoint, startLat, startLng, endPoint, endLat, endLng, cmtCnt, regDate, (new User(uid, profileUrl)), commentsList));
        Log.e("Test", BoardDetail.getContent());
    }

    //받은 url, req, method 서버 접속
    public void ServerList(String uri, String req, String method){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW9ra2kiLCJpYXQiOjE2MjUwNDE2MjcsImV4cCI6MTY1NjU3NzYyN30.WkvbmVouJNTkdP9ckqYrAlufLbSL09354NKAT6F8ZIw";
        String header = "Bearer " + token;
        try {
            StringBuilder urlBuilder = new StringBuilder("http://3.35.241.60" + uri); //"http://15.165.35.47/api/v1/posts"
            if (method.equals("GET")){
                urlBuilder.append(req); // req
            }
            URL url = new URL(urlBuilder.toString());
//            Log.e("URL-TAG", "url=" + urlBuilder.toString() );
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod(method); // "GET" or "POST"
            con.setRequestProperty("Authorization", header);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");


            if(method.equals("POST")){
                con.setDoOutput(true); // POST 파라미터 전달을 위한 설정

                // post방식으로 req 전달할 때 필요
                DataOutputStream dataout = new DataOutputStream(con.getOutputStream());
                dataout.writeBytes(req);
                dataout.flush();
                dataout.close();

            }
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
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

    public ArrayList<Post> getPost() {
        return PostList;
    }

    public ArrayList<Post> getPostAround() {
        return PostListAround;
    }
    public ArrayList<Post> getPostSoon() {
        return PostListSoon;
    }
    public Object getBoardDetail() {
        return  BoardDetail;
    }

}
