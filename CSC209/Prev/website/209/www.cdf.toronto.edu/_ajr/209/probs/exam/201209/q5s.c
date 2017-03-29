#include <stdio.h>

char *myfgets(char *buf, int size, FILE *fp)
{
    int pos, c;
    pos = 0;
    while (size - pos > 1) {
	if ((c = getc(fp)) == EOF) {
	    if (pos)
		break;
	    return(NULL);
	}
	buf[pos++] = c;
	if (c == '\n')
	    break;
    }
    buf[pos] = '\0';
    return(buf);
}
