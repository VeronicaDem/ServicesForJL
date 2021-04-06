package ru.croc.sevices;

import java.util.regex.Matcher;

public interface Action {
    abstract String exec(Matcher s);
}
