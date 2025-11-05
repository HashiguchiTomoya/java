package shapes;

//三角形（具象クラス）
public class Triangle extends Shapes
{
	//図形名と辺の初期化
	String name = "";
	double side1 = 0;
	double side2 = 0;
	double side3 = 0;
	
	//コンストラクタの定義
	public Triangle(String name,double side1, double side2, double side3)
	{
		this.name = name;
		this.side1 = side1;
		this.side2 = side2;
		this.side3 = side3;
	}
	
	//ゲッター
	//名前を返す
	public String getName()
	{
		return name;
	}
	
	//面積を計算して返す(Math.sqrtは平方根を計算)
	public double getArea()
	{
		//一辺*一辺*一辺/2をbに代入
		double b = (side1 + side2 + side3) / 2;
		//b*(b-一辺)(b-一辺)(b-一辺)の平方根
		double c = Math.sqrt(b * (b - side1) * (b - side2) * (b - side3));
		return c;
	}
	
	//周囲長を計算して返す
	public double getChircumferene()
	{
		//全部と辺の和
		return side1 + side2 + side3;
	}
}
