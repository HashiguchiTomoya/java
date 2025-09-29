package Interface;

//借りることができるオブジェクトの共通定義
public interface Borrowable
{
	boolean borrow();
	void returnBook();
	boolean isBorrowed();
}
