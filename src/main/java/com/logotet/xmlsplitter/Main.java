package com.logotet.xmlsplitter;

import javax.xml.stream.XMLStreamException;
import java.io.*;
/**
 *
 *
 *
 * */
public class Main {
//    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
//        File inputFile = new File("<full path to xml file>");
//        InputStream inputStream = new FileInputStream(inputFile);
////        ChunkReceiver chunkReceiver = ChunkReceiverFactory.newInstance(ChunkType.TERMINAL);
//        ChunkReceiver chunkReceiver = new ChunkBufferedImpl.Builder()
//                .inFolder("<folder for output file - no last separator!>")
//                .withName("<short output file name>")
//                .withType("xml") // not mandatory, xml default
//                .build();
//
//        XmlSplitter xmlSplitter = new XmlSplitter(inputStream, chunkReceiver);
//        xmlSplitter.splitBy("Listing"); // case sensitive !!

//    }


//    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
//        File inputFile = new File("/home/boban/Testing/TestFiles/syndication.xml");
//        InputStream inputStream = new FileInputStream(inputFile);
////        ChunkReceiver chunkReceiver = ChunkReceiverFactory.newInstance(ChunkType.TERMINAL);
////        ChunkReceiver chunkReceiver = new ListingAnalyzerImpl();
//        ChunkReceiver chunkReceiver = new PicturesAnalyzerImpl();
//
//        XmlSplitter xmlSplitter = new XmlSplitter(inputStream, chunkReceiver);
//        xmlSplitter.splitBy("Pictures"); // case sensitive !!
//        chunkReceiver.endAll();
//    }




    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        File inputFile = new File("/home/boban/Testing/TestFiles/syndication.xml");
        InputStream inputStream = new FileInputStream(inputFile);
//        ChunkReceiver chunkReceiver = ChunkReceiverFactory.newInstance(ChunkType.TERMINAL);
//        ChunkReceiver chunkReceiver = new ListingAnalyzerImpl();
        ChunkReceiver chunkReceiver = new AttrAnalyzerImpl();

        XmlSplitter xmlSplitter = new XmlSplitter(inputStream, chunkReceiver);
        xmlSplitter.splitBy("EmailAddress"); // case sensitive !!
        chunkReceiver.endAll();
    }

}
