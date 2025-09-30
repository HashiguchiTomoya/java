package FigureDrawing;

public class Main
{
	public static void main(String[] args)
	{
		//インスタンスの作成
		ShapeManager manager = new ShapeManager();
		
		//図形の追加
		manager.addShape(new Circle(5.0));
		manager.addShape(new Rectangle(4.0 , 6.0));
		manager.addShape(new Triangle(3.0, 4.0, 5.0));
		
		//図形の情報表示
		manager.displayAll();
		//統計情報表示
		manager.showStatistics();
	}
}
