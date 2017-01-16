#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

int main()
{
    int fd, clientfd, newclientfd;
    socklen_t len;
    struct sockaddr_in r, q;

    if ((fd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("socket");
        return(1);
    }

    memset(&r, '\0', sizeof r);
    r.sin_family = AF_INET;
    r.sin_addr.s_addr = INADDR_ANY;
    r.sin_port = htons(3000);

    if (bind(fd, (struct sockaddr *)&r, sizeof r) < 0) {
        perror("bind");
        return(1);
    }
    if (listen(fd, 5)) {
	perror("listen");
	return(1);
    }

    clientfd = -1;
    while (1) {
	len = sizeof q;
	if ((newclientfd = accept(fd, (struct sockaddr *)&q, &len)) < 0) {
	    perror("accept");
	    return(1);
	}
	if (clientfd >= 0) {
	    if (write(clientfd, "goodbye\r\n", 9) != 9)
		perror("write");
	    close(clientfd);
	}
	if (write(newclientfd, "hello\r\n", 7) != 7)
	    perror("write");
	clientfd = newclientfd;
    }
}
