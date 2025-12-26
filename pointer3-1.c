#include <stdio.h>
#include <stdlib.h>


struct Hoge
{
	int m_value;
};

void print(const struct Hoge *ptr)
{
	//もしptrが何かを指している場合
	if(ptr != NULL)
	{
		//ポインター経由でm_valueにアクセス
		printf("%d\n", ptr -> m_value);
	}
}

int main(void)
{
	struct Hoge *ptr = (struct Hoge *)malloc(sizeof(struct Hoge));

	//mallocが失敗した場合のチェック
	if(ptr == NULL)
	{
		return 1;
	}

	//m_valueに123を代入
	ptr -> m_value = 123;

	//point()を呼び出して123が表示されることを確認
	print(ptr);

	//使い終わったメモリは解放
	free(ptr);

	return 0;
}
