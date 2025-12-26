#include <stdio.h>

void my_strcpy(char *dst, const char *src)
{
	while(*src != '\0')
	{
		*dst = *src;
		dst++;
		src++;
	}
	*dst = '\0';
}

int main(void)
{
	char dst[10];
	char *src = "abc123";

	my_strcpy(dst, src);

	printf("dst:%s\n", dst);

	return 0;
}
