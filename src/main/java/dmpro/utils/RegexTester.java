package dmpro.utils;

public class RegexTester {

	public RegexTester() {
		// TODO Auto-generated constructor stub
	}
	public static void main (String[] args) {
		if (args.length != 2) System.exit(0);
		boolean b;
		b =  (args[0].matches(args[1])) ? true : false;
		System.out.println(b);
	}

}
