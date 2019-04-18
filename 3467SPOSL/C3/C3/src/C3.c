/*
 ============================================================================
 Name        : C3.c
 Author      : Akshay ubale
 Version     :
 Copyright   : Your copyright notice
 Description : Hello World in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>


int main(void) {
	do{
		printf("1.process status\n2.fork\n3.join\n4.wait\n");
		int num=0,pid,status;
		pid_t cpid;
		scanf("%d",&num);
		switch(num){
		case 1:
			execl("/bin/ps", "/bin/ps", "-l", (char *) 0);
			break;
		case 2:
			pid = fork();
			if(pid==0){
				printf("the child id is %d",getpid());
			}
			else{
				printf("the parent id is %d",getpid());
			}
			break;
		case 3:
			//execl("/bin/pwd","/bin/pwd",(char*) 0);
			execl("/bin/join","/bin/join","f1.txt","f2.txt",(char *) 0);
			break;
		case 4:

			if (fork()== 0){
				sleep(5);
				exit(0);   /* terminate child */
			}
			else{
				cpid = wait(&status); /* reaping parent */
				printf("%d",WIFEXITED(status));
			}
			printf("Parent pid = %d\n", getpid());
			printf("Child pid = %d\n", cpid);

			break;

		}
	}while(1);
	return EXIT_SUCCESS;
}

