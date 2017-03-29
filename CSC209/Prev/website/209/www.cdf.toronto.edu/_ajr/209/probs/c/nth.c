#include <stdio.h>
#include <unistd.h>
#include <string.h>


static int n;  /* the "n" in "nth" */


int main(int argc, char **argv)
{
    int c, status = 0;
    char garbage;
    FILE *fp;
    extern void process(FILE *fp);

    while ((c = getopt(argc, argv, "")) != EOF) {
	switch (c) {
	case '?':
	default:
	    status = 1;
	    break;
	}
    }

    if (status || optind >= argc
		|| sscanf(argv[optind++], "%d%c", &n, &garbage) != 1) {
	fprintf(stderr, "usage: %s fieldnum [file ...]\n", argv[0]);
	return(1);
    }
    if (n < 1) {
	fprintf(stderr, "%s: field number must be >= 1\n", argv[0]);
	return(1);
    }

    if (optind >= argc) {
	process(stdin);
    } else {
	for (; optind < argc; optind++) {
	    if (strcmp(argv[optind], "-") == 0) {
		process(stdin);
	    } else {
		if ((fp = fopen(argv[optind], "r")) == NULL) {
		    perror(argv[optind]);
		    status = 1;
		} else {
		    process(fp);
		    fclose(fp);
		}
	    }
	}
    }
    return(status);
}


void process(FILE *fp)
{
    char buf[500], *p;
    int i;

    while (fgets(buf, sizeof buf, fp)) {
	for (p = strtok(buf, " \t\n"), i = 1;
		p && i < n;
		p = strtok(NULL, " \t\n"), i++)
	    ;
	if (p)
	    printf("%s", p);
	printf("\n");
    }
}
