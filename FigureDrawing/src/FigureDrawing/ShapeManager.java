package FigureDrawing;

import java.util.ArrayList;

public class ShapeManager
{
	//図形を格納するリスト
	private ArrayList<Shape> shapes;
	
	//コンストラクタ
	public ShapeManager()
	{
		//図形リストを初期化
		shapes = new ArrayList<>();
	}
	
	public void addShape(Shape shape)
	{
		//図形の追加
		shapes.add(shape);
	}
	
	public void displayAll()
	{
		//登録された図形を順番に呼び出す
		for(Shape shape : shapes)
		{
			shape.displayInfo();
			System.out.println("-------------------");
		}
	}
	
	//統計処理
	public void showStatistics()
	{
		double totalArea = 0;
		double totalPerimeter = 0;
		
		for(Shape shape : shapes)
		{
			totalArea += shape.getArea();
			totalPerimeter += shape.getPerimeter();
		}
		
		System.out.println("図形の総数：" + shapes.size());
		System.out.println("総面積    ：" + totalArea);
		System.out.println("総周囲長  ：" + totalPerimeter);
	}
}
