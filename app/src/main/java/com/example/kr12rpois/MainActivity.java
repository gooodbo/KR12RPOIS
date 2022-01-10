package com.example.kr12rpois;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button1, button2, button3;
    private EditText firstNameET, nameET, secondNameET;
    private DBHelper dbHelper;

    private List<DataBD> dataBDList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(this);

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(this);

        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);

        firstNameET = findViewById(R.id.firstNameTV);
        nameET = findViewById(R.id.nameTV);
        secondNameET = findViewById(R.id.secondNameTV);

    }


    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @Override
    public void onClick(View v) {

        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String firstName = String.valueOf(firstNameET.getText());
        String name = String.valueOf(nameET.getText());
        String secondName = String.valueOf(secondNameET.getText());


        switch (v.getId()) {

            case R.id.button1:
                Intent intent = new Intent(this, Button1Activity.class);
                startActivity(intent);
                break;

            case R.id.button2:

                Date currentDate = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                String date = dateFormat.format(currentDate);
                DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String time = timeFormat.format(currentDate);

                cv.put(DBHelper.KEY_NAME, name);
                cv.put(DBHelper.KEY_FIRST_NAME, firstName);
                cv.put(DBHelper.KEY_SECOND_NAME, secondName);
                cv.put(DBHelper.KEY_TIME, date + " " + time);
                db.insert(DBHelper.TABLE_NAME, null, cv);

                break;

            case R.id.button3:
                if (dataBDList.size() != 0) {
                    break;
                }
                getDataFromBD();
                int id = dataBDList.get(dataBDList.size() - 1).getId();
                cv.put(DBHelper.KEY_FIRST_NAME, "Иванов");
                cv.put(DBHelper.KEY_NAME, "Иван");
                cv.put(DBHelper.KEY_SECOND_NAME, "Иванович");

                db.update(DBHelper.TABLE_NAME, cv, "_id = ?", new String[]{String.valueOf(id)});

                break;

        }
        db.close();
        dbHelper.close();
    }

    public void getDataFromBD() {
        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dataBDList.clear();
        Cursor c = db.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);

        if (c.moveToFirst()) {

            int idColIndex = c.getColumnIndex(DBHelper.KEY_ID);
            int nameColIndex = c.getColumnIndex(DBHelper.KEY_NAME);
            int firstNameColIndex = c.getColumnIndex(DBHelper.KEY_FIRST_NAME);
            int secondNameColIndex = c.getColumnIndex(DBHelper.KEY_SECOND_NAME);
            int timeColIndex = c.getColumnIndex(DBHelper.KEY_TIME);

            do {
                DataBD dataBD = new DataBD();
                dataBD.setId(Integer.parseInt(c.getString(idColIndex)));
                dataBD.setName(c.getString(nameColIndex));
                dataBD.setFirstName(c.getString(firstNameColIndex));
                dataBD.setSecondName(c.getString(secondNameColIndex));
                dataBD.setTime(c.getString(timeColIndex));

                dataBDList.add(dataBD);
            } while (c.moveToNext());
        }
        db.close();
        dbHelper.close();
    }
}