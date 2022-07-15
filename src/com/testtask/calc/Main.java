package com.testtask.calc;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static int countOfRomans;

    private static ArrayList<String> inputVolumes;
    private static ArrayList<Integer> intVolumes;

    static int value(char c){
        if (c == 'I'){
            return 1;
        }
        if (c == 'V'){
            return 5;
        }
        if (c == 'X'){
            return 10;
        }
        if (c == 'L'){
            return 50;
        }
        if (c == 'C'){
            return 100;
        }
        return -1;
    }

    static String intToRoman(int num){
        int[] values = {100,90,50,40,10,9,5,4,1};
        String[] romanLetters = {"C","XC","L","XL","X","IX","V","IV","I"};
        StringBuilder roman = new StringBuilder();
        for(int i = 0; i<values.length; i++)
        {
            while(num >= values[i])
            {
                num = num - values[i];
                roman.append(romanLetters[i]);
            }
        }
        return roman.toString();
    }
    static int romanToInt(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            int s1 = value(s.charAt(i));
            if (i + 1 < s.length()){
                int s2 = value(s.charAt(i+1));

                if (s1 >= s2){
                    res = res + s1;
                }else {
                    res = res + s2 - s1;
                    i++;
                }
            }else{
                res = res + s1;
            }
        }
        return res;
    }

    static void valueCheck() throws Exception {
        countOfRomans = 0;
        for (String volume : inputVolumes) {
            Pattern pattern = Pattern.compile("^(?=[MDCLXVI])M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
            Matcher matcher = pattern.matcher(volume);
            if (matcher.find()){
                countOfRomans++;
            }
        }
        if (countOfRomans == 2){
            for (String volume : inputVolumes) {
                intVolumes.add(romanToInt(volume));
            }
        }else if (countOfRomans == 1){
            throw new Exception("используются одновременно разные системы счисления");
        } else {
            for (String volume: inputVolumes) {
                intVolumes.add(Integer.valueOf(volume));
            }
        }
    }

    public static String calc(String input) throws Exception {
        input = input.replaceAll("\\s+","");
        inputVolumes = new ArrayList<>();
        Pattern pattern1 = Pattern.compile("\\d+");
        Matcher matcher1 = pattern1.matcher(input);
        String[] strings = input.split("[-+/*]");
        if (strings.length > 2){
            while (matcher1.find()){
                int start = matcher1.start();
                int end = matcher1.end();
                if(!input.substring(start,end).equals("")) {
                    inputVolumes.add(input.substring(start, end));
                }
            }
        }else  if (strings.length == 2){
            for (String s: strings) {
                if (!s.contains("-"))
                    inputVolumes.add(s);
            }
        }
        if (strings.length != 2){
            throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        intVolumes = new ArrayList<>();
        int answer;
        String strAnswer = null;
        valueCheck();
        String operator = input.substring(inputVolumes.get(0).length(), inputVolumes.get(0).length() + 1);
        switch (operator) {
            case "+" -> {
                answer = intVolumes.get(0) + intVolumes.get(1);
                if (countOfRomans == 2) {
                    if (answer >= 0) {
                        strAnswer = intToRoman(answer);
                    } else {
                        throw new Exception("в римской системе нет отрицательных чисел");
                    }
                } else {
                    strAnswer = String.valueOf(answer);
                }
            }
            case "-" -> {
                valueCheck();
                answer = intVolumes.get(0) - intVolumes.get(1);
                if (countOfRomans == 2) {
                    if (answer >= 0) {
                        strAnswer = intToRoman(answer);
                    } else {
                        throw new Exception("в римской системе нет отрицательных чисел");
                    }
                } else {
                    strAnswer = String.valueOf(answer);
                }
            }
            case "*" -> {
                valueCheck();
                answer = intVolumes.get(0) * intVolumes.get(1);
                if (countOfRomans == 2) {
                    if (answer >= 0) {
                        strAnswer = intToRoman(answer);
                    } else {
                        throw new Exception("в римской системе нет отрицательных чисел");
                    }
                } else {
                    strAnswer = String.valueOf(answer);
                }
            }
            case "/" -> {
                valueCheck();
                answer = intVolumes.get(0) / intVolumes.get(1);
                if (countOfRomans == 2) {
                    if (answer >= 0) {
                        strAnswer = intToRoman(answer);
                    } else {
                        throw new Exception("в римской системе нет отрицательных чисел");
                    }
                } else {
                    strAnswer = String.valueOf(answer);
                }
            }
        }
        return strAnswer;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println(calc(scanner.nextLine()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
