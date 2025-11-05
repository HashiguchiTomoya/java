package shapes;

//抽象クラス
public abstract class Shapes
{
	
	//オーバーライド
	//図形名
	abstract String getName();
	//面積
	abstract double getArea();
	//周囲長
	abstract double getChircumferene();
	
	//表示形式を定義
	public  void indication()
	{
		System.out.println("図形：" + getName());
		System.out.println("面積：" + getArea());
		System.out.println("周囲長：" + getChircumferene());
	}
	
}
