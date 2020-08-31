package com.maths.mathematics.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GenerateRandom {
    public static int getRandomInteger(int min, int max){
        //Generate random int value from 0 to 100
        return (int)(Math.random() * (max - min + 1) + min);
    }
}

