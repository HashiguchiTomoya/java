package ProducerConsumer;

import java.util.LinkedList;
import java.util.Queue;

//共有バファを管理するクラス
class sharedBuffer
{
	private final Queue<Integer> queue = new LinkedList<>();
	private final int capacity;
	
	//バッファの最大サイズを設定
	public sharedBuffer(int capacity)
	{
		this.capacity = capacity;
	}
	
	//生産者がバファに商品を追加するためのメソッド
	public synchronized void produce(int item) throws InterruptedException
	{
		while (queue.size() == capacity)
		{
			wait(); //バッファが満杯なら待機
		}
		
		queue.add(item);
		System.out.println("生産:" + item);
		notifyAll(); //消費者に連絡
	}
	
	//消費者がバッファから商品を取り出すためのメソッド
	public synchronized int consume() throws InterruptedException
	{
		while (queue.isEmpty())
		{
			wait(); //バッファが空なら待機
		}
		
		int item = queue.remove();
		System.out.println("消費:" + item);
		notifyAll(); //生産者に連絡
		return item;
	}
}

//生産者スレッド
class producer implements Runnable
{
	private final sharedBuffer buffer;
	
	//使用する共有バッファを受け取って保存
	public producer(sharedBuffer buffer)
	{
		this.buffer = buffer;
	}
	
	@Override
	public void run()
	{
		int item = 0;
		try
		{
			while(true)
			{
				//繰り返し呼び出して商品を精算
				buffer.produce(item++);
				Thread.sleep(500); //生産の間隔
			}
		}
		catch(InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}
}

//消費者スレッドの絵定義
class consumer implements Runnable
{
	private final sharedBuffer buffer;
	
	public consumer(sharedBuffer buffer)
	{
		this.buffer = buffer;
	}
	
	@Override
	public void run()
	{
		try
		{
			while(true)
			{
				//繰り返し呼び出して商品を取得
				buffer.consume();
				Thread.sleep(800); //消費の間隔
			}
		}
		catch(InterruptedException e)
		{
				Thread.currentThread().interrupt();
		}
	}
}

//スレッドの起動と開始
public class producerConsumer
{
	public static void main(String[] args)
	{
		sharedBuffer buffer = new sharedBuffer(5); //バッファのサイズ＝５
		
		Thread producerThread = new Thread(new producer(buffer));
		Thread consumerThread = new Thread(new consumer(buffer));
		
		producerThread.start();
		consumerThread.start();
	}
}
