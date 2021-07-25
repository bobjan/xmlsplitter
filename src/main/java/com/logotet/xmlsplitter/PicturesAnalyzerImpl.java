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
public class PicturesAnalyzerImpl implements ChunkReceiver {
    static int[] counter = new int[101];
    int totCounter = 0;

    static {
        for(int i = 0; i< 101; i++)
            counter[i] = 0;

    }

    int picCounter;
    /**
     *
     */
    public PicturesAnalyzerImpl() {
    }

    @Override
    public void close() {
        if(picCounter > 100)
            picCounter = 100;
        counter[picCounter]++;
        picCounter = 0;
        totCounter++;
    }

    @Override
    public void add(XMLEvent event) {
        Characters chars;
        switch (event.getEventType()) {
            case XMLStreamConstants.START_ELEMENT:
                if(event.toString().equals("<PictureUrl>"))
                    picCounter++;

            default:
                break;
        }
    }


    @Override
    public void endAll() {
        System.out.println("Total: " + totCounter);
        for (int i = 0; i < counter.length; i++){
            if(counter[i] > 0)
                System.out.println(String.format("%6d.%10d",i, counter[i]));
        }
    }
}
