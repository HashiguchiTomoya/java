package FigureDrawing;

//三角形
public class Triangle extends Shape
{
	//フィールド定義
	private double sideA = 0;
	private double sideB = 0;
	private double sideC = 0;
	
	//コンストラクタ
	public Triangle(double sideA, double sideB, double sideC)
	{
		//スーパークラスで呼び出す
		super("Triangle");
		//引数で渡された値を代入
		this.sideA = sideA;
		this.sideB = sideB;
		this.sideC = sideC;
	}
	
	//面積を求めるメソッド
	public double getArea()
	{
		double total = getPerimeter() / 2;
		//ヘロンの公式を使用
		return Math.sqrt(total * (total - sideA) * (total - sideB) * (total - sideC));
	}
	
	//周囲長を求めるメソッド
	public double getPerimeter()
	{
		return sideA + sideB + sideC;
	}
}

