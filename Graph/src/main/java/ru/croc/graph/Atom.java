package ru.croc.graph;

/**
 * Атом шаблона
 * Атом - отдельный смысловой объект
 */
public class Atom {
    /**
     * Конкретный паттерн, определяющий атом.
     */
    final String pattern;
    /**
     * Имя смыслового объекта.
     */
    final String name;
    /**
     * Экшн (может и не быть)
     * Создается из заменяющего шаблона отдельно. Пока в реализации это - лямбда.
     */
    Action action;
    boolean hasAction;
    boolean complexPattern;
    public Atom(String name, String pattern, boolean complexPattern) {
        this.name = name;
        this.pattern = pattern;
        hasAction = false;
        this.complexPattern = complexPattern;
    }
    public Atom(String name, String pattern, Action action) {
        this(name, pattern, true);
        this.action = action;
        hasAction = true;
    }
}
