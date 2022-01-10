package com.example.kr12rpois;

import static com.example.kr12rpois.DBHelper.TABLE_NAME;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Button1Activity extends AppCompatActivity {

    RecyclerView recyclerView;

    private List<DataBD> dataBDList = new ArrayList<>();
    private DBHelper dbHelper;
    private DataBaseAdapter dataBaseAdapter;
    private Button buttonClear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button1_activity);
        buttonClear = findViewById(R.id.buttonClear);
        initRecyclerView();
        getDataFromBD();
        loadData();
        dbHelper = new DBHelper(this);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete(TABLE_NAME, null, null);
                getDataFromBD();
                loadData();
            }
        });

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataBaseAdapter = new DataBaseAdapter(this, this);
        recyclerView.setAdapter(dataBaseAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadData() {
        Collection<DataBD> dataBDS = dataBDList;
        dataBaseAdapter.clearItems();
        dataBaseAdapter.notifyDataSetChanged();
        dataBaseAdapter.setItems(dataBDS);
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
