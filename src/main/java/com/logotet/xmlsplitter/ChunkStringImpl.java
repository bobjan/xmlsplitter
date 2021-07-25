package com.logotet.xmlsplitter;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.nio.file.FileSystems;

/**
 *
 *     @author :Boban Jankovic
 *     @since :2016
 *
 *
 *  @todo To write proper instructions how to use this class and how to write other implementation classes
 *
 *
 * */
public class ChunkStringImpl implements ChunkReceiver {
    private static final int BUFFER_SIZE;
    static String fileName;
    static String fileType;
    static String folder;
    static String separator;
    static boolean built = false;

    static {
        BUFFER_SIZE = 8192;
        separator = FileSystems.getDefault().getSeparator();
    }

    private static int increment = 0;


    BufferedWriter bufferedWriter;

    XMLOutputFactory outputFactory;

    XMLEventWriter xmlWriter;

    StringBuilder stringBuilder = new StringBuilder();

    public static class Builder {
        String fileName;
        String folder;
        String fileType = "xml";

        public Builder inFolder(String folder) {
            this.folder = folder;
            return this;
        }

        public Builder withType(String folder) {
            this.fileType = fileType;
            return this;
        }

        public Builder withName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        ChunkStringImpl build() {
            ChunkStringImpl chunk = new ChunkStringImpl();
            ChunkStringImpl.folder = this.folder;
            ChunkStringImpl.fileName = this.fileName;
            ChunkStringImpl.fileType = this.fileType;
            ChunkStringImpl.built = true;
            return chunk;
        }
    }

/**
 *
 *
 * */
    public ChunkStringImpl() {
        if (built) {    // If not created via Builder, it must not work at all
           File outputFile = new File(String.format("%s%s%s_%06d.%s", folder,separator, fileName, ++increment, fileType));
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        if(bufferedWriter != null){
            try {
                bufferedWriter.write(stringBuilder.toString());
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void add(XMLEvent event) {
        if(event.toString().equals("&"))
            stringBuilder.append("&amp;");
        else
        if(event.toString().equals("'"))
            stringBuilder.append("&apos;");
        else
        if(event.toString().equals("\""))
            stringBuilder.append("&quot;");
        else
        if(event.toString().equals(">"))
            stringBuilder.append("&gt;");
        else
        if(event.toString().equals("<"))
            stringBuilder.append("&lt;");
        else
        stringBuilder.append(event.toString());
        stringBuilder.append(event.toString());
        stringBuilder.append(event.toString());
    }

    @Override
    public void endAll() {
        System.out.println("Total " + increment);
    }
}
