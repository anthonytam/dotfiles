/*
 * q4.c - for each directory specified as command-line argument, recursively
 * find the total byte size of all files in that directory and its
 * subdirectories.
 *
 * Alan J Rosenthal, October 2000.
 */

#include <stdio.h>
#include <string.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/stat.h>

int status = 0;


int main(int argc, char **argv)
{
    int i;
    extern long totalbytes(char *);

    for (i = 1; i < argc; i++)
	printf("%s: %ld\n", argv[i], totalbytes(argv[i]));
    
    return(status);
}


long totalbytes(char *dirname)
{
    DIR *dp;
    struct dirent *p;
    long retval = 0;
    char buf[200];
    int len;
    struct stat statbuf;

    if ((len = strlen(dirname)) + 2 > sizeof buf) {
	fprintf(stderr, "%s: directory name too long\n", dirname);
	status++;
	return(0);
    }

    if ((dp = opendir(dirname)) == NULL) {
	perror(dirname);
	status++;
	return(0);
    }

    sprintf(buf, "%s/", dirname);
    len++;  /* now buf[len] is the first byte after that slash */
    while ((p = readdir(dp))) {
	if (strlen(p->d_name) + len + 1 > sizeof buf) {
	    buf[len] = '\0';
	    fprintf(stderr, "%s%s: filename too long\n", buf, p->d_name);
	    /*
	     * use status=1 instead of status++ in case we have a lot of these
	     */
	    status = 1;
	} else if (strcmp(p->d_name, ".") && strcmp(p->d_name, "..")) {
	    strcpy(buf + len, p->d_name);  /* a concatenation */
	    if (stat(buf, &statbuf)) {
		perror(buf);
		status++;
	    } else if (S_ISDIR(statbuf.st_mode)) {
		retval += totalbytes(buf);
	    } else {
		retval += statbuf.st_size;
	    }
	}
    }

    closedir(dp);
    return(retval);
}
