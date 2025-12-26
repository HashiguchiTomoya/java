#include <stdio.h>

int  my_size(const int *first, const int *last)
{
	return (size_t)(last - first);
}

int main(void)
{
	int v[] = {1, 2, 3, 4, 5};

	size_t size1 = my_size(v, v + 5);
	printf("my_size(v, v + 5)の結果:%zu\n", size1);

	size_t size2 = my_size(v + 1, v + 4);
	printf("my_size(v + 1, v + 4)の結果:%zu\n", size2);

	size_t size3 = my_size(v + 2, v + 2);
	printf("my_size(v + 2, v + 2)の結果:%zu\n", size3);

	return 0;
}
