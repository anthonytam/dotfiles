/* three different approaches to determining endianism */

#include <stdio.h>
#include <netinet/in.h>  /* for ntohs() */


int main()
{
    int i = 38;

    printf("Approach 1: ");
    if (i == ntohs(i))
	printf("big-endian\n");
    else
	printf("little-endian\n");

    printf("Approach 2: ");
    if (((char *)&i)[0] == 0)
	printf("big-endian\n");
    else
	printf("little-endian\n");

    printf("Approach 3: ");
    i = ('1' << 24) | ('2' << 16) | ('3' << 8) | '4';
    printf("Byte order is %.4s\n", (char *)&i);

    return(0);
}
