/*
 * back1.c - decrease a file's modify time by one hour
 *
 * Alan J Rosenthal, 30 March 1988
 */

#include <stdio.h>
#include <sys/types.h>
#include <sys/time.h>
#include <sys/stat.h>

#define ONEHOUR (60 * 60)


int main(int argc, char **argv)
{
    struct stat statbuf;
    struct timeval times[2];
    int status = 0;

    if (argc < 2) {
	fprintf(stderr, "usage: %s file ...\n", argv[0]);
	return(1);
    }

    times[0].tv_usec = times[1].tv_usec = 0;

    while (--argc > 0) {
	argv++;
	if (stat(*argv, &statbuf)) {
	    perror(*argv);
	    status++;
	} else {
	    times[0].tv_sec = times[1].tv_sec = statbuf.st_mtime - ONEHOUR;
	    utimes(*argv, times);
	}
    }

    return(status);
}
