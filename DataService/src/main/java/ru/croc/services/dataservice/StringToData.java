package ru.croc.services.dataservice;

import ru.croc.services.Services;
import ru.croc.services.numberService.*;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToData {
    // TODO сделать обработку двузначных годов
   static final public String year = "([1-9][0-9]{3})";
   static final public String month = "(0[1-9]|1[012])";
   static final public String day = "(0[1-9]|1[0-9]|2[0-9]|3[01])";
   static final public String monthWords = "(январ[яьюе]|" +
                                          "январ[её]м|" +
                                          "феврал[яьюе]|" +
                                          "февр[её]м|" +
                                          "март[ауе]|" +
                                          "март|" +
                                          "мартом|" +
                                          "апрел[еьяю]|" +
                                          "апрелем|" +
                                          "ма[йяюе]|" +
                                          "маем|" +
                                          "июн[ьяюе]|" +
                                          "июнем|" +
                                          "июл[ьюяе]|" +
                                          "июлем|" +
                                          "август|" +
                                           "августом|" +
                                          "август[ауе]|" +
                                          "сентябр[ьяюе]|" +
                                          "сентябр[её]м|" +
                                          "октябр[яьюе]|" +
                                          "октябр[её]м|" +
                                          "ноябр[ьяюе]|" +
                                          "ноябр[её]м|" +
                                           "декабр[ьяюе]|" +
                                              "декабр[её]м)";
   static final public String separators = "\\/|\\.|-";
   static final public String space = "\\s";
   static final public String postfix = "([год|год[аыуе]|годов|лет|годам|годами|годах|г\\.])";
   static final private PriorityQueue<MyPattern> patterns = new PriorityQueue<MyPattern>(new PatternComparator());
   public static String s(String number) {
       if(number.endsWith("один ")) {
           int first = number.lastIndexOf("один ");
           int second = first + 4;
           number = number.substring(0, first) + " первого" + number.substring(second);
       }
       else if (number.endsWith("два")) {
           int first = number.lastIndexOf("два");
           int second = first + 3;
           number = number.substring(0, first) + " второго" + number.substring(second);
       }
       else if(number.endsWith("три ")) {
           int first = number.lastIndexOf("три ");
           int second = first + 3;
           number = number.substring(0, first) + " третьего" + number.substring(second);
       }
       else if(number.endsWith("восемь ")) {
           int first = number.lastIndexOf("восемь ");
           int second = first + 7;
           number = number.substring(0, first) + "восьмого" + number.substring(second);
       }
       else {
           int first = number.lastIndexOf("ый");
           if(first != -1) {
               int second = first + 2;
               number = number.substring(0, first) + "ого" + number.substring(second);
           }
           first = number.lastIndexOf("ь");
           if(first != -1) {
               int second  = first + 1;
               number = number.substring(0, first) + "ого" + number.substring(second);
           }
       }
       return number;
   }
   public static String getMonth(long month) {
       switch((int) month) {
           case 1:
               return "январь";
           case 2:
               return "февраль";
           case 3:
               return "март";
           case 4:
               return "апрель";
           case 5:
               return "май";
           case 6:
               return "июнь";
           case 7:
               return "июль";
           case  8:
               return "август";
           case 9:
               return "сентябрь";
           case 10:
               return "октябрь";
           case 11:
               return "ноябрь";
           case 12:
               return "декабрь";
       }
       return "";
   }
   public static String classic_number(String number) {
       if(number.endsWith(" три ")) {
           int first = number.lastIndexOf("три ");
           int second = first + 3;
           number = number.substring(0, first) + " третье" + number.substring(second);
           return number;
       }
       else return s(number);
   }
   public static String po(String number) {
       if(number.split(" ").length >= 2) {
           return s(number);
       }
       if(number.endsWith("один ")) {
           int first = number.lastIndexOf("один ");
           int second = first + 4;
           number = number.substring(0, first) + "первое" + number.substring(second);
       }
       else if (number.endsWith("два")) {
           int first = number.lastIndexOf("два");
           int second = first + 3;
           number = number.substring(0, first) + " второе" + number.substring(second);
       }
       else if(number.endsWith("три ")) {
           int first = number.lastIndexOf("три ");
           int second = first + 3;
           number = number.substring(0, first) + " третье" + number.substring(second);
       }
       else if(number.endsWith("восемь ")) {
           int first = number.lastIndexOf("восемь ");
           int second = first + 7;
           number = number.substring(0, first) + "восьмое" + number.substring(second);
       }
       else {
           int first = number.lastIndexOf("ый");
           if (first != -1) {
               int second = first + 2;
               number = number.substring(0, first) + "ое" + number.substring(second);
           }
           first = number.lastIndexOf("ь");
           if (first != -1) {
               int second = first + 1;
               number = number.substring(0, first) + "ое" + number.substring(second);
           }
       }
       return number;
   }
   public static String ot(long day1, long month1, long year1) {
       String yearString1 = s(Services.numberToSymbol(year1, year1));
       String monthString1 = s_date(getMonth(month1));
       String dayString1 = s(Services.numberToSymbol(day1,day1));

       return "от " + dayString1 + " " + monthString1 + " " + yearString1 + " года ";
   }
    public static String ot(long day1, long month1) {
        String monthString1 = s_date(getMonth(month1));
        String dayString1 = s(Services.numberToSymbol(day1,day1));

        return "от " + dayString1 + " " + monthString1 + " ";
    }
    public static String ot(long day1, String month1, long year1) {
        String yearString1 = s(Services.numberToSymbol(year1, year1));
        String monthString1 = month1;
        String dayString1 = s(Services.numberToSymbol(day1,day1));

        return "от " + dayString1 + monthString1 + " " + yearString1 + "года ";
    }
    public static String ot(long day1, String month1) {
        String monthString1 = month1;
        String dayString1 = s(Services.numberToSymbol(day1,day1));

        return "от " + dayString1 + " " + monthString1 + " ";
    }
   public static String s_date(String month) {
       if(month.equals("март")) {
           return "марта";
       }
       else if(month.equals("май")){
           return "мая";
       }
       else if(month.equals("август")){
           return "августа";
       }
       else {
           return month.replace("ь", "я");
       }
   }
   public static String s_string(long day1, String month1, long year1) {
       String yearString1 = s(Services.numberToSymbol(year1, year1));
       String monthString1 = month1;
       String dayString1 = s(Services.numberToSymbol(day1, day1));

       return "c " + dayString1 + " " + monthString1 + " " + yearString1 + " года ";
   }
   public static String po_string(long day1, long month1, long year1) {
       String yearString1 = Services.numberToSymbol(year1, year1);
       String monthString1 = s_date(getMonth(month1));
       String dayString1 = Services.numberToSymbol(day1, day1);

       dayString1 = po(dayString1);
       yearString1 = po(yearString1);

       return "по " + dayString1 + " " + monthString1 + " " + yearString1+ "года";
   }
   public static String s_string(long day1, long month1, long year1) {
       String yearString1 = s(Services.numberToSymbol(year1, year1));
       String monthString1 = s_date((getMonth(month1)));
       String dayString1 = s(Services.numberToSymbol(day1, day1));

       return "c " + dayString1 + " " + monthString1 + " " + yearString1 + "года ";
   }
   public static String s_string(long day1, long month1) {
       String monthString1 = s_date(getMonth(month1));
       String dayString1 = s(Services.numberToSymbol(day1, day1));

       return "c " + dayString1 + " " + monthString1 + " ";
   }
   public static String s_string(long day1, String month1) {
       String monthString1 = month1;
       String dayString1 = s(Services.numberToSymbol(day1, day1));

       return "c " + dayString1 + " " + monthString1 + " ";
   }
   public static String po_string(long day1, String month1, long year1) {
       String yearString1 = Services.numberToSymbol(year1, year1);
       String monthString1 = month1;
       String dayString1 = Services.numberToSymbol(day1, day1);

       dayString1 = po(dayString1);
       yearString1 = po(yearString1);

       return "по " + dayString1 + " " + monthString1 + yearString1+ "года";
   }
  public static String po_string(long day1, String month1) {
       String dayString1 = po(Services.numberToSymbol(day1, day1));
       String monthString1 = month1;

       return "по " + dayString1 + " " + monthString1 + " ";
  }
  public static String po_string(long day1, long month1) {
       String dayString1 = po(Services.numberToSymbol(day1, day1));
       String monthString1 = s_date(Services.numberToSymbol(month1, month1));

       return "по " + dayString1 + " " + monthString1 + " ";
  }
  public static String classic(long day1, long month1, long year1) {
       String dayString1 = classic_number(Services.numberToSymbol(day1, day1));
       String monthString1 = s_date(getMonth(month1));
       String yearString1 = classic_number(Services.numberToSymbol(year1, year1));

       return dayString1 + " " + monthString1 + " " + yearString1 + " года ";
  }
    public static String classic(long day1, long month1) {
        String dayString1 = classic_number(Services.numberToSymbol(day1, day1));
        String monthString1 = s_date(getMonth(month1));


        return dayString1 + " " + monthString1 + " ";
    }

    public static String classic(long day1, String month1, long year1) {
        String dayString1 = classic_number(Services.numberToSymbol(day1, day1));
        String monthString1 = month1;
        String yearString1 = classic_number(Services.numberToSymbol(year1, year1));

        return dayString1 + " " + monthString1 + " " + yearString1 + " года ";
    }
    public static String classic(long day1, String month1) {
        String dayString1 = classic_number(Services.numberToSymbol(day1, day1));
        String monthString1 = s_date(month1);


        return dayString1 + " " + monthString1 + " ";
    }
   public static void generatePatternsQueue() {
       int order = 1;
       // самый высокий приоритет
       // с-по
       patterns.add(new MyPattern("с " + year + "[" + separators + "]" + month + "[" +separators + "]"
                                               + day + " " + postfix ,
                                       order++, (matcher)-> {
                                            long year1 = Long.parseLong(matcher.group(1));
                                            long month1 = Long.parseLong(matcher.group(2));
                                            long day1 = Long.parseLong(matcher.group(3));

                                            return s_string(day1,
                                                        month1,
                                                        year1
                                                        );
                                        }));
       patterns.add(new MyPattern("по " + year + "[" + separators + "]" +  month + "[" + separators +
               "]" + day + " " + postfix, order++, (matcher)-> {
           long year1 = Long.parseLong(matcher.group(1));
           long month1 = Long.parseLong(matcher.group(2));
           long day1 = Long.parseLong(matcher.group(3));

           return po_string(day1,
                   month1,
                   year1
           );
       }));
       patterns.add(new MyPattern("с " + day + "[" + separators + "]" + month + "[" + separators + "]" + year + " " +
                                        postfix,
                                       order++, (matcher)-> {
                                          long day1 = Long.parseLong(matcher.group(1));
                                          long month1 = Long.parseLong(matcher.group(2));
                                          long year1 = Long.parseLong(matcher.group(3));



                                           return s_string(day1,
                                                      month1,
                                                      year1
                                                   );
                                   }));
       patterns.add(new MyPattern(
               "по " + day + "[" + separators + "]" + month + "[" + separators + "]" + year + " " + postfix,
               order++, (matcher)-> {
           long day1 = Long.parseLong(matcher.group(1));
           long month1 = Long.parseLong(matcher.group(2));
           long year1 = Long.parseLong(matcher.group(3));

           return po_string(day1,
                          month1,
                         year1);
       }));
       patterns.add(new MyPattern("с " + year + "[" + separators + "|" + space + "]" + monthWords +
                                                   "[" + separators + "|" + space + "]" + day + " " + postfix,
                                        order++, (matcher)-> {
                                 long day1 = Long.parseLong(matcher.group(1));
                                 String month1 = matcher.group(2);
                                 long year1 = Long.parseLong(matcher.group(3));


                                 return s_string(day1,
                                            month1,
                                            year1
                                            );
                                         }));
       patterns.add(new MyPattern(
               "по " + year + "[" + separators + "|" + space + "]" + monthWords +
               "[" + separators + "|" + space + "]" + day + " " + postfix, order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(1));
                   String month1 = matcher.group(2);
                   long year1 = Long.parseLong(matcher.group(3));

                   return po_string(day1,
                                    month1,
                                   year1);
               }));
       patterns.add(new MyPattern("с " + day + "[" + separators + "|" + space + "]"+ monthWords +
                                             "[" + separators + "|" + space + "]" + year + " " + postfix,
                                       order++, (matcher)-> {
                                      long day1 = Long.parseLong(matcher.group(1));
                                      String month1 = matcher.group(2);
                                      long year1 = Long.parseLong(matcher.group(3));


                                      return s_string(day1,
                                                  month1,
                                                  year1);
       }));
       patterns.add(new MyPattern("по " + day + "[" + separators + "|" + space + "]" + monthWords +
               "[" + separators + "|" + space + "]" + year + " " + postfix, order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(1));
                   String month1 = matcher.group(2);
                   long year1 = Long.parseLong(matcher.group(3));


                   return po_string(day1,
                           month1,
                           year1);
               }));
       patterns.add(new MyPattern("c " + year + "[" + separators + "]" + month + "[" + separators + "]" + day ,
               order++,
                                      (matcher)-> {
                                       long year1 = Long.parseLong(matcher.group(1));
                                       long month1 = Long.parseLong(matcher.group(2));
                                       long day1 = Long.parseLong(matcher.group(3));

                                       return s_string(day1,
                                                  month1,
                                                  year1
                                                );

                                       }));
       patterns.add(new MyPattern("по " + year + "[" + separators + "]" + month + "[" + separators + "]" + day,
                order++,
               (matcher)-> {
                   long year1 = Long.parseLong(matcher.group(1));
                   long month1 = Long.parseLong(matcher.group(2));
                   long day1 = Long.parseLong(matcher.group(3));

                   return po_string(day1,
                           month1,
                           year1
                   );
               }));
       patterns.add(new MyPattern("c " + day + "[" + separators + "]" + month + "[" + separators + "]",
               order++,
               (matcher)-> {
                     long day1 = Long.parseLong(matcher.group(1));
                     long month1 = Long.parseLong(matcher.group(2));
                     long year1 = Long.parseLong(matcher.group(3));


                   return s_string(day1,
                           month1,
                           year1
                           );

               }));
       patterns.add(new MyPattern("по " + day + "[" + separators + "]" + month + "[" + separators + "]" + year,
                order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(1));
                   long month1 = Long.parseLong(matcher.group(2));
                   long year1 = Long.parseLong(matcher.group(3));


                   return po_string(day1,
                           month1,
                           year1
                   );
               }));
       patterns.add(new MyPattern("с " + year + "[" + separators + "|" + space + "]" + monthWords +
                                         "[" + separators + "|" + space + "]" + day ,
                                       order++, (matcher)-> {
                                          long year1 = Long.parseLong(matcher.group(1));
                                          String month1 = matcher.group(2);
                                          long day1 = Long.parseLong(matcher.group(3));


                                         return s_string(day1,
                                                    month1,
                                                     year1
                                                     );

       }));
       patterns.add(new MyPattern("по " + year + "[" + separators + "|" + space + "]"
               + monthWords + "[" + separators + "|" + space + "]" + day, order++,
               (matcher)-> {
                   long year1 = Long.parseLong(matcher.group(1));
                   String month1 = matcher.group(2);
                   long day1 = Long.parseLong(matcher.group(3));


                   return po_string(day1,
                           month1,
                           year1
                   );
               }));
       patterns.add(new MyPattern("с " + day + "[" + separators + "|" + space + "]" + monthWords +
                                            "[" + separators + "|" + space + "]" + year, order++,
               (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    String month1 = matcher.group(2);
                    long year1 = Long.parseLong(matcher.group(3));


                   return s_string(day1,
                           month1,
                           year1
                           );

               }));
       patterns.add(new MyPattern("по " + day + "[" + separators + "|" + space + "]" + monthWords +
               "[" + separators + "|" + space + "]" + year, order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(1));
                   String month1 = matcher.group(2);
                   long year1 = Long.parseLong(matcher.group(3));


                   return po_string(day1,
                           month1,
                           year1
                   );
               }));
       // от
       patterns.add(new MyPattern("от " + year + "[" + separators + "]" + month + "[" + separators + "]"
                                      + day + " " + postfix,
               order++, (matcher)-> {
                long year1 = Long.parseLong(matcher.group(1));
                long month1 = Long.parseLong(matcher.group(2));
                long day1 = Long.parseLong(matcher.group(3));

                return ot(
                        day1,
                        month1,
                        year1
                );
       }));
       patterns.add(new MyPattern("от " + year + "[" + separators + "|" + space + "]" + monthWords +
               "[" + separators + "|" + space + "]" + day + " " + postfix,
               order++, (matcher)-> {
           long year1 = Long.parseLong(matcher.group(1));
           String month1 = matcher.group(1);
           long day1 = Long.parseLong(matcher.group(3));

           return ot(
                   day1,
                   month1,
                   year1
           );
       }));
       patterns.add(new MyPattern("от " + day + "[" + separators + "]" + month +
                                                        "[" + separators + "]" + year + " " + postfix,
               order++, (matcher)-> {
               long day1 = Long.parseLong(matcher.group(1));
               long month1 = Long.parseLong(matcher.group(2));
               long year1 = Long.parseLong(matcher.group(3));

               return ot(
                   day1,
                   month1,
                   year1
               );
       }));

       patterns.add(new MyPattern("от " + day + "[" + separators + "|" + space + "]" +
                                          monthWords +"[" + separators + "|" + space + "]" + year + " " + postfix,
               order++, (matcher)-> {
           long day1 = Long.parseLong(matcher.group(1));
           String month1 = matcher.group(2);
           long year1 = Long.parseLong(matcher.group(3));

           return ot(
                   day1,
                   month1,
                   year1
           );

       }));
       patterns.add(new MyPattern("от " + year + "[" + separators + "]" + month + "[" +separators + "]" + day, order++,
               (matcher)-> {
                 long year1 = Long.parseLong(matcher.group(1));
                 long month1 = Long.parseLong(matcher.group(2));
                 long day1 = Long.parseLong(matcher.group(3));

                 return ot(day1,
                         month1,
                         year1);
               }
               ));
       patterns.add(new MyPattern("от " + day + "[" + separators + "]" + month + "[" + separators + "]" + year, order++,
               (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    long month1 = Long.parseLong(matcher.group(2));
                    long year1 = Long.parseLong(matcher.group(3));

                   return ot(day1,
                           month1,
                           year1);

               }));
       patterns.add(new MyPattern("от " + year + "[" + separators + "|" + space + "]" + monthWords +
                                           "[" + separators + "|" + space + "]" + day,
               order++,
               (matcher)-> {
                  long year1 = Long.parseLong(matcher.group(1));
                  String month1 = matcher.group(2);
                  long day1 = Long.parseLong(matcher.group(3));

                   return ot(day1,
                           month1,
                           year1);
               }));
       patterns.add(new MyPattern("от " + day + "[" + separators + "|" + space + "]" + monthWords +
                                                "[" + separators + "|" + space + "]" + year, order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(1));
                   String month1 = matcher.group(2);
                   long year1 = Long.parseLong(matcher.group(3));

                   return ot(day1,
                           month1,
                           year1);
               }
                       ));


       patterns.add(new MyPattern(year + "[" + separators + "]" + month +
                                                  "[" + separators + "]" + day + " " + postfix, order++,
               (matcher)-> {
                    long year1 = Long.parseLong(matcher.group(1));
                    long month1 = Long.parseLong(matcher.group(2));
                    long day1 = Long.parseLong(matcher.group(3));

                    return classic(day1,
                                   month1,
                                   year1);
               }));
       patterns.add(new MyPattern(day + "[" + separators + "]" + month + "[" + separators +"]" + year + " " +
               postfix, order++,
               (matcher)-> {
                    long day1 = Long.parseLong(matcher.group(1));
                    long month1 = Long.parseLong(matcher.group(2));
                    long year1 = Long.parseLong(matcher.group(3));

                   return classic(day1,
                           month1,
                           year1);
               }));
       patterns.add(new MyPattern(year + "[" + separators + "|" + space + "]" + monthWords +
                                             "[" + separators + "|" + space + "]" + day + " " + postfix, order++,
               (matcher)-> {
                  long year1 = Long.parseLong(matcher.group(1));
                  String month1 = matcher.group(2);
                  long day1 = Long.parseLong(matcher.group(3));

                   return classic(day1,
                           month1,
                           year1);
               }));
       patterns.add(new MyPattern(day + "[" + separators + "|" + space + "]" + monthWords +
                                               "[" + separators + "|" + space + "]" + year + " " + postfix, order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(1));
                   String month1 = matcher.group(2);
                   long year1 = Long.parseLong(matcher.group(3));

                   return classic(day1,
                           month1,
                           year1);
               }));
       patterns.add(new MyPattern(year + "[" + separators + "]" + month + separators + day, order++,
               (matcher)-> {
                 long year1 = Long.parseLong(matcher.group(1));
                 long month1 = Long.parseLong(matcher.group(2));
                 long day1 = Long.parseLong(matcher.group(3));

                 return classic(day1,
                           month1,
                           year1);
               }));
       patterns.add(new MyPattern(day + "[" + separators + "]" + month + "[" + separators + "]" + year,
               order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(1));
                   long month1 = Long.parseLong(matcher.group(2));
                   long year1 = Long.parseLong(matcher.group(3));

                   return classic(day1,
                           month1,
                           year1);
               }));
       patterns.add(new MyPattern(year + "[" + separators + "|" + space + "]" + monthWords +
                                                "[" + separators + "|" + space + "]" + day, order++,
               (matcher)-> {
                      long year1 = Long.parseLong(matcher.group(1));
                      String month1 = matcher.group(2);
                      long day1 = Long.parseLong(matcher.group(3));

                   return classic(day1,
                           month1,
                           year1);
               }));
       patterns.add(new MyPattern(day + "[" + separators + "|" + space + "]" + monthWords +
                                              "[" + separators + "|" + space + "]" + year, order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(1));
                   String month1 = matcher.group(2);
                   long year1 = Long.parseLong(matcher.group(3));
                   return classic(day1,
                           month1,
                           year1);
               }));
       // с-по
       patterns.add(new MyPattern("c " + day + "[" + separators + "]" + month, order++, (matcher)-> {
                 long day1 = Long.parseLong(matcher.group(1));
                 long month1 = Long.parseLong(matcher.group(2));


                 return s_string(day1,
                             month1
                             );
       }));
       patterns.add(new MyPattern(
               "по " + day + "[" + separators + "]" + month, order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(1));
                   long month1 = Long.parseLong(matcher.group(2));


                   return po_string(day1,
                           month1
                   );
               }));
       patterns.add(new MyPattern("с " + day + "[" + separators + "|" + space + "]" + monthWords, order++,
               (matcher)-> {
                     long day1 = Long.parseLong(matcher.group(1));
                     String month1 = matcher.group(2);


                   return s_string(day1,
                           month1
                          );
               }));
       patterns.add(new MyPattern("по " + day + "[" + separators + "|" + space + "]" + monthWords, order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(1));
                   String month1 = matcher.group(2);


                   return po_string(day1,
                           month1
                   );
               }));
       // от
       patterns.add(new MyPattern("от " + day + "[" + separators + "]" + month, order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(1));
                   long month1 = Long.parseLong(matcher.group(2));

                   return ot(
                           day1,
                           month1
                   );
               }));
       patterns.add(new MyPattern("от " + day + "[" + separators + "|" + space + "]" + monthWords, order++,
               (matcher)-> {
                   long day1 = Long.parseLong(matcher.group(1));
                   String month1 = matcher.group(2);

                   return ot(
                           day1,
                           month1
                   );
               }));

       patterns.add(new MyPattern(day + "[" +separators + "]" + month, order++,
               (matcher)-> {
                  long day1 = Long.parseLong(matcher.group(1));
                  long month1 = Long.parseLong(matcher.group(2));

                  return classic(
                          day1,
                          month1
                  );
               }));
       patterns.add(new MyPattern(day + "[" + separators + "|" + space + "]" + monthWords, order++,
               (matcher)-> {
                long day1 = Long.parseLong(matcher.group(1));
                String month1 = matcher.group(2);
                  return classic(
                          day1,
                          month1
                  );
               }));
   }
   public static String isPatterned(String string) {
       //generatePatternsQueue();
       Iterator itr = patterns.iterator();
       while (itr.hasNext()) {
           MyPattern myPattern = (MyPattern) itr.next();
           Pattern pattern = Pattern.compile(myPattern.getPattern());
           Matcher matcher = pattern.matcher(string);
           if (matcher.find()) {
               return myPattern.action.exec(matcher);
           }
       }
       return string;
   }
}
