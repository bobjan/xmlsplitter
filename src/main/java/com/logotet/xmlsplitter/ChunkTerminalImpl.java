package com.logotet.xmlsplitter;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.XMLEvent;
/**
 *
 *     @author :Boban Jankovic
 *     @since :2016
 *
 *  for testing purpose only
 * */
public class ChunkTerminalImpl implements ChunkReceiver{
    XMLEventWriter writer;
    XMLOutputFactory outputFactory;

    public ChunkTerminalImpl() {
        outputFactory = XMLOutputFactory.newInstance();
        System.out.println("***********************************Created terminal impl");
    }


    @Override
    public void close() {
        System.out.println("close terminal  !!!!!");

    }

    @Override
    public void add(XMLEvent event) {
        System.out.println(event.toString());
        System.out.println("Adding Terminal");
    }

    @Override
    public void endAll() {

    }
}
