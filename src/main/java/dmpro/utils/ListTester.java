package dmpro.utils;

import java.util.ArrayList;
import java.util.List;

public class ListTester {

	public ListTester() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> l = new ArrayList();
		l.add("number1");
		l.add("number 700");
		l.add("XXX");
		
		int i = 0;
		for (;;) {
			String current = l.get(i++);
			String next = l.get(i);
			//i = 1
			String merge = current.toString() + next.toString();
			l.set(i, merge);
			l.remove(i-1);
			l.stream().forEach(a -> System.out.println(a.toString()));
			break;
		}

	}

}
