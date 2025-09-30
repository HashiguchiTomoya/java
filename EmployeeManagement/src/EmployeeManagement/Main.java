package EmployeeManagement;

import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		//変数の初期化
		int Choice = 0;				//ユーザーが選択
		String Name = "";			//名前
		int Id = 0;					//ID
		double MonthlySalary = 0;	//月給
		double HourlyRate = 0;		//時給
		double HoursWorked = 0;		//勤務時間
		double ContractAmount = 0;	//契約金
		
		//ユーザーの入力値を取得
		Scanner scanner = new Scanner(System.in);
		
		//情報を格納、表示するためのインスタンス
		EmployeeManager manager = new EmployeeManager();
		
		System.out.println("従業員管理システム");
		
		//０と返されるまでループ
		while(true)
		{
			System.out.println("従業員タイプを選択してください：");
			System.out.println("１．フルタイム社員");
			System.out.println("２．パートタイム社員");
			System.out.println("３．契約社員");
			System.out.println("０．終了して表示");
			
			Choice = scanner.nextInt();
			scanner.nextLine();
			
			if(Choice == 0)
			{
				break;
			}
			
			System.out.println("名前：");
			Name = scanner.nextLine();
			
			System.out.println("ID：");
			Id = scanner.nextInt();
			
			Employee employee = null;
			
			switch(Choice)
			{
			case 1:
				System.out.print("月給：");
				MonthlySalary = scanner.nextDouble();
				employee = new FullTimeEmployee(Name, Id, MonthlySalary);
				break;
			case 2:
				System.out.print("時給：");
				HourlyRate = scanner.nextDouble();
				System.out.print("勤務時間：");
				HoursWorked = scanner.nextDouble();
				employee = new PartTimeEmployee(Name, Id, HourlyRate, HoursWorked);
				break;
			case 3:
				System.out.print("契約金額：");
				ContractAmount = scanner.nextDouble();
				employee = new Contractor(Name, Id, ContractAmount);
				break;
			default:
				System.out.println("無効な選択肢です。");
				continue;
				
			}
			//生成した従業員オブジェクトに追加
			manager.addEmployee(employee);
			System.out.println("従業員を登録しました。");
		}
		
		System.out.println("登録された従業員一覧：");
		//一覧表示
		manager.DisplayAllEmployees();
		//scannerをクローズ
		scanner.close();
	}
}
