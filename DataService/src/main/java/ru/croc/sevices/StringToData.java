package ru.croc.sevices;

import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToData {
   static final public String year = "([1-9][0-9]{1,3})";
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
   static final public String postfix = "([год|год[аыуе]|годов|лет|годам|годами|годах])";
   static final private PriorityQueue<MyPattern> patterns = new PriorityQueue<MyPattern>(new PatternComparator());

   static void generatePatternsQueue() {
       int order = 1;
       // самый высокий приоритет
       // с-по
       patterns.add(new MyPattern("с " + year + "[" + separators + "]" + month + "[" +separators + "]"
                                               + day + " " + postfix +
                                       " по " + year + "[" + separators + "]" +  month + "[" + separators +
                                              "]" + day + " " + postfix,
                                       order++, (matcher)-> {
                                            int year = Integer.parseInt(matcher.group(1));
                                            int month = Integer.parseInt(matcher.group(2));
                                            int day = Integer.parseInt(matcher.group(3));
                                            // обработка года

                                        }));
       patterns.add(new MyPattern("с " + day + "[" + separators + "]" + month + separators + year + " " +
                                        postfix +
                                       " по " + day + "[" + separators + "]" + month + separators + year + " " + postfix,
                                       order++));
       patterns.add(new MyPattern("с " + year + "[" + separators + "|" + space + "]" + monthWords +
                                                   "[" + separators + "|" + space + "]" + day + " " + postfix +
                                       " по " + year + "[" + separators + "|" + space + "]" + monthWords +
                                               "[" + separators + "|" + space + "]" + day + " " + postfix,
                                        order++));
       patterns.add(new MyPattern("с " + day + "[" + separators + "|" + space + "]"+ monthWords +
                                             "[" + separators + "|" + space + "]" + year + " " + postfix +
                                       " по " + day + "[" + separators + "|" + space + "]" + monthWords +
                                             "[" + separators + "|" + space + "]" + year + " " + postfix,
                                       order++));
       patterns.add(new MyPattern("c " + year + "[" + separators + "]" + month + separators + day +
                                       " по " + year + "[" + separators + "]" + month + separators + day, order++));
       patterns.add(new MyPattern("c " + day + "[" + separators + "]" + month + separators + year +
                                       " по " + day + "[" + separators + "]" + month + separators + year, order++));
       patterns.add(new MyPattern("с " + year + "[" + separators + "|" + space + "]" + monthWords +
                                         "[" + separators + "|" + space + "]" + day +
                                       " по " + year + "[" + separators + "|" + space + "]"
                                         + monthWords + "[" + separators + "|" + space + "]" + day ,
                                       order++));
       patterns.add(new MyPattern("с " + day + "[" + separators + "|" + space + "]" + monthWords +
                                            "[" + separators + "|" + space + "]" + year +
                                       " по " + day + "[" + separators + "|" + space + "]" + monthWords +
                                         "[" + separators + "|" + space + "]" + year, order++));
       // от
       patterns.add(new MyPattern("от " + year + separators + month + "[" + separators + "]"
                                      + day + " " + postfix,
               order++));
       patterns.add(new MyPattern("от " + day + "[" + separators + "]" + month +
                                                        "[" + separators + "]" + year + " " + postfix,
               order++));
       patterns.add(new MyPattern("от " + year + "[" + separators + "|" + space + "]" + monthWords +
                                         "[" + separators + "|" + space + "]" + day + " " + postfix,
               order++));
       patterns.add(new MyPattern("от " + day + "[" + separators + "|" + space + "]" +
                                          monthWords + separators + year + " " + postfix,
               order++));
       patterns.add(new MyPattern("от " + year + separators + month + "[" +separators + "]" + day, order++));
       patterns.add(new MyPattern("от " + day + separators + month + "[" + separators + "]" + year, order++));
       patterns.add(new MyPattern("от " + year + "[" + separators + "|" + space + "]" + monthWords +
                                           "[" + separators + "|" + space + "]" + day,
               order++));
       patterns.add(new MyPattern("от " + day + "[" + separators + "|" + space + "]" + monthWords +
                                                "[" + separators + "|" + space + "]" + year, order++));


       patterns.add(new MyPattern(year + "[" + separators + "]" + month +
                                                  "[" + separators + "]" + day + " " + postfix, order++));
       patterns.add(new MyPattern(day + "[" + separators + "]" + month + "[" + separators +"]" + year + " " + postfix, order++));
       patterns.add(new MyPattern(year + "[" + separators + "|" + space + "]" + monthWords +
                                             "[" + separators + "|" + space + "]" + day + " " + postfix, order++));
       patterns.add(new MyPattern(day + "[" + separators + "|" + space + "]" + monthWords +
                                               "[" + separators + "|" + space + "]" + year + " " + postfix, order++));
       patterns.add(new MyPattern(year + "[" + separators + "]" + month + separators + day, order++));
       patterns.add(new MyPattern(day + "[" + separators + "]" + month + "[" + separators + "]" + year, order++));
       patterns.add(new MyPattern(year + "[" + separators + "|" + space + "]" + monthWords +
                                                "[" + separators + "|" + space + "]" + day, order++));
       patterns.add(new MyPattern(day + "[" + separators + "|" + space + "]" + monthWords +
                                              "[" + separators + "|" + space + "]" + year, order++));
       // с-по
       patterns.add(new MyPattern("c " + day + "[" + separators + "]" + month + " " +
                                         " по ", order++));
       patterns.add(new MyPattern("с " + day + "[" + separators + "|" + space + "]" + monthWords +
                                          " по " + day + "[" + separators + "|" + space + "]" + monthWords, order++));

       // от
       patterns.add(new MyPattern("от " + day + "[" + separators + "]" + month, order++));
       patterns.add(new MyPattern("от " + day + "[" + separators + "|" + space + "]" + monthWords, order++));

       patterns.add(new MyPattern(day + "[" +separators + "]" + month, order++));
       patterns.add(new MyPattern(day + "[" + separators + "|" + space + "]" + monthWords, order++));
   }
   static boolean isPatterned(String string) {
       //generatePatternsQueue();
       Iterator itr = patterns.iterator();

       while (itr.hasNext()) {
           Pattern pattern = Pattern.compile(((MyPattern) itr.next()).getPattern());
           Matcher matcher = pattern.matcher(string);
           if (matcher.find()) return true;
       }
       return false;
   }
}
