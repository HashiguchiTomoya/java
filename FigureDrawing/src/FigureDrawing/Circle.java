package FigureDrawing;

//円
public class Circle extends Shape
{
	//フィールドの定義
	private double radius = 0;
	
	//コンストラクタ
	public Circle(double radius)
	{
		//スーパークラスで呼び出す
		super("Circle");
		//引数で渡された値を代入
		this.radius = radius;
	}
	
	//面積を求めるメソッド
	public double getArea()
	{
		//円の計算
		return Math.PI * radius * radius;
	}
	
	//周囲長を求めるメソッド
	public double getPerimeter()
	{
		//円周の計算
		return 2 * Math.PI * radius;
	}
}
