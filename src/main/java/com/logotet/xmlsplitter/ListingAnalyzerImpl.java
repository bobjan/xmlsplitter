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
public class ListingAnalyzerImpl implements ChunkReceiver {
    static int depth;
    static int order;

    static {
        depth = 0;
        order = 0;
    }

    static HashMap<String, CounterObj> hashMap = new HashMap<String, CounterObj>();
    static Stack<String> stack = new Stack<>();
    static CounterObj counterObj = null;


    String parent = "";
    String currentNode = "";
    static int specCounter = 0;

    /**
     *
     */
    public ListingAnalyzerImpl() {

    }

    @Override
    public void close() {
//        depth--;
    }

    @Override
    public void add(XMLEvent event) {
        Characters chars;
        switch (event.getEventType()) {
            case XMLStreamConstants.START_ELEMENT:
                depth++;
                parent = "";
                if (!stack.empty())
                    parent = stack.peek();
                stack.push(event.toString());
                counterObj = hashMap.get(event.toString());
                if (counterObj == null) {
                    order++;
                    counterObj = new CounterObj(parent, event.toString(), depth, order);
                    hashMap.put(event.toString(), counterObj);
                }
                currentNode = event.toString();
                counterObj.newRow();
                break;
            case XMLStreamConstants.END_ELEMENT:
                depth--;
                stack.pop();
                if (depth < 0) {
                    System.out.println(event.toString());
                    System.out.println("ERrOR!");
                }
                break;
            default:
                chars = event.asCharacters();
                if (!chars.isWhiteSpace()){
                    counterObj.newLength(event.toString().length());
                    if(currentNode.equals("<SpecializationName>"))
                        if(event.toString().length() < 15)
                        System.out.println(event.toString() + "\t" + ++specCounter);

                }
                break;
        }
    }


    @Override
    public void endAll() {
        CounterObj[] arrayList = new CounterObj[80];
        Iterator<Map.Entry<String, CounterObj>> iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            String key = (String) entry.getKey();
            CounterObj counterObj = (CounterObj) entry.getValue();
//            System.out.println(key + counterObj.toString());
            arrayList[counterObj.getOrder()] = counterObj;
        }

        for (int i = 0; i < arrayList.length; i++)
            try {
                System.out.println(arrayList[i].toString());
            } catch (NullPointerException npe) {
            }
    }
}
