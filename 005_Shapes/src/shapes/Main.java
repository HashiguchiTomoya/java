package shapes;

public class Main 
{
	public static void main(String[] args)
	{
		//ShapeManagerをインスタンス化
		ShapeManager manager = new ShapeManager();
		
		//addShapeを呼び出して図形を登録
		manager.addShape(new Circle("円", 5.0));
		manager.addShape(new Rectangle("長方形", 5.0, 6.0));
		manager.addShape(new Triangle("三角形", 5.0, 5.0 ,5.0));
		
		//indicationAllを呼び出して図形名と計算結果を表示させる
		manager.indicationAll();
		
	}
}
