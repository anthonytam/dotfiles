/*
 * This is to be added to builtin.c.  You also have to add a call to this
 * builtin along with the rest of 'em in execute.c.
 * And you'd have to add the #include of <sys/types.h> and <signal.h> at the
 * top of builtin.c.
 */


int builtin_kill(char **argv)
{
    extern int really_builtin_kill(char **argv);  /* except for usage message */
    int status = really_builtin_kill(argv);
    if (status == 1)
        fprintf(stderr, "usage: kill [-signal] pid ...\n");
    return(status);
}


/*
 * really_builtin_kill() does all the work of builtin_kill except for the
 * usage message.  Its return status is 0 for ok, 1 for usage error, 2 for
 * kill error.
 */
int really_builtin_kill(char **argv)
{
    int sig = 15;
    char *end;
    int status = 0;

    /* get optional signal number, and leave argv pointing at first pid */
    if (argv[1] == NULL)
        return(1);
    argv++;
    if (**argv == '-') {
        sig = (int)strtol(*argv + 1, &end, 10);
        if (end == *argv + 1 || *end)
            return(1);
        argv++;
    }

    /* process one or more pids */
    if (*argv == NULL)
        return(1);
    for (; *argv; argv++) {
        long pid = strtol(*argv, &end, 10);
        if (end == *argv || *end)
            return(1);
        if (kill(pid, sig)) {
            perror("kill");
            status = 2;
        }
    }

    return(status);
}
