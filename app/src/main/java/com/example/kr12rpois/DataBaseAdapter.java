package com.example.kr12rpois;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataBaseAdapter extends RecyclerView.Adapter<DataBaseAdapter.NotifyViewHolder> {
    private List<DataBD> dataBDList = new ArrayList<>();
    Context context;
    Button1Activity button1Activity;

    public DataBaseAdapter(Context context, Button1Activity button1Activity) {
        this.button1Activity = button1Activity;
        this.context = context;

    }

    class NotifyViewHolder extends RecyclerView.ViewHolder {
        private TextView firstNameTV,nameTV,secondNameTV, timeTV;


        @SuppressLint("SetTextI18n")
        public void bind(DataBD dataBD) {
            timeTV.setText(dataBD.getTime());
            firstNameTV.setText(dataBD.getFirstName());
            nameTV.setText(dataBD.getName());
            secondNameTV.setText(dataBD.getSecondName());
        }

        public NotifyViewHolder(View itemView) {
            super(itemView);
            timeTV = itemView.findViewById(R.id.timeTV1);
            firstNameTV = itemView.findViewById(R.id.FirstNameTV1);
            secondNameTV = itemView.findViewById(R.id.secondNameTV1);
            nameTV = itemView.findViewById(R.id.nameTV1);
        }
    }


    public void setItems(Collection<DataBD> data) {
        dataBDList.addAll(data);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearItems() {
        dataBDList.clear();
        notifyDataSetChanged();
    }

    @Override
    public NotifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_layout, parent, false);
        return new NotifyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotifyViewHolder holder, int position) {
        holder.bind(dataBDList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataBDList.size();
    }

    public void removeAt(int position) {

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME, DBHelper.KEY_ID + "=" + dataBDList.get(position).getId(), null);

        dataBDList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataBDList.size());
    }

}
