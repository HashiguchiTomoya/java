package employeeManager;

import java.util.Scanner;

public class Main
{
	//EmployeeManaerをインスタンス化
	private static EmployeeManager manager = new EmployeeManager();
	
	public static void main(String[] args)
	{
		//変数定義・初期化
		int id = 0;					//社員番号
		String name = "";			//社員名
		int select = 0;				//選択
		double salary = 0;			//給料(正社員)
		double contractMoney = 0;	//契約金
		double hourlyWage = 0;		//時給
		double workingHours = 0;	//労働時間
		
		//Scannerの定義
		Scanner sr = new Scanner(System.in);
		//ループの作成
		while(true)
		{
			System.out.println("操作を選択してください：");
			System.out.println("1．正社員の登録");
			System.out.println("2．契約社員の登録");
			System.out.println("3．パート社員の登録");
			System.out.println("4.一覧表示");
			System.out.println("5．終了");
			
			//ユーザの選択を受け取る
			select = sr.nextInt();
			
			//employeeの初期化
			Employee employee = null;
			
			if(select == 5)
			{
				break;
			}
			
			switch(select)
			{
				//正社員
				case 1:
					System.out.println("社員番号：");
					id = sr.nextInt();
					//改行文字のスキップ
					name = sr.nextLine();
					System.out.println("社員名：");
					name = sr.nextLine
							();
					System.out.println("給料：");
					salary = sr.nextDouble();
					
					//FullTimeEmployeeに代入
					employee = new FullTimeEmployee(id, name, salary);
					//addEmployeeを呼び出してデータを登録
					manager.addEmployee(employee);
					System.out.println("正社員を登録しました。");
					break;
				
				//契約社員
				case 2:

					System.out.println("社員番号：");
					id = sr.nextInt();
					//改行文字のスキップ
					name = sr.nextLine();
					System.out.println("社員名：");
					name = sr.nextLine();
					System.out.println("一か月の契約金：");
					contractMoney = sr.nextDouble();
					
					//Contractor
					employee = new Contractor(id, name, contractMoney);
					//addEmployeeを呼び出してデータを登録
					manager.addEmployee(employee);
					System.out.println("契約社員を登録しました。");
					break;
					
				//パートタイム社員	
				case 3:
					System.out.println("社員番号：");
					id = sr.nextInt();
					//改行文字のスキップ
					name = sr.nextLine();
					System.out.println("社員名：");
					name = sr.nextLine();
					System.out.println("時給：");
					hourlyWage = sr.nextDouble();
					System.out.println("1カ月の労働時間：");
					workingHours = sr.nextDouble();
					
					employee = new PartTimeEmployee(id, name, hourlyWage, workingHours);
					manager.addEmployee(employee);
					System.out.println("パートタイム社員を登録しました。");
					break;
					
					
				case 4:
					manager.indicationAll();
					break;
					
				default:
					System.out.println("無効な値です。");
					break;
			}
		}
		
		System.out.println("終了します。");
		sr.close();
	}
}
 