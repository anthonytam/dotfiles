#include <stdio.h>

int main() {
	int x = 9;
	printf("%d\n", x);
	int* addr_x = &x;
	printf("%d\n", addr_x);
	int val = *addr_x;
	printf("%d\n", val);
	*addr_x = 99;
	printf("%d\n", addr_x);
	return 0;
}
