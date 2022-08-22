package com.example.bus;

import static com.example.bus.xml_parser.xml_parsing2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    public static String busId_st_xml,busId_where_xml;
    public static ArrayList<int[]> in_bus;
    protected static String Bus_id;
    public static HashMap<String,String> bus_id;
    private  ArrayList<String> list;
    LinearLayoutManager linearLayoutManager;
    view_custom view_custom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AutoCompleteTextView autoCompleteTextView =findViewById(R.id.autoCompleteTextView);
        Button button=findViewById(R.id.button);

        CSV_parser csv_parser=new CSV_parser();
        try {
            bus_id=csv_parser.bus_ID();
        } catch (IOException e) {
            e.printStackTrace();
        }
        


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bus_number=autoCompleteTextView.getText().toString();//버스 이름
                Bus_id=bus_id.get(bus_number);
                if(Bus_id==null){
                    autoCompleteTextView.setText(null);
                    Toast toast = Toast.makeText(getApplicationContext(), "존재하지 않는 버스입니다",Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                forThread forThread1=new forThread("busid_st","","");
                forThread forThread2=new forThread("busid_where","","");
                forThread1.start();
                forThread2.start();
                try {
                    forThread1.join();
                    forThread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                busId_st_xml=forThread1.getResult();
                busId_where_xml=forThread2.getResult();
                try {
                    in_bus=xml_parsing2();
                } catch (ParserConfigurationException | IOException | SAXException e) {
                    e.printStackTrace();
                }

                try {
                    adapter(xml_parser.xml_parsing1());
                } catch (ParserConfigurationException | IOException | SAXException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void adapter(ArrayList<data> LIST_MENU) {
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        view_custom=new view_custom(LIST_MENU);
        recyclerView.setAdapter(view_custom);
    }
}