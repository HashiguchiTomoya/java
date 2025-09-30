import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import annotations.range;
import annotations.required;

public class validationSystem
{
	private static final Properties config = new Properties();
	
	//configの呼び出し
	static
	{
		try
		{
			config.load(new FileInputStream("config/validation.properties"));
		}
		catch(Exception e)
		{
			System.err.println("設定ファイルの読み込みに失敗しました：" + e.getMessage());
		}
	}
	
	public static List<String> validate(Object obj)
	{
		List<String> errors = new ArrayList<>();
		Class<?> clazz = obj.getClass();
		
		//全フィールドに対してループ
		for(Field field : clazz.getDeclaredFields())
		{
			field.setAccessible(true);
			try
			{
				Object value = field.get(obj);
				
				//@requiredチェック
				if(field.isAnnotationPresent(required.class))
				{
					if(value == null || (value instanceof String && ((String) value).trim().isEmpty()))
					{
						errors.add(field.getName() + "は必須項目です");
					}
				}
				
				//@rangeチェック
				if(field.isAnnotationPresent(range.class))
				{
					range Range = field.getAnnotation(range.class);
					int min = Range.min();
					int max = Range.max();
					
					//外部設定で上書き
					String key = clazz.getSimpleName() + "." + field.getName();
					if(config.containsKey(key + ".min"))
					{
						min = Integer.parseInt(config.getProperty(key + ".min"));
					}
					if(config.containsKey(key + ".max"))
					{
						max = Integer.parseInt(config.getProperty(key + ".max"));
					}
					
					if(value instanceof Integer)
					{
						int intValue = (Integer) value;
						if(intValue < min || intValue > max)
						{
							errors.add(field.getName() + "は範囲外です (" + min +"～" + max + ")");
						}
					}
				}
			}
			catch(IllegalAccessException e)
			{
				errors.add("フィールドアクセスエラー：" + field.getName());
			}
		}
		
		return errors;
	}
	
	//validate()を呼び出して実行
	public static void main(String[] args)
	{
		model.user User = new model.user(null, 150);
		List<String> result = validate(User);
		if(result.isEmpty())
		{
			System.out.println("バリデーション成功");
		}
		else
		{
			System.out.println("バリデーションエラー：");
			result.forEach(System.out::println);
		}
	}
}
