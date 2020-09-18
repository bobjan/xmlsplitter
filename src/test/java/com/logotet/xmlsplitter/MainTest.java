package com.logotet.xmlsplitter;

import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * @author : Boban Jankovic
 * @since : 2020
 **/
public class MainTest {

    @Test
    public void main()  throws FileNotFoundException, XMLStreamException {
        File inputFile = new File("/home/boban/Testing/TestFiles/syndication.xml");
        InputStream inputStream = new FileInputStream(inputFile);
//        ChunkReceiver chunkReceiver = ChunkReceiverFactory.newInstance(ChunkType.TERMINAL);
        ChunkReceiver chunkReceiver = new ChunkBufferedImpl.Builder()
                .inFolder("/home/boban/Testing/TestFiles")
                .withName("outl")
                .withType("xml") // not mandatory, xml default
                .build();

        XmlSplitter xmlSplitter = new XmlSplitter(inputStream, chunkReceiver);
        xmlSplitter.splitBy("Listing"); // case sensitive !!
    }
}