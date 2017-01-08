package com.example.dell.aryashitlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kriti on 01-01-2017.
 */

public class Hitlist extends Activity{

    TextView hitlistTextView;
    ImageButton addImageButton;
    ListView hitlistListView;
    Context ctx = this;
    //ArrayList peoples;
    String [] initialPeople = {"Sir Gregor Clegane","Dunsen","Polliver","Chiswyck","Raff the sweetling","The Tickler","The Hound","Ser Amory","Ilyn Payne","Meryn Trant","Joffrey Baratheon","Queen Cersei","Weese"};
    String [] initialStatus = {"dead","alive","dead","dead","dead","dead","dead","dead","alive","alive","dead","alive","dead"};
    String [] initialDescription = {"Sir Gregor Clegane, Knight of Clegane's Keep\n"+"\n" +
            "House: Clegane, Lannister\n"+"\n" +
            "Reason: His men captured Arya and other smallfolk"," Dunsen, man-at-arms in service to Ser Gregor Clegane\n"+"\n" +
            "House: Clegane\n"+"\n" +
            "Reason: Stole Gendry's horned helmet","Polliver,  man-at-arms in service to Ser Gregor Clegane\n"+"\n" +
            "House: clegane\n"+"\n" +
            "Reason: stole Needle from Arya","Chiswyck,  man-at-arms in service to Ser Gregor Clegane.\n"+"\n" +
            "House: Clegane\n"+"\n" +
            "Reason: Boasted of his participation in the gang rape of Layna","Raff the sweetling, man-at-arms in service to Ser Gregor Clegane.\n"+"\n" +
            "House: Clegane\n"+"\n" +
            "Reason : Killed Lommy Greenhands","The Tickler, man-at-arms in service to Ser Gregor Clegane.\n"+"\n" +
            "House: Clegane\n"+"\n" +
            "Reason : Tortured captives during questioning","The Hound aka Sandor Clegane, a dangerous fighter of Westros\n"+"\n" +
            "House : Clegane\n"+"\n" +
            "Reason : Killed Mycah","Ser Amory,  knight of House Lorch and bannerman of House Lannister\n"+"\n" +
            "House : Lorch, Lannister\n"+"\n" +
            "Reason: Killed Yoren"," Ilyn Payne, a knight from House Payne.\n"+"\n" +
            "House: Payne\n"+"\n" +
            "Reason: Beheaded Eddard Stark on the orders of King Joffrey","Meryn Trant, a knight of House Trant.\n"+"\n" +
            "House: Trant, KingsGuard\n"+"\n" +
            "Reason: Killed Syrio Forel","Joffrey Baratheon, eldest son and heir of King Robert I Baratheon and Queen Cersei Lannister.\n"+"\n" +
            "House : Baratheon of King's Landing\n"+"\n" +
            "Reason: ordered the execution of arya's father. ","Queen Cersei, Queen of Seven Kingdoms\n"+"\n" +
            "House: Lannister\n"+"\n" +
            "Reason : involved in the death of Eddard Stark","Weese, understeward of the Wailing Tower at Harrenhal\n"+"\n" +
            "House: Whent, Lannister\n"+"\n" +
            "Reason : Violently abused Arya."};
    int [] initialImg_location = {R.drawable.maxresdefault,R.drawable.dunsen,R.drawable.polliver,R.drawable.chiswyck,R.drawable.raff,R.drawable.tickler,R.drawable.hound,R.drawable.amory,R.drawable.payne,R.drawable.trant,R.drawable.joffrey,R.drawable.cersei,R.drawable.weese};

    ArrayList<String> people;
    ArrayList<String> status;
    ArrayList<String> description;
    ArrayList<Integer> img_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hitlist);
        hitlistTextView = (TextView)findViewById(R.id.hitlistTextView);
        hitlistListView = (ListView)findViewById(R.id.hitlistListView);
        addImageButton = (ImageButton)findViewById(R.id.addImageButton);

        //peoples = new ArrayList();
        addToDatabase();

        getFromDatabase();

        setOnClicklisteners();

        setList();
    }

    @Override
    public void onResume(){
        super.onResume();
        //Toast.makeText(getApplicationContext(),"Add",Toast.LENGTH_SHORT).show();
        getFromDatabase();
        setList();
    }

    public void addToDatabase(){
        DatabaseHandler dh = new DatabaseHandler(ctx);
        Cursor cr = dh.getInfo(dh);
        if(!cr.moveToFirst()) {
            int i = 0;
            while (i < initialPeople.length) {
                dh.addInfo(dh, initialPeople[i], initialStatus[i], initialDescription[i], initialImg_location[i]);
                i++;
            }
            //Toast.makeText(getBaseContext(), "Data Added", Toast.LENGTH_SHORT).show();
        }
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

    public void setOnClicklisteners(){
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),AddActivity.class);
                startActivity(intent);
            }
        });

        hitlistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),MyFragmentActivity.class);
                intent.putExtra("position",""+position);
                startActivity(intent);
            }
        });
    }

    public void setList(){
        hitlistListView.setAdapter(null);
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,people){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);
                if(status.toArray()[position].equals("dead")){
                    view.setBackgroundColor(Color.RED);
                }
                else if(status.toArray()[position].equals("alive")){
                    view.setBackgroundColor(Color.GREEN);
                }

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };
        hitlistListView.setAdapter(adapter);
    }
}
