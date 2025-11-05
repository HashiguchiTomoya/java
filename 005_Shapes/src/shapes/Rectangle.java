package shapes;

//長方形（具象クラス）
public class Rectangle extends Shapes
{
	//図形名と幅と高さを初期化
	String name = "";
	double height = 0;
	double width = 0;
	
	//コンストラクタの定義
	public Rectangle(String name,double height, double width)
	{
		this.name = name;
		this.height = height;
		this.width = width;
	}
	
	//ゲッター
	//名前を返す
	public String getName()
	{
		return name;
	}
	
	//面積を計算して返す
	public double getArea()
	{
		//幅*高さ
		return height * width;
	}
	
	//周囲長を計算して返す
	public double getChircumferene()
	{
		//幅+高さ*2
		return 2 * (height + width);
	}
}
