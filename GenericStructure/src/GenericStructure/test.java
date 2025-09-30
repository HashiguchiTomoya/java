package GenericStructure;

public class test
{
	public static void main(String[] args)
	{
		genericStack<Integer> stack = new genericStack<>();
		stack.push(10);
		stack.push(20);
		stack.push(30);
		
		System.out.println("Stack peek:" + stack.peek()); //30
		System.out.println("Stack pop:" + stack.pop()); //30
		for(int val : stack)
		{
			System.out.println("Stack item:" +val);
		}
		
		//Queue test
		genericQueue<String> queue = new genericQueue<>();
		queue.enqueue("A");
		queue.enqueue("B");
		queue.enqueue("C");
		System.out.println("Queue peek:" + queue.peek()); //A
		System.out.println("Queue dequeue:" + queue.dequeue()); //A
		for(String val : queue)
		{
			System.out.println("Queue item:" + val); //B,C
		}
	}
}
