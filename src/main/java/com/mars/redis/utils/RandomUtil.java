package com.mars.redis.utils;

import java.util.Random;

/**
 * Random Util to generate random strings
 * Created by MarsWang on 2016/10/12.
 */
public class RandomUtil {

    private static final char[] number = {'1','2','3','4','5','6','7','8','9','0'};
    private static final char[] letter = {
            'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','r','s','t',
            'u','v','w','x','y','z','A','B','C','D',
            'E','F','G','H','I','J','K','L','M','N',
            'O','P','Q','R','S','T','U','V','W','X',
            'Y','Z'
    };
    private static final int NUMBER_SIZE = number.length;
    private static final int LETTER_SIZE = letter.length;
    private static final Random rand = new Random(System.currentTimeMillis());

    /**
     * generate random numerical string
     * @param length
     * @return
     */
    public static String generateNumberString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0 ; i < length; ++i)
            sb.append(number[rand.nextInt(NUMBER_SIZE)]);
        return sb.toString();
    }

    /**
     * generate random letter string
     * @param length
     * @return
     */
    public static String generateLetterString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i)
            sb.append(letter[rand.nextInt(LETTER_SIZE)]);
        return sb.toString();
    }
}
