package com.company;

import ru.croc.services.Services;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	  String numberStr = "";
	  Scanner in = new Scanner(System.in);
	  String number = in.next();
		System.out.println(Services.resolveZeroes(number));
    }
}
