package ru.croc.graph.Module;

import ru.croc.graph.Graph.*;

import java.util.*;

/**
 * Модуль по преобразованию шаблонов RegExp
 * TODO
 * Использует граф определений для последовательной обработки шаблонов - создается один раз а) TODO при изменении файла
 *                                                                                          б) при запуске программы
 * В текущей реализации граф определений работает только для дат
 *
 */
public class ModuleOptimal {
    /**
     *
     */
    List<String> patternsNode = new ArrayList<>();
    Graph graphDefinition = new Graph();
    Map<String, Atom> patternToAtom = new HashMap<>();

    public ModuleOptimal() {
        patternToAtom.put("DD", DateNode.day);
        patternToAtom.put("MM", DateNode.month);
        patternToAtom.put("YYYY", DateNode.year);
        // Паттерн для даты
        // | или
        String separators = "";
        for (String separator : DateNode.separators) {
            separators += separator + "|";
        }
        separators = separators.substring(0, separators.length() - 1);
        patternsNode.add("[DD|MM|YYYY][" + separators +
                "][DD|MM|YYYY][" + separators +
                "][DD|MM|YYYY][" + separators +
                "]");
       /* String string = patternsNode.get(patternsNode.size() - 1);
        patternsNode.set(patternsNode.size() -1, string.substring(0, string.length() - 1));
       */
        if (graphDefinition.emptyWordVertex == null) {
            graphDefinition.emptyWordVertex = new Node<>(new Atom(
                    "",
                    "",
                    false),
                    false,
                    "emptyWord");
        }
    }

    /**
     * Добавление в граф шаблона
     * TODO Собрать спецсимволы
     *
     * @param graph     - граф, куда будет добавляться паттерн
     * @param pattern   - шаблон
     * @param replacing - замена
     */
    public void transformPatternToGraph(Graph graph, String pattern, String replacing) {
        // здесь разбиение шаблона на узлы
        // проверка на созданные пользователем узлы
        // игнор ^ и $. ^ не исключается только в группах ( символы в () или в [])
        // Специсимволы для символов в Java используются c \\ - признак атома с complexPattern = true
        // Группы: [], [^ ]
        // Интервал: -. Например, [a-zA-Z]
        // Квантификаторы - для обозначения количества символов. ?, *, +, {n} || {n,} || {n, m}
        // Необходимо различать + в режимах (жадный режим, сверхжадный режим, ленивый режим)

        // | - "или" в своих шаблонах
        // ~ - замена.

        //  Индентификатор начала спецсимвола в cтроке pattern
        int startSpecSymbol = 0;
        // Индентификатор конца в строке замен - replacing
        int endReplacing = 0;
        // Уровень
        int level = 1;
        boolean alternative = false;
        String nameSpecSymbol = "";
        String simpleSymbols = "";
        Node<Atom> start = graph.emptyWordVertex;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '[') {
                alternative = true;
            }
            if (pattern.charAt(i) == ']') {
                alternative = false;
            }

            Node<Atom> orderStart = start;
            if (alternative && pattern.charAt(i) != '[') {
                level++;
                while (pattern.charAt(i + 1) != ']') {
                    nameSpecSymbol += pattern.charAt(i);
                    // TODO добавить обработку дальнейших спецсимволов
                    if (patternToAtom.get(nameSpecSymbol) != null) {
                        // обработка с уже известными action

                        simpleSymbols = simpleSymbols.substring(0, simpleSymbols.length() - nameSpecSymbol.length());
                        // добавляем в узлы простой текст
                        // и сдвигаем start - очередной узел, к которому будут добавляться новые узлы
                        for (int j = 0; j < simpleSymbols.length(); j++) {
                            String data = String.valueOf(simpleSymbols.charAt(i));
                            Random random = new Random();
                            String name = "v" + random.nextInt();
                            while (graph.containsVertex(name)) {
                                name = "v" + random.nextInt();
                                // в будущем будет другая обработка name
                                // TODO ввести уровни узлов
                            }
                            if (start == graph.emptyWordVertex) {

                                orderStart = graph.addVertex(new Atom(
                                        data,
                                        data,
                                        false
                                ), name, true);
                                graph.addEdge(start.getName(), name);


                            } else {
                                orderStart = graph.addVertex(new Atom(
                                        data,
                                        data,
                                        false
                                ), name, level);
                                graph.addEdge(start.getName(), name);


                            }
                        }
                        Random random = new Random();
                        String name = "v" + random.nextInt();
                        while (graph.containsVertex(name)) {
                            name = "v" + random.nextInt();
                            // в будущем будет другая обработка name
                            // TODO ввести уровни узлов
                        }
                        // Добавление узла
                        if (graph.emptyWordVertex == start) {
                            orderStart = graph.addVertex(patternToAtom.get(nameSpecSymbol), name, true);

                            graph.addEdge(start.getName(), name);
                        } else {
                            orderStart = graph.addVertex(patternToAtom.get(nameSpecSymbol), name, level);
                            graph.addEdge(start.getName(), name);
                        }
                        nameSpecSymbol = "";
                        i++;
                    }
                    else {
                        simpleSymbols += pattern.charAt(i);

                    }
                }
            }
            else {

                if (pattern.charAt(i) != ']' && pattern.charAt(i) != '[') {

                    start = orderStart;
                    if(patternToAtom.get(nameSpecSymbol) == null) {
                        nameSpecSymbol += pattern.charAt(i);
                        simpleSymbols += pattern.charAt(i);
                    }
                    else {
                        simpleSymbols = simpleSymbols.substring(0, simpleSymbols.length() - nameSpecSymbol.length());
                        // добавляем в узлы простой текст
                        // и сдвигаем start - очередной узел, к которому будут добавляться новые узлы

                        for (int j = 0; j < simpleSymbols.length(); j++) {
                            String data = String.valueOf(simpleSymbols.charAt(i));
                            Random random = new Random();
                            String name = "v" + random.nextInt();
                            while (graph.containsVertex(name)) {
                                name = "v" + random.nextInt();
                                // в будущем будет другая обработка name
                                // TODO ввести уровни узлов
                            }
                            if (start == graph.emptyWordVertex) {

                                orderStart = graph.addVertex(new Atom(
                                        data,
                                        data,
                                        false
                                ), name, true);
                                graph.addEdge(start.getName(), name);


                            } else {
                                orderStart = graph.addVertex(new Atom(
                                        data,
                                        data,
                                        false
                                ), name, level);
                                graph.addEdge(start.getName(), name);


                            }
                            start = orderStart;
                        }
                        // Добавление узла
                        start = orderStart;
                        Random random = new Random();
                        String name = "v" + random.nextInt();
                        while (graph.containsVertex(name)) {
                            name = "v" + random.nextInt();
                            // в будущем будет другая обработка name
                            // TODO ввести уровни узлов
                        }
                        if (graph.emptyWordVertex == start) {
                            orderStart = graph.addVertex(patternToAtom.get(nameSpecSymbol), name, true);

                            graph.addEdge(start.getName(), name);
                        } else {
                            orderStart = graph.addVertex(patternToAtom.get(nameSpecSymbol), name, level);
                            graph.addEdge(start.getName(), name);
                        }

                        nameSpecSymbol = "";
                        start = orderStart;
                    }
                }
            }
        }
    }
}
