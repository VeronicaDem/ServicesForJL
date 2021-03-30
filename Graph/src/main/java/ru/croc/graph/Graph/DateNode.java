package ru.croc.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс представляет собой обрабатываемые {@link Graph} шаблоны для дат и соответствующие экшены {@link Action}
 */
public class DateNode {
    /**
     * Элемент шаблона - год.
     */
    public final static Atom year = new Atom("YYYY", "[0-9]{4}", true);
    /**
     * Элемент шаблона - месяц.
     */
    public final static Atom month = new Atom("MM","0[1-9]|1[012]", true);
    /**
     * Элемент шаблона - месяц с заменой.
     */
    public final static Atom monthReplace = new Atom("MM",month.pattern, (s)-> {

            String res = "";

            switch(Integer.parseInt(s)) {

                case 1:
                    res = "Январь";
                    break;
                case 2:
                    res = "Февраль";
                    break;
                case 3:
                    res = "Март";
                    break;
                case 4:
                    res = "Апрель";
                    break;
                case 5:
                    res = "Май";
                    break;
                case 6:
                    res = "Июнь";
                    break;
                case 7:
                    res = "Июль";
                    break;
                case 8:
                    res = "Август";
                    break;
                case 9:
                    res = "Сентябрь";
                    break;
                case 10:
                    res = "Октябрь";
                    break;
                case 11:
                    res = "Ноябрь";
                    break;
                case 12:
                    res = "Декабрь";
            }
            return res;
        }
    );
    /**
     * Элемент шаблона - день.
     */
    public final static Atom day = new Atom("DD", "0[1-9]|1[0-9]|2[0-9]|3[01]", true);
    /**
     * Разделители, использующиеся в датах.
     */
    public final static List<String> separators = Arrays.asList( ".",
            "/",
            "-");


}
