package shapes;

import java.util.ArrayList;

public class ShapeManager
{
	//リストの宣言
	private ArrayList<Shapes> shapes = new ArrayList<>();
	
	
	//リストに図形を登録するメソッド
	public void addShape(Shapes shape)
	{
		shapes.add(shape);
	}
	
	//登録した図形を順番に表示させるメソッド
	public void indicationAll()
	{
		for(Shapes shape : shapes)
		{
			shape.indication();
		}
	}
	

}
