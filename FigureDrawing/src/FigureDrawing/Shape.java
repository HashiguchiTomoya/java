package FigureDrawing;

public abstract class Shape
{
	protected String name = "";
	
	//図形名前を保持
	public Shape(String name)
	{
		this.name = name;
	}
	
	//具象メソッドをオーバーライド
	public abstract double getArea();		//面積
	public abstract double getPerimeter();	//周囲長
	
	//図形情報を表示
	public void displayInfo()
	{
		System.out.println("図形	  :" + name);
		System.out.println("面積	  :" + getArea());
		System.out.println("周囲長：" + getPerimeter());
	}
	
	public String getName()
	{
		return name;
	}
}
