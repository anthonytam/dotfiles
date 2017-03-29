/*
 * I think that just counting the depth is way easier than printing the full
 * path name.  But here's a version which uses the chdir() strategy from q6s.c
 * and prints the full path name in an interesting way without malloc(), by
 * using only local ("auto") variables, but such that none of the pointers are
 * used after the scopes declaring those auto variables cease to exist.
 * It's also relying on the guarantee that r->d_name is not reused for the
 * same directory stream, else we'd have to stash the name.
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/stat.h>

struct pathname {
    char *name;
    struct pathname *next;
};

int main()
{
    struct pathname pn;
    extern void rfind(struct pathname *pn);
    pn.next = NULL;
    pn.name = ".";
    rfind(&pn);
    printf("not found\n");
    return(0);
}


void rfind(struct pathname *pn)
{
    struct pathname newpn;
    DIR *dp;
    struct dirent *r;
    struct stat statbuf;
    extern void rprint(struct pathname *pn);

    newpn.next = pn;
    if ((dp = opendir(".")) == NULL) {
	perror("opendir");
	exit(1);
    }
    while ((r = readdir(dp))) {
	if (strcmp(r->d_name, "squid") == 0) {
	    rprint(pn);
	    printf("%s\n", r->d_name);
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
	    newpn.name = r->d_name;
	    rfind(&newpn);
	    if (chdir("..")) {
		perror("..");
		exit(1);
	    }
	}
    }
    closedir(dp);
}


void rprint(struct pathname *pn)
{
    if (pn) {
	rprint(pn->next);
	printf("%s/", pn->name);
    }
}
