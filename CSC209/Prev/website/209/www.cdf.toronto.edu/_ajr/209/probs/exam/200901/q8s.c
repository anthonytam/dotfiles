#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>


int main()
{
    int count = 0;
    struct sockaddr_in r, q;
    int listenfd, clientfd;

    if ((listenfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("socket");
        exit(1);
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
        char buf[30];
        socklen_t len = sizeof q;
        if ((clientfd = accept(listenfd, (struct sockaddr *)&q, &len)) < 0) {
            perror("accept");
            return(1);
        }
        sprintf(buf, "%d\r\n", ++count);
        write(clientfd, buf, strlen(buf));
        close(clientfd);
    }
}
