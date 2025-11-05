package validationSystem;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

//これが付加されたアノテーションをほかのプログラムでも参照できる
@Documented
//
@Constraint(validatedBy = {ValidationSystem.class})
//アノテーションの保持期間を指定(実行時にも保持される)
@Retention(RetentionPolicy.RUNTIME)
//アノテーションの適用場所を指定
@Target({
	//フィールド宣言に適応可能
	ElementType.TYPE,
	//アノテーションを引数やメソッドに適応可能
	ElementType.PARAMETER
})
public @interface Required
{
	String massage() default "パスワードを入力してください。";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default{};
	
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public static @interface Range
	{
		int min();
		int max();
	}
}

