package com.logotet.xmlsplitter;
/**
 *
 *     @author :Boban Jankovic
 *     @since :2016
 *
 *
 * Idea abandoned, but can be realized if I get ideas for different kind of ChunkReceivers
 *
 *
 * */
public class ChunkReceiverFactory {
    public static ChunkReceiver newInstance(ChunkType chunkType) {
        switch (chunkType) {
            // replaced by Builder pattern
//            case BUFFERED:
//                return new ChunkBufferedImpl();
            case TERMINAL:
                return new ChunkTerminalImpl();
        }
        return null;
    }
}
