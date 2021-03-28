package ru.croc.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Узел в графе
 * @param <T> тип данных узла
 */
public class Node<T> {
    /**
     * Маркер узла
     */
    String name;
    /**
     * Данные в узле графа
     */
    T data;
    /**
     * Список дочерних элементов
     * Храним ребра
     */
    List<Node<T>> childList;
    /**
     * Есть ли у узла родитель
     */
    boolean hasParent;

    public Node(T data, boolean hasParent, String name) {
        this.data = data;
        this.hasParent = hasParent;
        this.childList = new ArrayList<Node<T>>();
        this.name = name;
    }

    /**
     * Проверка есть ли ребро (nodeStart, nodeEnd)
     * @param nodeEnd - дочка nodeStart (конец ребра)
     */
    public boolean containsEdge(Node<T> nodeEnd) {
        return childList.contains(nodeEnd);
    }
    /**
     * Создать и добавить дочерний узел - образовать ребро
     * @param data данные узла
     */
    public void addChildren(T data, String name) {
        Node newNode = new Node<T>(data, true, name);
        newNode.setHasParent(true);
        addChildren(newNode);
    }
    /**
     * Добавить дочерний узел из существующих
     */
    public void addChildren(Node<T> newNode) {
        childList.add(newNode);
    }
    /**
     * Удалить дочку из узла
     * @param node дочка
     */
    public void removeChild(Node<T> node) {
        childList.remove(node);
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<Node<T>> getChildList() {
        return childList;
    }

    public void setChildList(List<Node<T>> childList) {
        this.childList = childList;
    }

    public boolean isHasParent() {
        return hasParent;
    }

    public void setHasParent(boolean hasParent) {
        this.hasParent = hasParent;
    }
}
