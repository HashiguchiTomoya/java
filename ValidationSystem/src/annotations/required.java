package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//独自の付加情報を定義
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface required
{
	
}
