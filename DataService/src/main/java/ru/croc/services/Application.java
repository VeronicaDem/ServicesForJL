package ru.croc.services;

import ru.croc.services.dataservice.StringToData;

public class Application {
    public static void main(String [] args) {
        StringToData.generatePatternsQueue();
        System.out.println("23 мая 2021 года");
        System.out.println(StringToData.isPatterned("23 мая 2021 года"));
        System.out.println("23 мая");
        System.out.println(StringToData.isPatterned("23 мая"));
        System.out.println("Больше не обрабатывается в модуле - та же строка на выходе");
        System.out.println(StringToData.isPatterned("15 11 83"));
        System.out.println("от 26 мая 2020");
        System.out.println(StringToData.isPatterned("от 26 мая 2020"));
        System.out.println("от 29.11.2001");
        System.out.println(StringToData.isPatterned("от 29.11.2001"));
        System.out.println("с 28.02.2018 г.");
        System.out.println(StringToData.isPatterned("с 28.02.2018 г."));
        System.out.println("по 26.02.2021 г.");
        System.out.println(StringToData.isPatterned( "по 26.02.2021 г."));
    }
}
