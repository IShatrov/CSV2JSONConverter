package com.netcracker.edu.ishatrov.csv2jsonconverter;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0) {
            Converter.convert(args[0]);
        } else {
            System.out.println("Please specify file path as command line argument");
        }
    }
}