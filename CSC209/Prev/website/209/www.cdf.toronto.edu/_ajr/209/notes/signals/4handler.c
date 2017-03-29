/* this is also silly, but could be less silly if fewer signals were caught */

#include <stdio.h>
#include <unistd.h>
#include <signal.h>

void handler()
{
    printf("in signal handler\n");
}


int main()
{
    int i;
    for (i = 0; i < 256; i++)
	signal(i, handler);
    printf("starting\n");
    sleep(4);
    printf("done\n");
    return(0);
}
