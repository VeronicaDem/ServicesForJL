package ru.croc.services;

import ru.croc.services.dataservice.StringToData;

public class Application {
    public static void main(String [] args) {
        StringToData.generatePatternsQueue();
        System.out.println("c 01.03.2021 г.");
        System.out.println(StringToData.isPatterned("c 01.03.2021 г."));
        System.out.println("15 мая 1983 года");
        System.out.println(StringToData.isPatterned("15 мая 1983 года"));
        System.out.println("15-11-1983");
        System.out.println(StringToData.isPatterned("15-11-1983"));
        System.out.println("15/11/1983");
        System.out.println(StringToData.isPatterned("15/11/1983"));
        System.out.println("11/15/1983");
        System.out.println("15 11 83 года - не будет обработано");
        System.out.println(StringToData.isPatterned("15 11 83 года"));
        System.out.println("31 декабря 2021 г");
        System.out.println(StringToData.isPatterned("31 декабря 2021 г."));
        System.out.println("32 декабря 2020 года");
        System.out.println(StringToData.isPatterned("32 декабря 2020 года"));
        System.out.println("Генеральная лицензия Банка России № 3349 от 12.08.2015 г.");
        System.out.println(StringToData.isPatterned("Генеральная лицензия Банка России № 3349 от 12.08.2015 г."));
        System.out.println("Генеральная лицензия Банка России № 3349 от 12 августа 2015");
        System.out.println(StringToData.isPatterned("Генеральная лицензия Банка России № 3349 от 12 августа 2015"));
        System.out.println("09 марта 2021");
        System.out.println(StringToData.isPatterned("09 марта 2021"));
        System.out.println("05 марта 2021");
        System.out.println(StringToData.isPatterned("05 марта 2021"));
        System.out.println("Период проведения акции с 01.03.2021 по 30.04.2021 включительно");
        System.out.println(StringToData.isPatterned("Период проведения акции с 01.03.2021 по 30.04.2021 включительно"));
        System.out.println("[ставки действуют с 01.03.2021 г.]");
        System.out.println(StringToData.isPatterned("[ставки действуют с 01.03.2021 г.]"));
        //System.out.println("по 01.02.2015 по 01.02.2018");
        //System.out.println(StringToData.isPatterned("по 01.02.2015 по 01.02.2018"));
        //System.out.println("с 01.02.2015 c 01.02.2018");
       // System.out.println(StringToData.isPatterned("с 01.02.2015 c 01.02.2018"));
        System.out.println("Период проведения акции с 01.02.2015 по 01.02.2018");
        System.out.println(StringToData.isPatterned("Период проведения акции с 01.02.2015 по 01.02.2018"));
        System.out.println("с 01.02.2015 по 01.02.2018");
        System.out.println(StringToData.isPatterned("с 01.02.2015 по 01.02.2018"));
        //System.out.println("Доходность за период с 28.02.2018г. по 26.02.2021г.");
       // System.out.println(StringToData.isPatterned("Доходность за период с 28.02.2018г. по 26.02.2021г."));
    }
}
