package com.logotet.xmlsplitter;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

/**
 * @author :Boban Jankovic
 * @todo To write proper instructions how to use this class and how to write other implementation classes
 * @since :2016
 */
public class AttrAnalyzerImpl implements ChunkReceiver {
    static HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
    static int specCounter = 0;
    Integer nothing = 0;


    StringBuilder stringBuilder;

    /**
     *
     */
    public AttrAnalyzerImpl() {
        stringBuilder = new StringBuilder();
    }

    @Override
    public void close() {
//                    if(currentNode.equals("<SpecializationName>"))
//                        hashMap.put(event.toString(), nothing);
        if(stringBuilder.toString().length() < 10)
            System.out.println(stringBuilder.toString() + "\t" + ++specCounter);

    }

    @Override
    public void add(XMLEvent event) {
        Characters chars;
        switch (event.getEventType()) {
            case XMLStreamConstants.START_ELEMENT:
                break;
            case XMLStreamConstants.END_ELEMENT:
                break;
            default:
                chars = event.asCharacters();
                if (!chars.isWhiteSpace()){
                    stringBuilder.append(event.toString());
                }
                break;
        }
    }


    @Override
    public void endAll() {
        System.out.println("Ujupno je bilo " + specCounter);


        Iterator<Map.Entry<String, Integer>> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            String key = (String) entry.getKey();
            System.out.println(key);
        }
    }
}
