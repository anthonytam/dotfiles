#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>  /* for memset() */
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <pthread.h>

/* compile with a4/util.c for extractline() */

int nclients = 0;
int clientfds[100];


int main(int argc, char **argv)
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
    int bytes_in_buf = 0;
    extern char *extractline(char *s, int size);

    static char greeting[] = "chatsvr blah blah\r\n";
    printf("new connection, fd %d\n", fd);
    write(fd, greeting, sizeof greeting - 1);

    while ((nbytes = read(fd, buf + bytes_in_buf, sizeof buf - 1 - bytes_in_buf)) > 0) {
        bytes_in_buf += nbytes;
        char *p;
        while ((p = extractline(buf, bytes_in_buf))) {
            int messagesize = strlen(buf);
            for (i = 0; i < nclients; i++) {
                if (clientfds[i] >= 0) {
                    write(clientfds[i], buf, messagesize);
                    write(clientfds[i], "\r\n", 2);
                }
            }
            bytes_in_buf -= p - buf;
            memmove(buf, p, bytes_in_buf);
        }
    }

    printf("lost connection %d\n", fd);
    close(fd);
    *(int *)arg = -1;
    return(NULL);
}
