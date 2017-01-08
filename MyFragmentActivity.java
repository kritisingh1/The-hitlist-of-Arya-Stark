package com.example.dell.aryashitlist;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by kriti on 07-01-2017.
 */

public class MyFragmentActivity extends FragmentActivity {
    Button addButton,removeFragmentButton,markButton;
    ViewPager pager;
    MyAdapter adapter;
    FragmentManager fragmentManager;

    Context ctx = this;

    ArrayList<String> people;
    ArrayList<String> status;
    ArrayList<String> description;
    ArrayList<Integer> img_location;

    int check,position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hitlist_view_pager);
        Intent intent = getIntent();
        position = Integer.parseInt(intent.getStringExtra("position"));
        //Toast.makeText(getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
        check = 0;
        getFromDatabase();
        fragmentManager = getSupportFragmentManager();
        adapter = new MyAdapter(fragmentManager);
        setIDs();
        setButtons();
        setEvents();
        addPage(position);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFromDatabase();
        if(check==0)addPage(position);
        else if(check==1)addPage();
    }

    private void setButtons() {
        if(adapter.getCount()==0){
            removeFragmentButton.setVisibility(View.GONE);
            markButton.setVisibility(View.GONE);
        }
        else{
            removeFragmentButton.setVisibility(View.VISIBLE);
            markButton.setVisibility(View.VISIBLE);
        }
    }

    private void setEvents() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = 1;
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                startActivity(intent);
            }
        });

        removeFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = pager.getCurrentItem();
                int count = adapter.getCount();
                String removeName = people.get(position);
                String removeStatus = status.get(position);
                String removeDescription = description.get(position);
                int removeImg_Location = img_location.get(position);
                String selection = TableData.TableInfo.NAME+" LIKE ? AND "+ TableData.TableInfo.STATUS+" LIKE ? AND "+ TableData.TableInfo.DESCRIPTION+" LIKE ? AND "+ TableData.TableInfo.IMG_LOCATION+" LIKE ?";
                String [] args = {removeName,removeStatus,removeDescription,""+removeImg_Location};
                DatabaseHandler dh = new DatabaseHandler(ctx);
                SQLiteDatabase db = dh.getWritableDatabase();
                db.delete(TableData.TableInfo.TABLE_NAME,selection,args);
                adapter.removeFrag(position);
                adapter.notifyDataSetChanged();
                pager.setAdapter(adapter);
                if(adapter.getCount()!=0){
                    if(position==adapter.getCount())
                        pager.setCurrentItem(position-1);
                    else pager.setCurrentItem(position);
                }
                setButtons();
            }
        });

        markButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = pager.getCurrentItem();
                String updateName = people.get(position);
                String updateStatus = status.get(position);
                String updateDescription = description.get(position);
                int updateImg_Location = img_location.get(position);
                String newUpdateStatus = "dead";
                if(newUpdateStatus.equals(updateStatus)){
                    Toast.makeText(getApplicationContext(),"Already Dead",Toast.LENGTH_SHORT).show();
                }
                DatabaseHandler dh = new DatabaseHandler(ctx);
                SQLiteDatabase db = dh.getWritableDatabase();
                String selection = TableData.TableInfo.NAME+" LIKE ? AND "+ TableData.TableInfo.STATUS+" LIKE ? AND "+ TableData.TableInfo.DESCRIPTION+" LIKE ? AND "+ TableData.TableInfo.IMG_LOCATION+" LIKE ?";
                String [] args = {updateName,updateStatus,updateDescription,""+updateImg_Location};
                ContentValues values = new ContentValues();
                values.put(TableData.TableInfo.STATUS,newUpdateStatus);
                db.update(TableData.TableInfo.TABLE_NAME,values,selection,args);
                getFromDatabase();
                addPage(position);
            }
        });
    }

    private void setIDs() {
        removeFragmentButton = (Button)findViewById(R.id.removeFragmentButton);
        pager = (ViewPager)findViewById(R.id.pager);
        addButton = (Button)findViewById(R.id.linearLayoutButton);
        markButton = (Button)findViewById(R.id.markButton);
    }
    public void getFromDatabase(){
        people = new ArrayList<>();
        status = new ArrayList<>();
        description = new ArrayList<>();
        img_location = new ArrayList<>();
        DatabaseHandler dh = new DatabaseHandler(ctx);
        Cursor cr = dh.getInfo(dh);
        if(cr.moveToFirst()){
            do{
                people.add(cr.getString(0));
                status.add(cr.getString(1));
                description.add(cr.getString(2));
                img_location.add(cr.getInt(3));
            }while (cr.moveToNext());
        }
        //Toast.makeText(getBaseContext(),"Data Received",Toast.LENGTH_SHORT).show();
    }

    public void addPage(int position){
        pager.setAdapter(null);
        adapter = new MyAdapter(fragmentManager);
        for(int i=0;i<people.size();i++) {
            Bundle bundle = new Bundle();
            bundle.putString("name",people.get(i));
            bundle.putString("status", status.get(i));
            bundle.putString("description", description.get(i));
            bundle.putInt("img_location",img_location.get(i));
            FragmentMethod fragmentChild = new FragmentMethod();
            fragmentChild.setArguments(bundle);
            adapter.addFrag(fragmentChild, people.get(i));
            adapter.notifyDataSetChanged();
        }
        pager.setAdapter(adapter);
        pager.setCurrentItem(position);
        setButtons();
    }

    public void addPage() {
        pager.setAdapter(null);
        adapter = new MyAdapter(fragmentManager);
        for(int i=0;i<people.size();i++) {
            Bundle bundle = new Bundle();
            bundle.putString("name",people.get(i));
            bundle.putString("status", status.get(i));
            bundle.putString("description", description.get(i));
            bundle.putInt("img_location",img_location.get(i));
            FragmentMethod fragmentChild = new FragmentMethod();
            fragmentChild.setArguments(bundle);
            adapter.addFrag(fragmentChild, people.get(i));
            adapter.notifyDataSetChanged();
        }
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getCount()-1);
        setButtons();
    }
}
