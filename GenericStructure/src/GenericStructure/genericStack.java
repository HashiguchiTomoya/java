package GenericStructure;

import java.util.Iterator;
import java.util.NoSuchElementException;

//ジェネリック型を使ったスタッククラス
public class genericStack<T> implements Iterable<T>
{
	//スタックの一番上の様子をさすノード
	private Node<T> top;
	//スタックに現在格納されている要素の数
	private int size = 0;
	
	//スタックの各要素を表すノード
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
	
	//スタックに追加
	public void push(T item)
	{
		//新しいノードを作成
		Node<T> node = new Node<>(item);
		//ノードを次に設定
		node.next = top;
		//新しいノードに更新
		top = node;
		//スタックのサイズをインクリメント
		size++;
	}
	
	//スタックから取り出す
	public T pop()
	{
		//スタックが～なら例外を投げる
		if(isEmpty()) throw new NoSuchElementException("Stack is empty");
		//データを取得
		T item = top.data;
		//次のノードに更新
		top = top.next;
		//サイズをデクリメント
		size--;
		//取得したデータを返す
		return item;
	}
	
	//先頭を確認
	public T peek()
	{
		//空なら例外を返す
		if(isEmpty()) throw new NoSuchElementException("Stack is empty");
		//データを返すが、削除はしない
		return top.data;
	}
	
	//スタックが空かどうかを判定
	public boolean isEmpty()
	{
		return top == null;
	}
	
	//スタックの要素数を返す
	public int size()
	{
		return size;
	}
	
	//イテレーション対応
	@Override
	public Iterator<T> iterator()
	{
		//スタックを順に取り出すためのIteratorを返す
		return new Iterator<T>()
		{
			//現在のノードをさす
			private Node<T> current = top;
			//次の要素があるか
			public boolean hasNext()
			{
				return current !=  null;
			}
		
			//次のノードに進む
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
