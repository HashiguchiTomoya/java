package GenericStructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

//ジェネリック型を使ったキュークラス
public class genericQueue<T> implements Iterable<T>
{
	//キューの先頭、末尾をさす
	private Node<T> head, tail;
	//キューに格納されている要素数
	private int size;
	
	//各要素を表すノード
	private static class Node<T>
	{
		//ノードが保持する値
		T data;
		//次のノードへの参照
		Node<T> next;
		Node(T data)
		{
			this.data = data;
		}
	}
	
	//キューに追加
	public void enqueue(T item)
	{
		//新しいノードを作成
		Node<T> node = new Node<>(item);
		//キューが空なら両方に新しいノードを追加
		if(isEmpty())
		{
			head = tail = node;
		}
		//tailに新しいノードをつなぎ更新
		else
		{
			tail.next = node;
			tail = node;
		}
		//サイズをインクリメント
		size++;
	}
	
	//級から取り出す
	public T dequeue()
	{
		//キューが空なら例外を投げる
		if(isEmpty()) throw new NoSuchElementException("Queue is empty");
		//データを取得
		T item = head.data;
		//次のノードに更新
		head = head.next;
		//空の状態
		if(head == null) tail = null;
		//サイズをデクリメント
		size--;
		//所得したデータを返す
		return item;
	}
	
	//先頭確認
	public T peek()
	{
		//キューが空なら例外
		if(isEmpty()) throw new NoSuchElementException("Queue is empty");
		//データを返すが削除しない
		return head.data;
	}
	
	//空かどうかを判定
	public boolean isEmpty()
	{
		return head == null;
	}
	
	//要素数を返す
	public int size()
	{
		return size;
	}
	
	//イテレーション対応
	@Override
	public Iterator<T> iterator()
	{
		return new Iterator<T>()
		{
			private Node<T> current = head;
			public boolean hasNext()
			{
				return current != null;
			}
			
			public T next()
			{
				if(!hasNext()) throw new NoSuchElementException();
				T data = current.data;
				current = current.next;
				return data;
			}
		};
	}
}
