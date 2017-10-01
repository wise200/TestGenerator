import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Stack;


public class PostFixEvaluator {
	private static String ops = "*/+~^!PCS$";

	public static String eval(String string) {
		try {
			ArrayList<String> strings = format(string);
			Stack<String> s = new Stack<String>();
			for (String str : strings) {
				if(ops.contains(str)) {
					if (str.equals("+"))
						s.push(String.valueOf(doubleVal(s.pop()) + doubleVal(s.pop())));
					else if (str.equals("/")) {
						double second = doubleVal(s.pop());
						s.push(String.valueOf(doubleVal(s.pop()) / second));
					}
					else if (str.equals("~")) { //Subtraction (- is reserved for negative numbers)
						double second = doubleVal(s.pop());
						s.push(String.valueOf(doubleVal(s.pop()) - second));
					}
					else if (str.equals("*"))
						s.push(String.valueOf(doubleVal(s.pop()) * doubleVal(s.pop())));
					else if (str.equals("^")) {
						double second = doubleVal(s.pop());
						s.push(String.valueOf(Math.pow(doubleVal(s.pop()), second)));
					}
					else if (str.equals("!")) { //Factorials
						double operand = doubleVal(s.pop());
						double n = 1;
						for (int i = 1; i <= operand; i++)
							n *= i;
						s.push(String.valueOf(n));
					} else if (str.toUpperCase().equals("P")) { //Permutations
						double k = doubleVal(s.pop());
						double n = doubleVal(s.pop());
						s.push(String.valueOf(eval(n+"!"+n+","+k+"~!/")));
					} else if (str.toUpperCase().equals("C")) { //Combinations
						double k = doubleVal(s.pop());
						double n = doubleVal(s.pop());
						s.push(String.valueOf(eval(n+"!"+n+","+k+"~!"+k+"!*/")));
					} else if (str.toUpperCase().equals("S")) { //Sqrt
						s.push(String.valueOf(Math.sqrt(doubleVal(s.pop()))));
					} else if (str.equals("$")) {
						NumberFormat f = NumberFormat.getCurrencyInstance();
						s.push(f.format(doubleVal(s.pop())));
					}
					else throw new IllegalArgumentException("Not a parsable String");
				} else {
					s.push(str);
				}
			}
			return s.pop();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Not a parsable String");
		}
	}
	
	public static String evalMultiple(String seed) {
		int i = 0;
		String fin = "";
		while(i < seed.length()) {
			int j = i;
			while(j < seed.length() && validChar(seed.charAt(j)))
				j++;
			if (j == i)
				j++;
			String next = seed.substring(i,j);
			fin += next.length() > 1 ? eval(next) : next;
			i = j;
		}
		return fin;
	}
	
	private static boolean validChar(char c) {
		return isDigit(c) || ops.indexOf(c) >= 0 || c == '#' || c == ' ';
	}
	
	private static ArrayList<String> format(String str) {
		ArrayList<String> list = new ArrayList<String>();
		int i = 0;
		while (i < str.length()) {
			int j = i;
			while (j < str.length() && isDigit(str.charAt(j)))
				j++;
			if (j == i)
				j++;
			String next = str.substring(i,j);
			if (isDigit(next.charAt(0)) || ops.contains(next))
				list.add(next);
			i = j;
		}
		return list;
	}
	
	private static boolean isDigit(char c) {
		return c =='.' || c == '-' || c >= '0' && c <= '9' ;
	}
	
	//Lmao my own implementation of doubleeger.parsedouble()
	private static double doubleVal(String s) {
		/*
		double val = 0, mult = 1;
		char[] chars = s.toCharArray();
		for (double i = chars.length - 1; i >= 0; i--) {
			val += (chars[i] - 48) * mult;
			mult *= 10;
		}
		return val;
		*/ return Double.parseDouble(s);
	}
}
