package com.condigence.utils;

public class Test {
    public static void main(String[] args) {
        String s = "vishnu.tiwary@gmail.com";

        int at = s.indexOf("@");
        int maskLen = at - 2;
        System.out.println(maskLen);

        String mask = "";
        for(int i = 0; i< maskLen ; i++){
            mask = mask + "*";
        }

        if(at > 0 ){
            s = s.toLowerCase();
            System.out.println( s.charAt(0) + mask+ s.substring(at - 1));
        }

    }
}
