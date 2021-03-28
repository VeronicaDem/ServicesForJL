package ru.croc.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ориентированный граф.
 */
public class Graph {
    /**
     * Список всех узлов
     */
    private List<Node<Atom>> vertexList;
    /**
     * Отображение названия узла к его индексу
     */
    private Map<String, Integer> nameToIndex;
    /**
     * Отображение индекса в vertexList к уровню вершины
     */
    private Map<Integer, Integer> indexToLevel;
    private Node<Atom> emptyWordVertex;

    public Graph() {
        this.vertexList = new ArrayList<>();
        this.nameToIndex = new HashMap<>();
        this.indexToLevel = new HashMap<>();
    }

    public Graph(Node<Atom> emptyWordVertex) {
        this();
        this.emptyWordVertex = emptyWordVertex;
        this.vertexList.add(this.emptyWordVertex);
        this.nameToIndex.put(this.emptyWordVertex.name, 0);
        this.indexToLevel.put(0, 0);

    }

    /**
     * true <=> вершина с именем name есть в графе
     *
     * @param name имя вершины
     * @return есть или нет вершина в графе
     */
    public boolean containsVertex(String name) {
        return nameToIndex.get(name) != null;
    }

    /**
     * Добавить изолированную вершину и привязка ее к пустому слову
     *
     * @param data
     */
    public void addVertex(Atom data, String name, boolean needToBeIncludedToEmptyWord) {
        Node<Atom> newNode = new Node<Atom>(data, false, name);
        vertexList.add(newNode);
        int index = vertexList.size() - 1;
        nameToIndex.put(name, index);
        // У всех изолированных вершин ставим уровень 1
        indexToLevel.put(index, 1);
        // И привязываем вершину к пустому слову
        if (needToBeIncludedToEmptyWord) addEdge(emptyWordVertex.name, name);

    }

    /**
     * TODO
     * Добавить вершину вместе с уровнем
     * После вызова нужно обязательно вызвать addEdge
     */
    public void addVertex(Atom data, String name, int level) {
        Node<Atom> newNode = new Node<Atom>(data, false, name);
        vertexList.add(newNode);
        int index = vertexList.size() - 1;
        nameToIndex.put(name, index);
        indexToLevel.put(index, level);
    }

    /**
     * Удалить вершину с именем name
     *
     * @param name имя вершины
     */
    public void deleteVertex(String name) {
        Node delNode = vertexList.get(nameToIndex.get(name));
        // Удаление из списков дочерних элементов у узлов-родителей
        for (Node<Atom> node : vertexList) {
            if (node.getChildList().contains(delNode)) {
                node.removeChild(delNode);
            }
        }
        // Сделать узлы изолированными, если это требуется
        for (Node<Atom> node : vertexList) {
            boolean flagToDelete = true;
            if (delNode.getChildList().contains(node)) {

                for (Node<Atom> parentNode : vertexList) {
                    if (parentNode == delNode) continue;
                    if (parentNode.getChildList().contains(node)) flagToDelete = false;
                }
            }
            if (flagToDelete) node.setHasParent(false);
        }
        vertexList.remove(nameToIndex.get(name));
        nameToIndex.remove(name);
    }

    /**
     * Добавить ребро, то есть добавить привязанный узел к существующему
     *
     * @return message с выполнением задачи
     */
    public String addEdge(String nameVertex1, String nameVertex2) {
        if (nameToIndex.get(nameVertex1) == null || nameToIndex.get(nameVertex2) == null) {
            return "Создайте узлы";
        } else {
            int indexVertex1 = nameToIndex.get(nameVertex1);
            int indexVertex2 = nameToIndex.get(nameVertex2);
            Node<Atom> node1 = vertexList.get(indexVertex1);
            Node<Atom> node2 = vertexList.get(indexVertex2);
            node1.addChildren(node2);

        }
        return "Успешно";
    }

    /**
     * Проверка на изолированность
     *
     * @param node узел
     * @return true <=> узел стал изолированным
     */
    private boolean checkOnIsolation(Node<Atom> node) {
        // Сделать узлы изолированными, если это требуется
        boolean flagToIsolate = true;

        for (Node<Atom> parentNode : vertexList) {
            if (parentNode.getChildList().contains(node)) flagToIsolate = false;
        }
        return flagToIsolate;
    }

    /**
     * Удалить ребро
     */
    public void deleteEdge(String nameVertex1, String nameVertex2) {
        if (nameToIndex.get(nameVertex1) == (Integer) null || nameToIndex.get(nameVertex2) == (Integer) null) {
            return;
        }
        int indexVertex1 = nameToIndex.get(nameVertex1);
        int indexVertex2 = nameToIndex.get(nameVertex2);

        Node<Atom> node1 = vertexList.get(indexVertex1);
        Node<Atom> node2 = vertexList.get(indexVertex2);

        node1.removeChild(node2);

        // могут вершины стать изолированными
        // Сделать узлы изолированными, если это требуется
        if (checkOnIsolation(node2)) node2.setHasParent(false);
    }

    /**
     * Проверка на наличие ребра в графе между двумя вершинами
     *
     * @param nameStart имя вершины - начало ребра
     * @param nameEnd   имя вершины - конец ребра
     */
    public boolean containsEdge(String nameStart, String nameEnd) {
        if (nameToIndex.get(nameStart) == (Integer) null || nameToIndex.get(nameEnd) == (Integer) null) {
            return false;
        }
        int indexStart = nameToIndex.get(nameStart);
        int indexEnd = nameToIndex.get(nameEnd);

        Node<Atom> nodeStart = vertexList.get(indexStart);
        Node<Atom> nodeEnd = vertexList.get(indexEnd);

        return nodeStart.containsEdge(nodeEnd);
    }


    /**
     * Обход графа и поиск по шаблонам
     * TODO
     * Сделать возможность возврата в предыдущий узел, если пошли не по тому пути
     */
    public String searchAndExec(String string) {

        Map<String, Boolean> used = new HashMap<>();
        int k = 0;
        Node<Atom> start = this.emptyWordVertex;
        int i = 0;
        boolean replaceNeed = false;
        String newString = "";
        while (start != null && k < string.length()) {
            Node<Atom> temp = start;
            int tempK = k;
            replaceNeed = false;
            // Поиск нужного начала шаблона
            Node<Atom> node = start;
            newString = "";
            String text = string.substring(k);
            while (node != null) {
                if (used.get(node.name) == null) {
                    used.put(node.name, true);
                    text = text.substring(k);

                    if (node.data.complexPattern) {

                        Pattern pattern = Pattern.compile(node.data.pattern);
                        Matcher matcher = pattern.matcher(text);
                        if (matcher.find()) {
                            k = matcher.end();
                            if (node.data.hasAction) {
                                // Action
                               newString+= node.data.action.exec(text.substring(0, k));

                            }
                            else {
                                newString += text.substring(0, k);

                            }
                            replaceNeed = true;
                        }
                    } else {
                        int ind = text.indexOf(node.data.pattern);
                        if (ind != -1) {
                            k = ind + node.data.pattern.length();
                            if (node.data.hasAction) {
                                // Action
                                newString +=node.data.action.exec(text.substring(0, k));
                            }
                            else {
                                newString += text.substring(0, k);
                            }

                            replaceNeed = true;
                        }

                    }
                    if (replaceNeed) {
                        start = node;
                        i = 0;
                    } else {
                        start = temp;
                        k = tempK;
                        i++;
                    }

                }
                if(i >= start.getChildList().size()) {

                    return newString;
                }
                else  {
                        node = start.getChildList().get(i);
                        replaceNeed = false;
                }

            }
        }
        return newString;

    }
}
