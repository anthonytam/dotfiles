#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <dirent.h>
#include <sys/types.h>
#include <sys/wait.h>

#define CATMAN "/usr/share/catman"
#define LESS "/usr/bin/less"

extern char *find(int manno, char *name);
extern void man(int manno, char *filename);


int main(int argc, char **argv)
{
    int i;
    char *p;

    for (argc--, argv++; argc > 0; argc--, argv++) {
        for (i = 1; i <= 8 && (p = find(i, *argv)) == NULL; i++)
            ;
        if (p)
            man(i, p);
        else
            fprintf(stderr, "No manual entry for %s\n", *argv);
    }
    return(0);
}


char *find(int manno, char *name)
{
    char dirname[100];
    DIR *dp;
    struct dirent *r;
    extern int ismatch(char *name, char *filename);

    sprintf(dirname, "%s/man%d", CATMAN, manno);
    if ((dp = opendir(dirname)) == NULL) {
        perror(dirname);
        exit(1);
    }
    while ((r = readdir(dp)) && !ismatch(name, r->d_name))
        ;
    closedir(dp);
    return(r ? r->d_name : NULL);
}


int ismatch(char *name, char *filename)
{
    int len = strlen(name);
    return(strncmp(name, filename, len) == 0 && filename[len] == '.');
}


void man(int manno, char *filename)
{
    /* max dirname size is <100, and max filename size in bsd fsys is 256 */
    char fullpath[356];
    int pid = fork();
    if (pid == -1) {
        perror("fork");
        exit(1);
    } else if (pid == 0) {
        sprintf(fullpath, "%s/man%d/%s", CATMAN, manno, filename);
        execl(LESS, "less", fullpath, (char *)NULL);
        perror(LESS);
        exit(1);
    } else {
        if (wait((int *)NULL) < 0)
            perror("wait");
    }
}
