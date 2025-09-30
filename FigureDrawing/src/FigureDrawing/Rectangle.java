package FigureDrawing;

//長方形
public class Rectangle extends Shape
{
	//フィールド定義
	private double width = 0;
	private double height = 0;
	
	//コンストラクタ
	public Rectangle(double width, double height)
	{
		//スーパークラスで呼び出す
		super("Rectangle");
		//引数で渡された値を代入
		this.width = width;
		this.height = height;
	}
	
	//面積を求めるメソッド
	public double getArea()
	{
		return width * height;
	}
	
	//周囲長を求めるメソッド
	public double getPerimeter()
	{
		return 2 * (width * height);
	}
}
