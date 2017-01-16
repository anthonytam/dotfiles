#include <stdio.h>

int main()
{
    char *s = NULL;
    extern void f();
    int i = 12;
    i += 5;
    f(s);
    printf("%s\n", s);
    return(0);
}
