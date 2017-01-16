#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

int main()
{
    int x, y;
    extern void dofork(int i, int *fd);
    extern int readint(int fd);
    dofork(12, &x);
    dofork(49, &y);
    printf("%d\n", readint(x) + readint(y));
    return(0);
}

void dofork(int i, int *fd)
{
    int pipefd[2];
    if (pipe(pipefd)) {
	perror("pipe");
	exit(1);
    }
    switch (fork()) {
    case -1:
	perror("fork");
	exit(1);
    case 0:
	if (write(pipefd[1], &i, sizeof i) != sizeof i) {
	    perror("write");
	    exit(1);
	}
	exit(0);
    default:
	*fd = pipefd[0];
    }
}

int readint(int fd)
{
    int x;
    if (read(fd, &x, sizeof x) != sizeof x) {
	fprintf(stderr, "read problems\n");  /* good enough for a test... */
	exit(1);
    }
    return(x);
}
