package calculator;

import java.util.Scanner;

public class Calculator
{
	public static void main(String[] args)
	{
		//scannerを作成
		Scanner sr = new Scanner(System.in);
		
		//変数の定義、初期化
		double num1 = 0;
		double num2 = 0;
		String operator = "";
		double result = 0;
		boolean judge = true;
		
		System.out.println("計算プログラム(終了のときはexitを入力)");
		
		while(true)
		{
			System.out.println("一つ目の数値を入力してください");
			
			if(sr.hasNextDouble())
			{
				num1 = sr.nextDouble();
				
				System.out.println("二つ目の数字を入力してください");
				if(sr.hasNextDouble())
				{
					num2 = sr.nextDouble();
					
					System.out.println("演算子を入力してください");
					operator = sr.next();
					
					switch(operator)
					{
					//演算子が+だったとき
					case "+":
						judge = true;
						result = num1 + num2;
						break;
						
					//演算子が-だったとき
					case "-":
						judge = true;
						result = num1 - num2;
						break;
						
					//演算子が*だったとき
					case "*":
						judge = true;
						result = num1 * num2;
						break;
						
					//演算子が/だったとき
					case "/":
						judge = true;
						if(num2 == 0)
						{
							judge = false;
							System.out.println("０で割ることはできません");
						}
						else
						{
							judge = true;
							result = num1 / num2;
						}
						break;
						
					//演算子が無効な場合
					default:
						judge = false;
						System.out.println("無効な値です");
						break;
					}
					
					//結果表示
					if(judge == true)
					{
						System.out.printf("%s\n",judge);
						System.out.printf("%.2f %s %.2f = %.2f\n", num1, operator, num2, result);
					}
					else
					{
						System.out.println("値が正しくありません。再度入力してください。");
					}
					
				}
				else
				{
					System.out.println("値が正しくありません。再度入力してください。");
				}
			}
			else
			{
				String input = sr.next();
				if(input.equals("exit"))
				{
					System.out.println("システムを終了します。");
					break;
				}
				else
				{
					System.out.println("値が正しくありません。再度入力してください。");
				}
			}
		}
		
		sr.close();
	}
}
