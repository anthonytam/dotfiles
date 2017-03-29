/*
 * cmdline.c - a version of main.c which expects the two file names to be
 *             specified on the unix command-line, instead of prompting for
 *             and inputting them.
 *
 * Alan J Rosenthal, November 2000.
 */

#include <stdio.h>

int main(int argc, char **argv)
{
    FILE *in, *out;
    extern void sortfile(FILE *in, FILE *out);

    if (argc != 3) {
	fprintf(stderr, "usage: %s infile outfile\n", argv[0]);
	return 1;
    }

    /* open files */
    if ((in = fopen(argv[1], "r")) == NULL) {
	perror(argv[1]);
	return 1;
    }
    if ((out = fopen(argv[2], "w")) == NULL) {
	perror(argv[2]);
	return 1;
    }

    /* call sortfile on 'em; files closed upon program exit */
    sortfile(in, out);

    return 0;
}
