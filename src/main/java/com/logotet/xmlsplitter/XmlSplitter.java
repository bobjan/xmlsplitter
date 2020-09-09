package com.logotet.xmlsplitter;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 *
 *     @author :Boban Jankovic
 *     @since :2016
 *
 *  XmlSplitter splits XML file into many XML chunks according to specified node name
 *
 *
 *
 *
 * */
public class XmlSplitter {
    public static final int BUFFER_SIZE = 8192;

    BufferedInputStream bufferedInput;

    boolean nodeFound;

    XMLInputFactory inputFactory;
    XMLEventReader reader;
    QName splitNodeName;

    ChunkType chunkType;
    ChunkReceiver chunkReceiver = null;
    Class<? extends ChunkReceiver> implClass;


    /**
     * @param inputStream
     *          input stream repesenting XML file to be parsed
     *
     * @param chunkReceiver
     *          implementation of ChunkReceiver interface
     * */
    public XmlSplitter(InputStream inputStream, ChunkReceiver chunkReceiver) throws XMLStreamException {
        implClass = chunkReceiver.getClass();
        bufferedInput = new BufferedInputStream(inputStream, BUFFER_SIZE);
        inputFactory = XMLInputFactory.newInstance();
        reader = inputFactory.createXMLEventReader(bufferedInput);
    }


    /**
     *
     * */
    public void splitBy(String nodename) {
        splitNodeName = QName.valueOf(nodename);// case sensitive !!! unfortunately
        chunkReceiver = null;
        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = (StartElement) event;
                        if (nodeFound) {
                            if (chunkReceiver != null) chunkReceiver.add(event);
                        }
                        if (startElement.getName().equals(splitNodeName)) {
                            chunkReceiver = newChunkInstance();
                            if (chunkReceiver != null) chunkReceiver.add(event);
                            nodeFound = true;
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = (EndElement) event;

                        if (nodeFound)
                            if (!endElement.getName().equals(splitNodeName))
                                if (chunkReceiver != null)
                                    chunkReceiver.add(event); // only inner element event is written

                        if (endElement.getName().equals(splitNodeName)) {
                            if (chunkReceiver != null) {
                                chunkReceiver.add(event);
                                chunkReceiver.close();
                            }
                            chunkReceiver = null;
                            nodeFound = false;
                        }
                        break;
                    default:
                        if (chunkReceiver != null) chunkReceiver.add(event);
                        break;
                }
            }
            reader.close();
            if (chunkReceiver != null) chunkReceiver.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } finally {
            if (bufferedInput != null) {
                try {
                    bufferedInput.close();
                } catch (IOException ignored) {
                }
            }
        }
    }


    /**
     *
     *
     * */
    private ChunkReceiver newChunkInstance() {
        try {
            return implClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
