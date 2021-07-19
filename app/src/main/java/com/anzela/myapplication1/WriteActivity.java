package com.anzela.myapplication1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;

public class WriteActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    boolean complete_check = false;
    int maxnum = 0;
    String checkDate = "";
    TextView PageText;
    TextView dueDate;
    TextView maxPerson;
    TextView completeButton;
    TextView endDetail;
    ImageView backButton;
    CheckBox endPCheck;
    EditText titleText;
    EditText startPText;
    EditText endPText;
    EditText detailText;
    View titleLine;
    View startPLine;
    View endPLine;
    View detailLine;
    Dialog editDialog;
    Dialog errorDialog;
    Dialog teamDialog;
    InputMethodManager inputMethodManager;
    BoardDetail boardDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write);

        backButton = findViewById(R.id.backpressed);
        completeButton = findViewById(R.id.complete);
        dueDate = findViewById(R.id.duedate);
        maxPerson = findViewById(R.id.detailmaxperson);
        endPCheck = findViewById(R.id.endPcheck);
        endDetail = findViewById(R.id.enddetail);
        titleText = findViewById(R.id.titleBox);
        startPText = findViewById(R.id.startP);
        detailText = findViewById(R.id.detailtext);
        endPText = findViewById(R.id.endP);
        titleLine = findViewById(R.id.titleline);
        startPLine = findViewById(R.id.startPline);
        endPLine = findViewById(R.id.endPline);
        detailLine = findViewById(R.id.detailline);
        PageText = findViewById(R.id.pageText);

        editDialog = new Dialog(WriteActivity.this);
        editDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editDialog.setContentView(R.layout.editdialog);

        errorDialog = new Dialog(WriteActivity.this);
        errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        errorDialog.setContentView(R.layout.errordialog);

        teamDialog = new Dialog(WriteActivity.this);
        teamDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        teamDialog.setContentView(R.layout.teamdialog);

        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        int id = getIntent().getIntExtra("id", 0);
        Log.e("Write-test", String.valueOf(id));
        if (!(id == 0)){
//            int idnum = getIntent().getIntExtra("idnum", 0);
//            String data = getIntent().getStringExtra("data");
//            setting((BoardDetail) data);
            Log.e("Write-test", String.valueOf(id));
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpConnection http = new HttpConnection();
                        http.getServerDetail(id);
                        Log.e("Detail-text", "test1");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setting((BoardDetail) http.getBoardDetail());
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showeditDialog();
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (complete_check){
                    Toast.makeText(WriteActivity.this, "SAVE" ,Toast.LENGTH_LONG).show();
                    // 글 작성
                    Log.e("writetest", titleText.getText().toString());
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                HttpConnection http = new HttpConnection();
                                Post post = new Post();
                                post.setTitle(titleText.getText().toString());
                                post.setContent(detailText.getText().toString());
                                post.setCruCnt(maxnum);
                                post.setStartDate(checkDate);
                                post.setStartPoint(startPText.getText().toString());

                                String endPoint = endPText.getText().toString();
                                if (!endPoint.isEmpty()) {
                                    post.setEndPoint(endPoint);
                                }
                                http.WriteServer(post);
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    });
                    onBackPressed();
                }else {
                    showerrorDialog();
                }
            }
        });

        titleText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bFocus) {
                titleLine.setBackgroundColor(ContextCompat.getColor(WriteActivity.this, R.color.aqua_marine));
                if( !bFocus){
                    titleLine.setBackgroundColor(Color.parseColor("#848484"));
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        startPText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bFocus) {
                startPLine.setBackgroundColor(ContextCompat.getColor(WriteActivity.this, R.color.aqua_marine));
                if( !bFocus){
                    startPLine.setBackgroundColor(Color.parseColor("#848484"));
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        endPText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bFocus) {
                endPLine.setBackgroundColor(ContextCompat.getColor(WriteActivity.this, R.color.aqua_marine));
                if( !bFocus){
                    endPLine.setBackgroundColor(Color.parseColor("#848484"));
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        endPCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(endPCheck.isChecked()){
                    endDetail.setTextColor(ContextCompat.getColor(WriteActivity.this, R.color.white));
                    endPText.setText("");
                }else {
                    endDetail.setTextColor(Color.parseColor("#848484"));
                }
                clear();
                check();
            }
        });


        detailText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bFocus) {
                detailLine.setBackgroundColor(ContextCompat.getColor(WriteActivity.this, R.color.aqua_marine));
                if( !bFocus){
                    detailLine.setBackgroundColor(Color.parseColor("#848484"));
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        //SpannableString content = new SpannableString("Content");
        SpannableString datecontent = new SpannableString("2021년 06월 01일");
        datecontent.setSpan(new UnderlineSpan(), 0, datecontent.length(), 0);
        dueDate.setText(datecontent);

        //SpannableString personcontent = new SpannableString("personcontent");
        SpannableString personcontent = new SpannableString("총 0명");
        personcontent.setSpan(new UnderlineSpan(), 0, personcontent.length(), 0);
        maxPerson.setText(personcontent);

        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                clear();
            }
        });



        maxPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showteamDialog();
                maxPerson.setText(personcontent);
                clear();
            }
        });

        titleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { check(); }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        });
        startPText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { check(); }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        });
        endPText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { check(); }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        });
        detailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { check(); }
            @Override
            public void afterTextChanged(Editable s) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        });
    }

    public void check(){
        if((!(titleText.getText().toString().equals("") || titleText.getText() == null))
        && (!(startPText.getText().toString().equals("") || startPText.getText() == null))
        && ((!(endPText.getText().toString().equals("") || endPText.getText() == null)) || endPCheck.isChecked())
        && (!(detailText.getText().toString().equals("") || detailText.getText() == null))){
            completeButton.setBackgroundDrawable(ContextCompat.getDrawable(WriteActivity.this, R.drawable.radius12_aqua));
            completeButton.setTextColor(ContextCompat.getColor(WriteActivity.this, R.color.aqua_marine));
            complete_check = true;
        }else{
            // null
            completeButton.setBackgroundDrawable(ContextCompat.getDrawable(WriteActivity.this, R.drawable.radius12_2a594e));
            completeButton.setTextColor(Color.parseColor("#2a594e"));
            complete_check = false;
        }
    }

    public void clear(){
        titleText.clearFocus();
        startPText.clearFocus();
        endPText.clearFocus();
        detailText.clearFocus();
    }

    public void showeditDialog(){
        editDialog.show();

        editDialog.findViewById(R.id.noButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDialog.dismiss();
            }
        });
        editDialog.findViewById(R.id.yesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public void showerrorDialog(){
        errorDialog.show();

        errorDialog.findViewById(R.id.yesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorDialog.dismiss();
            }
        });
    }
    public void showteamDialog(){
        teamDialog.show();

        EditText maxTeam = teamDialog.findViewById(R.id.maxteam);

        teamDialog.findViewById(R.id.noButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamDialog.dismiss();
            }
        });
        teamDialog.findViewById(R.id.yesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(maxTeam.getText().toString().equals("") || maxTeam.getText() == null)){
                    maxnum = Integer.parseInt(maxTeam.getText().toString());
                    SpannableString personcontent = new SpannableString("총 " + maxTeam.getText().toString()+"명");
                    personcontent.setSpan(new UnderlineSpan(), 0, personcontent.length(), 0);
                    maxPerson.setText(personcontent);

                    teamDialog.dismiss();
                }else {
                    Toast.makeText(WriteActivity.this, "인원 수를 입력하세요." ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONDAY, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SpannableString datecontent = new SpannableString(DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime()));
        datecontent.setSpan(new UnderlineSpan(), 0, datecontent.length(), 0);
        dueDate = findViewById(R.id.duedate);
        dueDate.setText(datecontent);

        String Smonth = "";
        String Sday = "";
        if (month + 1 < 10){
            Smonth = "0" + (month + 1);
        }else{
            Smonth = String.valueOf(month + 1);
        }
        if (dayOfMonth < 10){
            Sday = "0" + dayOfMonth;
        }else{
            Sday = String.valueOf(dayOfMonth);
        }
        checkDate = year + Smonth + Sday + "000000";
//        checkDate = String.format("%d%d%d000000", year,month+1,dayOfMonth);
        Log.e("date-test", checkDate);
    }
    public void setting(BoardDetail data){
//        PageText = findViewById(R.id.pageText);
        Log.e("setting", "test");
        boardDetail = data;
        PageText.setText("글 수정");
    }
}