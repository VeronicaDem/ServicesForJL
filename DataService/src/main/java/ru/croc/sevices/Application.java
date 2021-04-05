package ru.croc.sevices;

public class Application {
    public static void main(String [] args) {
        StringToData.generatePatternsQueue();
        System.out.println(StringToData.isPatterned("23 мая 2021 года"));
        System.out.println(StringToData.isPatterned("23 мая"));
        System.out.println(StringToData.isPatterned("15 11 83"));
        System.out.println(StringToData.isPatterned("от 26 мая 2020"));
        System.out.println(StringToData.isPatterned("от 29.11.2001"));
        System.out.println(StringToData.isPatterned("с 28.02.2018 г. по 26.02.2021 г."));
    }
}
