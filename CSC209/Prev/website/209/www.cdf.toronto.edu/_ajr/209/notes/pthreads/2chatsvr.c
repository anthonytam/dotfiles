#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>  /* for memset() */
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <pthread.h>

int nclients = 0;
int clientfds[100];


int main()
{
    int listenfd, newfd;
    struct sockaddr_in r;
    socklen_t len;
    pthread_t t;
    extern void *beclient(void *arg);

    if ((listenfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("socket");
        return(1);
    }
    memset(&r, '\0', sizeof r);
    r.sin_family = AF_INET;
    r.sin_addr.s_addr = INADDR_ANY;
    r.sin_port = htons(1234);
    if (bind(listenfd, (struct sockaddr *)&r, sizeof r)) {
        perror("bind");
        exit(1);
    }
    if (listen(listenfd, 5)) {
        perror("listen");
        exit(1);
    }

    while (1) {
        len = sizeof r;
        if ((newfd = accept(listenfd, (struct sockaddr *)&r, &len)) < 0) {
            perror("accept");
        } else {
            clientfds[nclients] = newfd;
            pthread_create(&t, NULL, beclient, &clientfds[nclients]);
            nclients++;
        }
    }
}


void *beclient(void *arg)
{
    int fd = *(int *)arg;
    int i, nbytes;
    char buf[1000];  /* should be MAXMESSAGE or something */

    static char greeting[] = "chatsvr blah blah\r\n";
    printf("new connection, fd %d\n", fd);
    write(fd, greeting, sizeof greeting - 1);

    while ((nbytes = read(fd, buf, sizeof buf)) > 0)
        for (i = 0; i < nclients; i++)
            if (clientfds[i] >= 0)
                write(clientfds[i], buf, nbytes);

    printf("lost connection %d\n", fd);
    close(fd);
    *(int *)arg = -1;
    return(NULL);
}
