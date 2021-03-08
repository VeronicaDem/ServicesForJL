package com.company;

import ru.croc.services.Services;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	  String numberStr = "";
	  Scanner in = new Scanner(System.in);
	  long number = in.nextLong();
		System.out.println(Services.numberToSymbol(number, number));
    }
}
