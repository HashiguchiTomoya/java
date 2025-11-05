package genric;

import java.util.ArrayDeque;
import java.util.Iterator;

public class GenricStack
{
	public static void main(String[] args)
	{
		ArrayDeque<Integer> stack = new ArrayDeque<>();
		stack.push(10);
		stack.push(20);
		stack.push(30);
		stack.push(40);
		
		int pop = stack.pop();
		System.out.println(pop);
		
		Iterator<Integer> it = stack.iterator();
		String massage = ""; 
		while(it.hasNext())
		{
			massage += it.next() + ",";
		}
		System.out.print(massage);
	}
}
