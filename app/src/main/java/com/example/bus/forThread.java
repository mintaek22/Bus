package com.example.bus;

import static com.example.bus.MainActivity.Bus_id;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class forThread extends Thread{

    private final String which_xml;
    private final String par1;
    private final String par2;
    private  String result;

    forThread(String which_xml,String par1,String par2){
        this.which_xml=which_xml;
        this.par1=par1;
        this.par2=par2;
    }

    public void run(){
        if(which_xml.equals("busid_where")){
            try {
                busid_where();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(which_xml.equals("busid_st")) {
            try {
                busid_st();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(which_xml.equals("bus_arrive")) {
            try {
                bus_arrive(par1,par2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void busid_where() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=aHPdC0ARtgpabzxsk6IgyHsEDJ9CDxf5QM9i%2BSD3BjwOAHyC1UdQQbMBVDsT3jvVhi%2BFAZGmbctDwH%2Fg%2BFQhnQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("busRouteId", "UTF-8") + "=" + URLEncoder.encode(Bus_id, "UTF-8"));//bus_id
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        result=sb.toString();
    }

    public void busid_st() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=aHPdC0ARtgpabzxsk6IgyHsEDJ9CDxf5QM9i%2BSD3BjwOAHyC1UdQQbMBVDsT3jvVhi%2BFAZGmbctDwH%2Fg%2BFQhnQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("busRouteId","UTF-8") + "=" + URLEncoder.encode(Bus_id, "UTF-8")); /**/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        result=sb.toString();
    }

    public void bus_arrive(String st_id,String ord) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRoute"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=aHPdC0ARtgpabzxsk6IgyHsEDJ9CDxf5QM9i%2BSD3BjwOAHyC1UdQQbMBVDsT3jvVhi%2BFAZGmbctDwH%2Fg%2BFQhnQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("stId","UTF-8") + "=" + URLEncoder.encode(st_id, "UTF-8")); /*정류장ID*/
        urlBuilder.append("&" + URLEncoder.encode("busRouteId","UTF-8") + "=" + URLEncoder.encode(Bus_id, "UTF-8")); /*노선ID*/
        urlBuilder.append("&" + URLEncoder.encode("ord","UTF-8") + "=" + URLEncoder.encode(ord, "UTF-8")); /*정류장 순번*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        result=sb.toString();
    }
    public String getResult() {
        return result;
    }
}
