package com.anzela.myapplication1;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

public class HttpConnection {

    private String result = "";

    private BoardDetail BoardDetail= new BoardDetail();
    private ArrayList<Comments> commentsList= new ArrayList();

    // 모임 글 작성
    public void WriteServer(Post post) throws UnsupportedEncodingException{

        JsonObject jsonObj = new JsonObject();
//        jsonObj.addProperty("title", URLEncoder.encode(post.getTitle(), "utf-8"));
//        jsonObj.addProperty("content", URLEncoder.encode(post.getContent(), "utf-8"));
//        jsonObj.addProperty("cruCnt", post.getCruCnt());
//        jsonObj.addProperty("startDate", URLEncoder.encode(post.getStartDate(), "utf-8"));
//        jsonObj.addProperty("startPoint", URLEncoder.encode(post.getStartPoint(), "utf-8"));
//        jsonObj.addProperty("endPoint", URLEncoder.encode(post.getEndPoint(), "utf-8"));

        jsonObj.addProperty("title", post.getTitle());
        jsonObj.addProperty("content", post.getContent());
        jsonObj.addProperty("cruCnt", post.getCruCnt());
        jsonObj.addProperty("startDate", post.getStartDate());
        jsonObj.addProperty("startPoint", post.getStartPoint());
        jsonObj.addProperty("endPoint", post.getEndPoint());

        String jsonReq = jsonObj.toString();
        Log.e("req-test", jsonReq);
        ServerList("/api/v1/posts", jsonReq, "POST");
        Log.e("WriteServer_result", result);
    }

    // 모임 리스트
    public ArrayList<Post> getServer(int page) throws JSONException, UnsupportedEncodingException {
        ServerList("/api/v1/posts", "?page=" + page,"GET");
        ArrayList<Post> PostList = new ArrayList<>();

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
        return PostList;
    }

    // 내근처 리스트
    public ArrayList<Post> getServerAround(int page, double lat, double lng) throws JSONException, UnsupportedEncodingException {
        ServerList("/api/v1/posts/around", "?page="+ page +"&lat=" + lat + "&lng=" + lng,"GET");
        ArrayList<Post> PostListAround = new ArrayList<>();

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
            Log.d("TEST>>"  , "near list check size : " + PostListAround.size());
        }
        return PostListAround;
    }

    // 모임 일정이 가까운 리스트
    public ArrayList<Post> getServerSoon(int page, int soondt) throws JSONException, UnsupportedEncodingException {
        ServerList("/api/v1/posts/soon", "?page="+ page +"&soondt=" + soondt,"GET");
        ArrayList<Post> PostListSoon = new ArrayList<>();

        JSONObject jsonObj_1 = new JSONObject(result);
        String data = jsonObj_1.getString("data");
        Log.e("getServerSoon_result", result);

        JSONObject jsonObj_2 = new JSONObject(data);
        JSONArray jsonArray = jsonObj_2.getJSONArray("content");

        Log.d("TEST>>"  , "soon json array : " + jsonArray.length());
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
            Log.d("TEST>>"  , "soon list check size : " + PostListSoon.size());
        }
        return PostListSoon;
    }

    // 모임 상세
    public void getServerDetail(int id) throws JSONException, UnsupportedEncodingException {
        ServerList("/api/v1/posts/" + id, "", "GET");

        JSONObject jsonObj_result = new JSONObject(result);
        Log.e("getServerDetail_result", result);
        String data = jsonObj_result.getString("data");

        JSONObject jsonObj_data = new JSONObject(data);
        String user = jsonObj_data.getString("user");

        JSONObject jsonObj_user = new JSONObject(user);

        JSONArray jsonArray_comments = jsonObj_data.getJSONArray("comments");

        int id1 = jsonObj_data.getInt("id");
        String title = jsonObj_data.getString("title");
//        String title = jsonObj_data.getString("title");
        String content = jsonObj_data.getString("content");
        int cruCnt = jsonObj_data.getInt("cruCnt");
        String startDate = jsonObj_data.getString("startDate");
        String startPoint = jsonObj_data.getString("startPoint");
        String startLat = jsonObj_data.getString("startLat");
        String startLng = jsonObj_data.getString("startLng");
        String endPoint = jsonObj_data.getString("endPoint");
        String endLat = jsonObj_data.getString("endLat");
        String endLng = jsonObj_data.getString("endLng");
        int cmtCnt = jsonObj_data.getInt("cmtCnt");
        String regDate = jsonObj_data.getString("regDate");

        String uid = jsonObj_user.getString("uid");
        String profileUrl = jsonObj_user.getString("profileUrl");

        for (int i = 0; i < jsonArray_comments.length(); i++){
            JSONObject jsonObj_com = jsonArray_comments.getJSONObject(i);

            int com_id = jsonObj_com.getInt("id");
            String com_content = jsonObj_com.getString("content");
            int com_depth = jsonObj_com.getInt("depth");
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

    // 모임 수정
    public void getServerModify(int id, Post post) throws UnsupportedEncodingException {
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("title", post.getTitle());
        jsonObj.addProperty("content", post.getContent());
        jsonObj.addProperty("cruCnt", post.getCruCnt());
        jsonObj.addProperty("startDate", post.getStartDate());
        jsonObj.addProperty("startPoint", post.getStartPoint());
        jsonObj.addProperty("endPoint", post.getEndPoint());

        String jsonReq = jsonObj.toString();
        Log.e("req-test", jsonReq);
        ServerList("/api/v1/posts/" + id, jsonReq, "POST");
        Log.e("WriteServer_result", result);
    }

    // 모임 삭제
    public void getServerDelete(int id){
        ServerList("/api/v1/posts/" + id, "", "DELETE");
    }

    // 댓글 작성
    public void getComment(int id, String comment){
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("content", comment);

        String jsonReq = jsonObj.toString();
        ServerList("/api/v1/posts/" + id + "/comment", jsonReq, "POST");
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
            Log.e("HTTP", "url=" + urlBuilder.toString() );
            Log.e("HTTP", "request = " + req);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod(method); // "GET" or "POST"
            con.setRequestProperty("Authorization", header);
            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            con.setRequestProperty("Accept", "application/json");


            if(method.equals("POST")){
                con.setDoOutput(true); // POST 파라미터 전달을 위한 설정

                // post방식으로 req 전달할 때 필요
                OutputStream out = con.getOutputStream();
                out.write(req.getBytes());
                out.flush();
                out.close();
//                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
//                DataOutputStream dataout = new DataOutputStream(con.getOutputStream());
//                out.write(req);
//                out.flush();
//                out.close();

            }
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                Log.e("TAG200", urlBuilder.toString());
                Log.e("TAG200", "Clear");
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
    public Object getBoardDetail() {
        return  BoardDetail;
    }

}
