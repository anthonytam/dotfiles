/* simple cmp */

#include <stdio.h>


int main(int argc, char **argv)
{
    FILE *fp1, *fp2;
    int c, d, i;

    if (argc != 3) {
        fprintf(stderr, "usage: cmp file1 file2\n");
        return(2);
    }

    if ((fp1 = fopen(argv[1], "r")) == NULL) {
        perror(argv[1]);
        return(2);
    }
    if ((fp2 = fopen(argv[2], "r")) == NULL) {
        perror(argv[2]);
        return(2);
    }

    /* read until EOF on either file OR a different char in the two files */
    for (i = 1;
            c = getc(fp1), d = getc(fp2), c != EOF && d != EOF && c == d;
            i++)
        ;

    if (c == EOF && d == EOF)
        return(0);
    if (c == EOF) {
        printf("eof on %s\n", argv[1]);
        return(1);
    }
    if (d == EOF) {
        printf("eof on %s\n", argv[2]);
        return(1);
    }
    printf("%s %s differ: char %d\n", argv[1], argv[2], i);
    return(1);
}
