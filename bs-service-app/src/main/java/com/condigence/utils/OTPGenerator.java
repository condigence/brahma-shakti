package com.condigence.utils;

import java.util.Random;

public class OTPGenerator {

    public static void main(String[] args) {
        String num = getRandomNumberString();
        System.out.println(num);
    }

    public static String getRandomNumberString() {
        // It will generate 4 digit random Number.
        // from 0 to 9999
        Random rnd = new Random();
        int number = rnd.nextInt(9999);

        // this will convert any number sequence into 6 character.
        return String.format("%04d", number);
    }
}
