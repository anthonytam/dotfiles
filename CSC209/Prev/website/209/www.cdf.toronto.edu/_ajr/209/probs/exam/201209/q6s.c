/* Here is an algorithm for this problem which you might find interesting. */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/stat.h>

int main()
{
    extern void rfind(int depth);
    rfind(0);
    printf("not found\n");
    return(0);
}


void rfind(int depth)
{
    DIR *dp;
    struct dirent *r;
    struct stat statbuf;

    if ((dp = opendir(".")) == NULL) {
	perror("opendir");
	exit(1);
    }
    while ((r = readdir(dp))) {
	if (strcmp(r->d_name, "squid") == 0) {
	    printf("%d\n", depth);
	    exit(0);
	}
	if (lstat(r->d_name, &statbuf)) {
	    perror(r->d_name);
	    exit(1);
	}
	if (S_ISDIR(statbuf.st_mode)
		&& strcmp(r->d_name, ".")
		&& strcmp(r->d_name, "..")) {
	    if (chdir(r->d_name)) {
		perror(r->d_name);
		exit(1);
	    }
	    rfind(depth+1);
	    if (chdir("..")) {
		perror("..");
		exit(1);
	    }
	}
    }
    closedir(dp);
}
