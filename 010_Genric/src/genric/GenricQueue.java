package genric;

public class GenricQueue 
{
	static class Queue<T>
	{
		final int SIZE = 5;
		Object[] values = new Object[SIZE + 1];
		int head = 0;
		int tail = 0;
		
		//エンキュー
		boolean enqueue(T data)
		{
			if(data == null)
			{
				return false;
			}
			
			if(((tail + 1) % values.length) == head)
			{
				return false;
			}
			
			values[tail++] = data;
			tail = tail % values.length;
			return true;
		}
		
		@SuppressWarnings("unchecked")
		//デキュー
		T dequeue()
		{
			T data = null;
			if(tail != head)
			{
				data = (T)values[head++];
				head = head % values.length;
			}
			return data;
		}
		
		boolean isEmpty()
		{
			return (tail == head);
		}
	}
	
	public static void main(String[] args)
	{
		Queue<Long> q = new Queue<Long>();
		q.enqueue((long) 10);
		q.enqueue((long) 20);
		q.enqueue((long) 30);
		q.enqueue((long) 40);
		q.enqueue((long) 50);
		q.enqueue((long) 60);
		
		System.out.println(q.dequeue());
		q.enqueue((long) 70);
		
		while(!q.isEmpty())
		{
			long data = q.dequeue();
			System.out.print(data+ ",");
		}
		System.out.println("");
		
	}
}
