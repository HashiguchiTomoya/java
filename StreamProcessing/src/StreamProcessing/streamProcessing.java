package StreamProcessing;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//従業員の情報を保持
class Employee
{
	String name = "";
	int age = 0;
	double salary = 0;
	String department = "";
	
	Employee(String name, int age, double salary, String department)
	{
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.department = department;
	}
	
	@Override
	public String toString()
	{
		return String.format("%s (%d) - %.0f[%s]", name, age, salary, department);
	}
}


public class streamProcessing
{
	public static void main(String[] args)
	{
		//従業員データ
		List<Employee> employees = Arrays.asList(
			new Employee("田中", 30, 500000, "営業"),
			new Employee("佐藤", 45, 800000, "開発"),
			new Employee("鈴木", 28, 450000, "営業"),
			new Employee("高橋", 35, 700000, "開発"),
			new Employee("伊藤", 50, 900000, "人事"),
			new Employee("中村", 40, 600000, "営業")
		);
		
		//集計処理
		System.out.println("=====集計処理=====");
		double totalSalary = employees.stream()
			.mapToDouble(e -> e.salary)
			.sum();
		double avgAge = employees.stream()
			.mapToInt(e -> e.age)
			.average()
			.orElse(0);
		System.out.printf("給与合計: %,0f円\n", totalSalary);
		System.out.printf("平均年齢: %.1f歳\n",avgAge);
		
		//フィルタリング＆マッピング（高収入を抽出）
		System.out.println("\n=====フィルタリング&マッピング=====");
		List<String> highEarners = employees.stream()
				.filter(e -> e.salary > 600000)
				.map(e -> e.name)
				.collect(Collectors.toList());
		System.out.println("高収入従業員:" + highEarners);
		
		//グルーピング処理
		System.out.println("\n=== 複雑なグルーピング処理 ===");
        Map<String, Map<String, List<Employee>>> grouped =
            employees.stream()
                .collect(Collectors.groupingBy(
                    e -> e.department,
                    Collectors.groupingBy(e -> e.salary > 600000 ? "高収入" : "通常")
                ));

		grouped.forEach((dept, incomeMap) -> {
			System.out.println("部署:" + dept);
			incomeMap.forEach((incomeLevel, list) ->{
				System.out.println("  " + incomeLevel + ": " + list);
			});
		});
		
		//パフォーマンス比較（forとStream）
		System.out.println("\n=====パフォーマンス比較=====");
		int iterations = 100000;
		List<Employee> largeList = IntStream.range(0, iterations)
				.mapToObj(i -> new Employee("従業員" + i, 25 + (i % 30), 400000 + (i % 500000), i % 2 == 0 ? "開発" : "営業"))
				.collect(Collectors.toList());
		
		long startTraditional = System.nanoTime();
		double totalTraditional = 0;
		for (Employee e : largeList)
		{
			if(e.salary > 600000)
			{
				totalTraditional += e.salary;
			}
		}
		long endTraditional = System.nanoTime();
		
		long startStream = System.nanoTime();
		double totalStream = largeList.stream()
			.filter(e -> e.salary > 600000)
			.mapToDouble(e -> e.salary)
			.sum();
		long endStream = System.nanoTime();
		
		//出力
		System.out.println("Total (Traditional): " + totalTraditional);
		System.out.println("Total (Stream): " + totalStream);
		
		System.out.printf("従来方式: %2f ms\n", (endTraditional - startTraditional) / 1_000_000.0);
		System.out.printf("Stream方式: %2f ms\n", (endStream - startStream) / 1_000_000.0);
	}
}
