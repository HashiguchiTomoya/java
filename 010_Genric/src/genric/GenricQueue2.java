package genric;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

public class GenricQueue2
{
	public static void main(String[] args)
	{
		Queue<Integer> q = new ArrayDeque<>();
		q.add(1);
		q.add(2);
		q.add(3);
		q.add(4);
		q.add(5);
		
		String massage = "";
		Iterator<Integer> it = q.iterator();
		while(it.hasNext())
		{
			massage += it.next() + ",";
		}
		System.out.print(massage);
	}
}
