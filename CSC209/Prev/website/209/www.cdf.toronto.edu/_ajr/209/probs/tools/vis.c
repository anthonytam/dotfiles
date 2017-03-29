/*
 * vis.c - turn all "funny" characters in the input into visible strings.
 * Derived from https://cs.utm.utoronto.ca/~ajr/209/notes/toolsfiles/cat1.c
 */

#include <stdio.h>
#include <string.h>


int main(int argc, char **argv)
{
    int i;
    FILE *fp;
    extern void process(FILE *fp);

    if (argc == 1) {
	process(stdin);
    } else {
	for (i = 1; i < argc; i++) {
	    if (strcmp(argv[i], "-") == 0) {
		process(stdin);
	    } else if ((fp = fopen(argv[i], "r")) == NULL) {
		perror(argv[i]);
	    } else {
		process(fp);
		fclose(fp);
	    }
	}
    }
    return(0);
}


void process(FILE *fp)
{
    int c;
    while ((c = getc(fp)) != EOF) {
	if (c >= 128) {
	    printf("M-");
	    c -= 128;
	}
	if (c == 127 || c < ' ' && c != '\n') {
	    putchar('^');
	    c = (c + 64) % 128;
	}
	putchar(c);
    }
}
