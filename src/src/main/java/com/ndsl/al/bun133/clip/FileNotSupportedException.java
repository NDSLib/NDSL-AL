package com.ndsl.al.bun133.clip;

public class FileNotSupportedException extends Throwable {
    public FileNotSupportedException(String s){
        System.out.println("File:"+s+" is not Supported.");
    }
}
