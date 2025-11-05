package streamProcessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//従業員クラス
class Employee
{
	//社員番号
	int id = 0;
	//社員名
	String name = "";
	//部署
	String department = "";
	//給料
	int salary = 0;
	
	//コンストラクタの定義
	Employee(int id, String name, String department, int salary)
	{
		this.id = id;
		this.name = name;
		this.department = department;
		this.salary = salary;
	}
	
	//ゲッター
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDepartment()
	{
		return department;
	}
	
	public int getSalary()
	{
		return salary;
	}
}

public class StreamProcessing
{
		
	public static void main(String[] args)
	{
		//従業員リスト
		List<Employee> emp = Arrays.asList(
				new Employee(1, "山田太郎", "管理", 370000),
				new Employee(2, "田中たけし" ,"事務", 340000),
				new Employee(3, "松本拓朗" , "管理", 300000),
				new Employee(4, "後藤ー郎" , "営業", 300000),
				new Employee(5, "本田花子" , "営業", 260000),
				new Employee(6, "西田みなみ", "事務", 240000)
				);
		
		//フィルタリングとマッピング
		//Streamの取得
		System.out.println("---給与が30万円以上の社員---");
		Stream<Employee> sm = emp.stream();
		//fillterメソッドによりsalaryが30万以上の要素を取得し、mapメソッドにより、絞り込まれた要素を変換
		Stream<Object> sm2 = sm.filter(e -> e.getSalary() >= 300000).map(e -> e.getName());
		//引数で指定された処理を実行
		sm2.forEach(e -> System.out.println(e));
		
		//グループ化
		System.out.println("\n---部署ごとに表示---");
		//EmployeeリストをStreamに変換しmapを生成
		Map<String, List<Employee>> eDepartment = emp.stream()
				//getDepartmentを呼び出し戻り値をキーをグループ化
				.collect(Collectors.groupingBy(Employee::getDepartment));
		
		//mapの各キーに対してラムダ式の処理を繰り返し行う
		eDepartment.forEach((department, empList) ->
		{
			System.out.println("部署:" + department);
			
			//各部署の従業員に処理を行う
			empList.forEach(e -> System.out.println(" "+ e.getId() + " " + e.getName() + " " + e.getSalary() + "円"));
		});
		
		//部署ごとの給与集計
		System.out.println("\n---部署ごとの集計---");
		//EmployeeリストをStreamに変換しmapを生成
		Map<String, Integer> tDepartment = emp.stream()
				//getDepartmentを呼び出し戻り値をキーをグループ化
				.collect(Collectors.groupingBy(Employee::getDepartment,
						//getSalaryを呼び出して合計する
						Collectors.summingInt(Employee::getSalary)));
		
		//mapの各キーに対してラムダ式の処理を繰り返し行う
		tDepartment.forEach((department, totalsalary) ->
		
			System.out.println("部署:" + department + " 給与合計:" + totalsalary + "円")
		);
		
		//1から1億までの数値からなるListの作成
		final List<Integer> list = new ArrayList<>();
		for(int i = 0; i <= 1000000000; i++)
		{
			list.add(i);
		}
		
		long startTime = 0;
		long endTime = 0;
		
		System.out.println("forの結果:");
		startTime = System.currentTimeMillis();;
		long sumFor = 0;
		//全てのデータを１つずつ取り出して処理
		for(Integer data : list)
		{
			//3の倍数かを判定
			if(data % 3 == 0)
			{
				//リストから3の倍数だけ足し合わせる
				sumFor += data;
			}
		}
		System.out.println(sumFor);
		//協定世界時間をミリ秒多単位で代入
		endTime = System.currentTimeMillis();
		//処理時間を計算して出力
		System.out.println("処理時間:" + (endTime - startTime) + "ms");
		
		System.out.println("\nstreamの結果:");
		long sumStream = 0;
		startTime =System.currentTimeMillis();
		//複数のスレッドで同時に並列処理
		sumStream = list.parallelStream()
				//3の倍数を絞り込む
				.filter(data1 -> data1 % 3 == 0)
				//Integer型からLong型に
				.mapToLong(data1 -> data1)
				//合計
				.sum();
		System.out.println(sumStream);
		endTime = System.currentTimeMillis();
		System.out.println("処理時間:" + (endTime - startTime) + "ms");
	}
}
