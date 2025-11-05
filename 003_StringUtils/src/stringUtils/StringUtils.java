package stringUtils;

public class StringUtils
{
	//文字数カウント
	public static int wordsCount(String input)
	{
		//全角スペースを半角スペースに変換
		input = input.replaceAll("　", " ");
		//半角スペースを空文字に変換
		input = input.replaceAll(" ", "");
		StringBuilder sb = new StringBuilder(input);
		int count = sb.length();
		
		return count;
	}

	//文字列反転
	public static String inversion(String input)
	{
		String inver = new StringBuilder(input).reverse().toString();
		return inver;
	}
	
	//回文判定
	public static boolean judgment(String input)
	{
		String reverse = inversion(input);
		return input.equals(reverse);
	}
	
	//単語数カウント
	public static int charCount(String input)
	{
		input = input.replaceAll("　", " ");
		int count = input.split("\\s").length;
		return count;
		
	}
	
	public static void main(String[] args)
	{
		String test1 = "し ん ぶ ん し";
		String test2 = "と　ま　と";
		String test3 = "がくしゅうづくえ";
		
		//test1
		System.out.printf("%s\n", test1);
		System.out.println("文字数：" + wordsCount(test1));
		System.out.println("回文：" + inversion(test1));
		System.out.println("回文判定："+ judgment(test1));
		System.out.println("単語数カウント：" + charCount(test1));
		
		//test2
		System.out.printf("%s\n", test2);
		System.out.println("文字数：" + wordsCount(test2));
		System.out.println("回文：" + inversion(test2));
		System.out.println("回文判定：" + judgment(test2));
		System.out.println("単語数カウント：" + charCount(test2));
		
		//test3
		System.out.printf("%s\n", test3);
		System.out.println("文字数：" + wordsCount(test3));
		System.out.println("回文：" + inversion(test3));
		System.out.println("回文判定：" + judgment(test3));
		System.out.println("単語数カウント：" + charCount(test3));
		
	}
}
