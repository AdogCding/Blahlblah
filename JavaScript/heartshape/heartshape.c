#include <stdio.h>
#include <math.h>
#include <string.h>
#include <stdlib.h>

void heart1(){
	// draw heart shape one
	for(float y = 1.5f; y >= -1.5f; y-= 0.1f){
		for(float x = -1.5f; x < 1.5f; x += 0.05f){
			float a = x * x + y * y - 1;
			putchar(a*a*a - x*x*y*y*y <= 0.0? '*':' ');
		}
		putchar('\n');
	}
}

void heart2(char *str) {
	// draw heart shape which content is str
	double x, y, res, var;
	const double x_bound = 2.1;
	const double y_bound = -1.3;
	const double y_step = 0.08;
	const double x_step = 0.025;
	int index;
	for(y = 1.5; y > y_bound; y -= y_step){
		index = 0;
		for(x = -1.5; x < x_bound; x += x_step){
			var = 5.0*y/4.0 - sqrt(fabs(x));
			res = x*x + var*var - 1;
			putchar(res <= 0? str[index++%strlen(str)]:' ');
		}
		putchar('\n');
	}
}
void usageError(){
	printf("Usage: [string]\n");
	exit(1);
}
int main(int argc, char *argv[]){
	char *res;
	int length = 0;
	switch(argc){
		case 1:
			//abnormal
			usageError();
			break;
		default:
		//cat strings
			for(int i = 1; i < argc; i++){
				length += strlen(argv[i]);
			}
			//note: extra bytes for whitespace
			res = malloc(length*sizeof(char)+argc-1);
			for(int i = 1; i < argc; i++){
				strncat(res, argv[i], strlen(argv[i]));
				strncat(res, " ", 1);
			}
			// print heart shape
			heart2(res);
			putchar('\n');
	}
}