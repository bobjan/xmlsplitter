package com.logotet.xmlsplitter;

/**
 * @author : Boban Jankovic
 * @since : 2020
 **/
public class CounterObj {
    String parentName;
    String nodeName;

    int counter = 0;
    int maxLength = Integer.MIN_VALUE;
    int minLength = Integer.MAX_VALUE;
    double avrgLengh = 0.0;
    int depth;
    int order = 0;
    boolean isParent = true;


    public CounterObj() {
    }

    public CounterObj(String parentName, String nodeName, int depth, int order) {
        this.parentName = parentName;
        this.nodeName = nodeName;
        this.depth = depth;
        this.order = order;
    }

    public void newRow() {
        counter++;
    }

    public void newLength(int length) {
        isParent = false;
        if (length < minLength)
            minLength = length;
        if (length > maxLength)
            maxLength = length;
        avrgLengh = (avrgLengh * (counter - 1) + length) / counter;
    }

    public double getAvrgLengh() {
        return avrgLengh;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("");
        for(int i = 1; i < depth; i++){
            sb.append("    ");
        }
        nodeName = sb.toString() + nodeName;
        if (isParent) {
//            return parentName + sb.toString() + nodeName + sbTrail.toString() + counter;
            return String.format("%-30s   %10d",nodeName,counter);
        } else {
//            return  parentName + sb.toString() + nodeName + sbTrail.toString() + counter + "\t" + minLength + "\t" + maxLength + "\t" + String.format("%.2f", avrgLengh);
            return String.format("%-30s   %10d%12d%12d%12.2f",nodeName,counter, minLength, maxLength, avrgLengh);

        }
    }
}
