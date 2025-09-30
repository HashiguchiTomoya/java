package EmployeeManagement;

import java.util.ArrayList;

public class EmployeeManager
{
	//リストの作成
	private ArrayList<Employee> employees = new ArrayList<>();
	
	//リストに追加
	public void addEmployee(Employee e)
	{
		employees.add(e);
	}
	
	//リストの情報を表示させる
	public void DisplayAllEmployees()
	{
		for(Employee e : employees)
		{
			e.displayInfo();
		}
	}
}
