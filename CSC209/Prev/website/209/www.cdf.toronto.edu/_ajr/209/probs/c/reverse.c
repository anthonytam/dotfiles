#include <stdio.h>

int main()
{
    char buf[82];

    if (fgets(buf, sizeof buf, stdin)) {
        main();
        fputs(buf, stdout);
    }
    return(0);
}
