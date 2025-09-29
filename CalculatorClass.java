package Calculator;

import java.util.Scanner;

public class CalculatorClass
{
	public static void main(String[] atrs)
	{
		//変数の定義、初期化
		double num1 = 0;			//一つ目の数値
		double num2 = 0;			//二つ目の数値
		double result = 0;		//合計
		String operator = "";	//演算子
		boolean valid = true;	//tureかfalseを格納
		
		//Scannerを作成
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("計算プログラム（終了は'exit'を入力）");
		
		//継続計算
		while(true)
		{
			System.out.println("1つ目の数値を入力してください：");
			//数値を入力された場合
			if(scanner.hasNextDouble())
			{
				num1 = scanner.nextDouble();
			
				System.out.println("2つ目の数値を入力してください：");
				num2 = scanner.nextDouble();
			
				System.out.println("演算子を入力してください（+、-、*、/）：");
				operator = scanner.next();
			
				switch(operator)
				{
				//演算子が＋だった時
				case "+":
					result = num1 + num2;
					break;
				//演算子が－だった時
				case "-":
					result = num1 - num2;
					break;
				//演算子が*だった時
				case "*":
					result = num1 * num2;
					break;
				//演算子が/だった時
				case "/":
					//num2が0の時
					if(num2 == 0)
					{
						System.out.println("エラー：０で割ることはできません");
						valid = false;
					}
					else
					{
						result = num1 / num2;
					}
					break;
				//演算子の値が無効の時
				default:
					System.out.println("演算子が無効な値です");
					valid = false;
				}
			
				//結果表示
				if(valid == true)
				{
					System.out.printf("計算結果：%.2f %s %.2f = %.2f\n",num1, operator, num2, result);
				}
				//値が正しくない時
				else
				{
					System.out.println("数値ではありません。再入力してください。");
					scanner.next();
				}
			}
			//exitと入力された時
			else
			{
				String input = scanner.next();
				if(input.equalsIgnoreCase("exit"))
				{
					System.out.println("計算プログラムを終了します");
					break;
				}
				//入力された値が正しくない時
				else
				{
					System.out.println("数値ではありません。正しい値を入力してください。");
				}
			}
		}
		
		//scannerをクローズ
		scanner.close();
	}
}
