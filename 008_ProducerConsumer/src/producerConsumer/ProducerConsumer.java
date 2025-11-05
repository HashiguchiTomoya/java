package producerConsumer;

//BlockingQueue：キューの空きがない場合は追加スレッドをブロックし、キューが空の場合は取得スレッドをブロックする
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer
{
	//キューの容量を10に定義
	public static final int QUEUE_CAPACITY = 10;
	//キューの定義
	public static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
	
	public static void main(String[] args)
	{
		//生産者インスタンスの作成(new Threadはコンストラクタを呼び出し、new Priducerはインスタンスの生成)
		Thread producerThread = new Thread(new Producer(), "producerThread");
		//消費者インスタンスの作成
		Thread consumerThread = new Thread(new Consumer(), "consumerThread");
		
		//producerThreadを呼び出して実行
		producerThread.start();
		//consumerThreadを呼び出して実行
		consumerThread.start();
		
	}
	
	//生産者クラス(implements Runnable：実行可能な処理)
	static class Producer implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				int value = 0;
				while(true)
				{
					//キューの要素数を代入
					int queueSize = queue.size();
					//生産数した値を表示
					System.out.println("生産者：" + value);
					//キューに値を挿入
					queue.put(value++);
					//1秒間に1個生産
					Thread.sleep(1000);
					//キューの要素数を表示
					System.out.println("キューの要素数を表示：" + queueSize);
				}
				
			}
			//待機中にほかのスレッドから中断された時の例外
			catch(InterruptedException e)
			{
				//現在実行中のスレッドに割り込み信号を送る
				Thread.currentThread().interrupt();
			}
		}
	}
	
	//消費者クラス(implements Runnable：実行可能な処理)
	static class Consumer implements Runnable
	{
		@Override
		public void run()
		{
			try
			{
				while(true)
				{
					//キューから値を取り出す
					int value = queue.take();
					//消費した値を表示
					System.out.println("消費者：" + value);
					//3秒に1個消費
					Thread.sleep(3000);
				}
			}
			catch(InterruptedException e)
			{
				//現在進行中のスレッドに割り込み信号を送る
				Thread.currentThread().interrupt();
			}
		}
	}
}
