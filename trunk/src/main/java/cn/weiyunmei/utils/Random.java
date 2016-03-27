package cn.weiyunmei.utils;

import java.util.HashMap;
import java.util.Map;

public class Random {
	
	private static final Map<Integer,String> codeTable = new HashMap<Integer,String>();
	static{
		codeTable.put(0, "0");
		codeTable.put(1, "1");
		codeTable.put(2, "2");
		codeTable.put(3, "3");
		codeTable.put(4, "4");
		codeTable.put(5, "5");
		codeTable.put(6, "6");
		codeTable.put(7, "7");
		codeTable.put(8, "8");
		codeTable.put(9, "9");
		codeTable.put(10, "A");
		codeTable.put(11, "B");
		codeTable.put(12, "C");
		codeTable.put(13, "D");
		codeTable.put(14, "E");
		codeTable.put(15, "F");
		codeTable.put(16, "G");
		codeTable.put(17, "H");
		codeTable.put(18, "I");
		codeTable.put(19, "J");
		codeTable.put(20, "K");
		codeTable.put(21, "L");
		codeTable.put(22, "M");
		codeTable.put(23, "N");
		codeTable.put(24, "O");
		codeTable.put(25, "P");
		codeTable.put(26, "Q");
		codeTable.put(27, "R");
		codeTable.put(28, "S");
		codeTable.put(29, "T");
		codeTable.put(30, "U");
		codeTable.put(31, "V");
		codeTable.put(32, "W");
		codeTable.put(33, "X");
		codeTable.put(34, "Y");
		codeTable.put(35, "Z");
	}
	
	public static String getRandomCode(){
		String random = "";
		for(int iIndex=0;iIndex<6;iIndex++){
			int code = (int)(Math.random()*36)-1;
			random+= codeTable.get(code);
		}
		return random;
	}
	
	public static void main(String[] args) {
		int a = (int)(Math.random()*34);
		System.out.println(a);
	}
	
}
