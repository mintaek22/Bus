package com.example.bus;

import static com.example.bus.MainActivity.busId_st_xml;
import static com.example.bus.MainActivity.busId_where_xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class xml_parser {


    //버스id->모든정류장
    public static ArrayList<data> xml_parsing1() throws ParserConfigurationException, IOException, SAXException {
        ArrayList<data> arr = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        InputSource is = new InputSource(new StringReader(busId_st_xml));
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(is);
        document.getDocumentElement().normalize();

        NodeList childeren = document.getElementsByTagName("itemList"); // 자식 노드 목록 get
        String dir = "";
        boolean Direction=true;
        for (int i = 0; i < childeren.getLength(); i++) {
            Node node = childeren.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) { // 해당 노드의 종류 판정(Element일 때)
                Element ele = (Element) node;
                String stationNm=ele.getElementsByTagName("stationNm").item(0).getTextContent();
                String st_id=ele.getElementsByTagName("station").item(0).getTextContent();
                String direction = ele.getElementsByTagName("direction").item(0).getTextContent();

                if (i == 0) dir = direction;
                else {
                    if (!dir.equals(direction) && !direction.equals(" ")) {
                        dir = direction;
                        Direction=false;
                    }
                }
                if(Direction) arr.add(new data(st_id,"0",stationNm));
                else arr.add(new data(st_id,"1",stationNm));
            }
        }
        return arr;
    }

    protected static ArrayList<int[]> xml_parsing2() throws ParserConfigurationException, IOException, SAXException {
        ArrayList<int[]> list=new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        InputSource is2 = new InputSource(new StringReader(busId_where_xml));
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(is2);
        document.getDocumentElement().normalize();

        NodeList childeren = document.getElementsByTagName("itemList"); // 자식 노드 목록 get
        for(int i = 0; i < childeren.getLength(); i++){
            Node node = childeren.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) { // 해당 노드의 종류 판정(Element일 때)
                Element ele = (Element) node;
                int[] arr=new int[3];
                int sectOrd= Integer.parseInt(ele.getElementsByTagName("sectOrd").item(0).getTextContent());//구간 순번
                int congestion = Integer.parseInt(ele.getElementsByTagName("congetion").item(0).getTextContent());//혼잡도
                int stopFlag = Integer.parseInt(ele.getElementsByTagName("stopFlag").item(0).getTextContent());//정류장 도착여부 0이 운행중
                arr[0]=sectOrd;
                arr[1]=congestion;
                arr[2]=stopFlag;
                list.add(arr);
            }
        }
        return list;
    }

    protected static String[] xml_parsing3(String xml) throws ParserConfigurationException, IOException, SAXException {

        String[] time=new String[2];
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        InputSource is2 = new InputSource(new StringReader(xml));
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        Document document = documentBuilder.parse(is2);
        document.getDocumentElement().normalize();

        NodeList childeren = document.getElementsByTagName("itemList"); // 자식 노드 목록 get
        for(int i = 0; i < childeren.getLength(); i++){
            Node node = childeren.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE) { // 해당 노드의 종류 판정(Element일 때)
                Element ele = (Element) node;
                time[0]=ele.getElementsByTagName("arrmsg1").item(0).getTextContent();
                time[1]=ele.getElementsByTagName("arrmsg2").item(0).getTextContent();
            }
        }

        return time;

    }
}
