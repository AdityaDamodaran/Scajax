package com.sct;


import java.util.regex.*;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Pattern pvp = Pattern.compile("(\\w*)=(\\w*)[\\&(\\w*)=(\\w*)]*");
		Matcher m1 = pvp.matcher("name=a&c=d");
		System.out.println(m1.matches());
}
	
}
