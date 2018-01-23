package com.scut.originsystem.util;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class RandomUtil {

    public static double doubleRandom(int min ,int max){
        return min + ((max - min) * new Random().nextDouble());
    }

    public static String fourValid(){
        int i = (int)(Math.random()*10000);
        return String.format("%04d", i);
    }

    public static double[] randomVoucher(int amount ,int number){
        double[] voucher =new double[number];
        Set<Double> set = new TreeSet<>();
        set.add(0.00);
        set.add(amount * 1.00);
        DecimalFormat format = new DecimalFormat("#.00");
        while (set.size() != number + 1){
            double i = Double.parseDouble(format.format(Math.random()*amount));
            set.add(i);
        }
        Iterator<Double> iterator = set.iterator();
        int i = 0;
        double a = iterator.next();
        double b = iterator.next();
        while (iterator.hasNext()){
            voucher[i++] = Double.parseDouble(format.format(b-a));
            a = b;
            b = iterator.next();
            if(i >= number) break;
        }
        voucher[i] = Double.parseDouble(format.format(b-a));
        return voucher;
    }

}
