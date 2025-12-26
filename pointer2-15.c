#include <stdio.h>

int my_accumulate(const int *first, const int *last)
{
	int sum = 0;
	while(first < last)
	{
		sum += *first;
		first ++;
	}

	return sum;
}

int main(void)
{
	int v1[] = {1, 2, 3, 4, 5};
	printf("case1:%d\n", my_accumulate(v1, v1 + 5));

	int v2[] = {1, 2, 2, 3, 3};
	printf("case2:%d\n", my_accumulate(v2, v2 + 5));

	int v3[] = {2, 2, 2, 2, 2};
	printf("case3:%d\n", my_accumulate(v3 + 1, v3 +4));

	printf("case4:%d\n", my_accumulate(v1, v1));

	return 0;
}
