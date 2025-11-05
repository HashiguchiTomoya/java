package employeeManager;

import java.util.ArrayList;

public class EmployeeManager
{
	//リストの宣言
	private ArrayList<Employee> employees = new ArrayList<>(); 
	
	//リストに登録するメソッド
	public void addEmployee(Employee employee)
	{
		employees.add(employee);
	}

	//登録されたデータを順番に表示
	public void indicationAll() 
	{
		for(Employee employee: employees )
		{
			//indicationを呼び出して表示
			employee.indication();
		}
	}
}
