/*
 * outputlimiter - run a program with an output line limit, printing a
 *		student-oriented message if the limit is reached.
 * Alan J Rosenthal, 4 November 2000.
 */

#include <stdio.h>


int main(int argc, char **argv)
{
    FILE *fp;
    int line = 0, col = 0, c;

    if (argc != 2) {
	fprintf(stderr, "usage: outputlimiter command\n");
	fprintf(stderr, "       The entire command should be in argv[1] -- it is passed to popen().\n");
	return(1);
    }

    /*
     * merge stderr and stdout to stdout.  This isn't really for my own
     * benefit, it's to be inherited by the child process.
     */
    (void)dup2(1, 2);

    if ((fp = popen(argv[1], "r")) == NULL) {
	perror(argv[1]);
	return(1);
    }
    while ((c = getc(fp)) != EOF) {
	putchar(c);
	if (c == '\n' || ++col == 80) {
	    line++;
	    col = 0;
	}
	if (line > 60) {
	    if (col)
		putchar('\n');
	    printf("\nFurther output lines discarded.\n");
	    exit(1);
	}
    }

    if (col != 0)
	putchar('\n');

    return(0);
}
