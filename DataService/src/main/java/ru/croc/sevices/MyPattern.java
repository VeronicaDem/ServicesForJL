package ru.croc.sevices;

public class MyPattern {
    private String pattern;
    private int order;

    public MyPattern(String pattern, int order) {
        this.pattern = pattern;
        this.order = order;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Pattern{" +
                "pattern='" + pattern + '\'' +
                ", order=" + order +
                '}';
    }
}
