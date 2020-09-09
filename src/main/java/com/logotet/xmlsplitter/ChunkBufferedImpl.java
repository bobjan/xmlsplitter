package com.logotet.xmlsplitter;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
public class ChunkBufferedImpl implements ChunkReceiver {
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

    XMLOutputFactory outputFactory;

    XMLEventWriter xmlWriter;

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



        ChunkBufferedImpl build() {
            ChunkBufferedImpl chunk = new ChunkBufferedImpl();
            ChunkBufferedImpl.folder = this.folder;
            ChunkBufferedImpl.fileName = this.fileName;
            ChunkBufferedImpl.fileType = this.fileType;
            ChunkBufferedImpl.built = true;
            return chunk;
        }
    }

/**
 *
 *
 * */
    public ChunkBufferedImpl() {
        if (built) {    // If not created via Builder, it must not work at all
            outputFactory = XMLOutputFactory.newInstance();
            File outputFile = new File(String.format("%s%s%s_%06d.%s", folder,separator, fileName, ++increment, fileType));

            try {
                xmlWriter = outputFactory.createXMLEventWriter(new BufferedOutputStream(new FileOutputStream(outputFile), BUFFER_SIZE));
            } catch (XMLStreamException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        if (xmlWriter != null) {
            try {
                xmlWriter.close();
            } catch (XMLStreamException ignored) {
            }
        }
    }

    @Override
    public void add(XMLEvent event) {
        try {
            if (xmlWriter != null) xmlWriter.add(event);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
