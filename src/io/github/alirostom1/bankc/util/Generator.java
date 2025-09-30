package io.github.alirostom1.bankc.util;

import java.util.Random;


public class Generator {
    public final static String generateAccountCode(){
        Random rand = new Random();
        int number = rand.nextInt(100000);
        return "CPT-" + String.format("%05d", number);
    }
}
