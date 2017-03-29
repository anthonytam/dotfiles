#include <stdio.h>

int main(int argc, char **argv)
{
    int x, y;
    if (argc != 3) {
	fprintf(stderr, "usage: test x y\n");
	return(3);
    }
    x = atoi(argv[1]);
    y = atoi(argv[2]);
    if (x < y)
	return(0);
    else if (x == y)
	return(1);
    else
	return(2);
}
