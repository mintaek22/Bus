package com.example.bus;

import android.annotation.SuppressLint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CSV_parser {

    //버스번호 버스id
    public HashMap<String, String> bus_ID() throws IOException {
        HashMap<String,String> hashMap=new HashMap<>();

        @SuppressLint("SdCardPath") FileInputStream input=new FileInputStream("/data/data/com.example.bus/files/busid.csv");
        InputStreamReader reader=new InputStreamReader(input,"UTF-8");
        BufferedReader bufferedReader=new BufferedReader(reader);
        String line;
        while ((line= bufferedReader.readLine())!=null){
            String[] str=line.split(",");
            hashMap.put(str[0],str[1]);
         //   System.out.println(str[0]+"/"+str[1]);
        }
        bufferedReader.close();
        return hashMap;
    }
}
