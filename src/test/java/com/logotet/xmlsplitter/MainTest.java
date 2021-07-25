package com.logotet.xmlsplitter;

import org.junit.Ignore;
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
    public void TestBufferedImpl()  throws FileNotFoundException, XMLStreamException {
        File inputFile = new File("/home/boban/Testing/TestFiles/syndication.xml");
        InputStream inputStream = new FileInputStream(inputFile);
//        ChunkReceiver chunkReceiver = ChunkReceiverFactory.newInstance(ChunkType.TERMINAL);
        ChunkReceiver chunkReceiver = new ChunkBufferedImpl.Builder()
                .inFolder("/home/boban/Testing/TestFiles/xmloutput")
                .withName("outl")
                .withType("xml") // not mandatory, xml default
                .build();

        XmlSplitter xmlSplitter = new XmlSplitter(inputStream, chunkReceiver);
        xmlSplitter.splitBy("Listing"); // case sensitive !!
    }


    @Ignore
    @Test
    public void TestAnalyzerImpl()  throws FileNotFoundException, XMLStreamException {
        File inputFile = new File("/home/boban/Testing/TestFiles/syndication.xml");
        InputStream inputStream = new FileInputStream(inputFile);
//        ChunkReceiver chunkReceiver = ChunkReceiverFactory.newInstance(ChunkType.TERMINAL);
        ChunkReceiver chunkReceiver = new ListingAnalyzerImpl();

        XmlSplitter xmlSplitter = new XmlSplitter(inputStream, chunkReceiver);
        xmlSplitter.splitBy("Listing"); // case sensitive !!
        chunkReceiver.endAll();
    }

    @Test
    @Ignore
    public void testAverage(){
        CounterObj counterObj = new CounterObj();
        counterObj.newRow();
        counterObj.newLength(10);
        assertEquals(10, counterObj.getAvrgLengh());
        counterObj.newRow();
        counterObj.newLength(8);
        assertEquals(9, counterObj.getAvrgLengh());
        counterObj.newRow();
        counterObj.newLength(3);
        assertEquals(7, counterObj.getAvrgLengh());




    }


}