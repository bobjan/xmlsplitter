package com.logotet.xmlsplitter;

import javax.xml.stream.events.XMLEvent;
/**
 *
 *     @author :Boban Jankovic
 *     @since :2016
 *
 *    iplementations of this interface should deal with different outpust streams
 *    @todo To write proper instructions how to write other implementation classes
 * */
public interface ChunkReceiver {


    public void close();

    public void add(XMLEvent event);
}
