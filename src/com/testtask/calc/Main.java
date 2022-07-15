package com.testtask.calc;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static int countOfRomans;

    private static String[] volumes;
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
        if (c == 'D'){
            return 500;
        }
        if (c == 'M'){
            return 1000;
        }
        return -1;
    }

    static boolean isRoman(String string){
        int count = 0;
        for (char c:string.toCharArray()) {
            if (c == 'I'){
                count++;
            }
            if (c == 'V'){
                count++;
            }
            if (c == 'X'){
                count++;
            }
            if (c == 'L'){
                count++;
            }
            if (c == 'C'){
                count++;
            }
            if (c == 'D'){
                count++;
            }
            if (c == 'M'){
                count++;
            }
        }
        if (count == string.length() && !string.equals("")){
            return true;
        }else {
            return false;
        }
    }

    static String intToRoman(int num){
        int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] romanLetters = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
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
        for (String volume : volumes) {
            if (isRoman(volume)){
                countOfRomans++;
            }
        }
        if (countOfRomans == 2){
            for (String volume : volumes) {
                intVolumes.add(romanToInt(volume));
            }
        }else if (countOfRomans == 1){
            throw new Exception("используются одновременно разные системы счисления");
        } else {
            for (String volume: volumes) {
                intVolumes.add(Integer.valueOf(volume));
            }
        }
    }

    public static String calc(String input) throws Exception {
        input = input.replaceAll("\\s+","");
        ArrayList<String> inputVolumes = new ArrayList<>();
        Pattern pattern1 = Pattern.compile("[-]?[0-9]+");
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
        if (inputVolumes.size() != 2){
            throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        intVolumes = new ArrayList<>();
        int answer = 0;
        String strAnswer = null;
        if (input.contains("+")){
            volumes = input.split("[+]");
            valueCheck();
            answer = intVolumes.get(0) + intVolumes.get(1);
            if (countOfRomans == 2) {
                if (answer >= 0){
                    strAnswer = intToRoman(answer);
                }else {
                    throw new Exception("в римской системе нет отрицательных чисел");
                }
            }else {
                strAnswer = String.valueOf(answer);
            }
        }else if (input.contains("-")){
            volumes = input.split("[-]");
            valueCheck();
            answer = intVolumes.get(0) - intVolumes.get(1);
            if (countOfRomans == 2) {
                if (answer >= 0){
                    strAnswer = intToRoman(answer);
                }else {
                    throw new Exception("в римской системе нет отрицательных чисел");
                }
            }else {
                strAnswer = String.valueOf(answer);
            }
        }else if (input.contains("*")){
            volumes = input.split("[*]");
            valueCheck();
            answer = intVolumes.get(0) * intVolumes.get(1);
            if (countOfRomans == 2) {
                if (answer >= 0){
                    strAnswer = intToRoman(answer);
                }else {
                    throw new Exception("в римской системе нет отрицательных чисел");
                }
            }else {
                strAnswer = String.valueOf(answer);
            }
        }
        else if (input.contains("/")){
            volumes = input.split("[/]");
            valueCheck();
            answer = intVolumes.get(0) / intVolumes.get(1);
            if (countOfRomans == 2) {
                if (answer >= 0){
                    strAnswer = intToRoman(answer);
                }else {
                    throw new Exception("в римской системе нет отрицательных чисел");
                }
            }else {
                strAnswer = String.valueOf(answer);
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
