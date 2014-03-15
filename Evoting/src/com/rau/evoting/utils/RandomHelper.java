package com.rau.evoting.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RandomHelper {

    public static BigInteger randomBigInteger(BigInteger n) {
        SecureRandom rnd = new SecureRandom();
        int maxNumBitLength = n.bitLength();
        BigInteger aRandomBigInt;
        do {
            aRandomBigInt = new BigInteger(maxNumBitLength, rnd);
            // compare random number less than given number
        } while (aRandomBigInt.compareTo(n) > 0); 
        return aRandomBigInt;
    }
    
    
    public static BigInteger randomNonZeroBigInteger(BigInteger n) {
        SecureRandom rnd = new SecureRandom();
        int maxNumBitLength = n.bitLength();
        BigInteger zero = new BigInteger("0");
        BigInteger aRandomBigInt;
        do {
            aRandomBigInt = new BigInteger(maxNumBitLength, rnd);
            // compare random number less than given number 
        } while (aRandomBigInt.compareTo(n) > 0 || aRandomBigInt.equals(zero)); 
        return aRandomBigInt;
    }
    
}
