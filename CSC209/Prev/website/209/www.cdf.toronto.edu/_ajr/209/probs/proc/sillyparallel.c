#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>


int main()
{
    int sum, i, status;
    extern void doit(int i);

    for (i = 0; i < 5; i++)
        if (fork() == 0)
            doit(i);
    for (sum = i = 0; i < 5; i++) {
        wait(&status);
        sum += (status >> 8);
    }
    printf("%d\n", sum);
    return(0);
}


void doit(int i)
{
    int j, sum = 0;
    i *= 5;
    for (j = 0; j < 5; j++)
        sum += i + j;
    _exit(sum);
}
