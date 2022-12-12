#include"IncludeFajl.h"


char *readFile(char *fileName){
    FILE *file = fopen(fileName, "r");
    char *buffer = (char*) malloc(FOUR_KB);
    char c;
    int i=0;
    while((c=fgetc(file)) != EOF){
        i++;
        if(i % FOUR_KB == 0){
            buffer = realloc(buffer, (i / FOUR_KB + 1) * FOUR_KB);
        }
        buffer[i-1] = c;
    }
    buffer[i] = 0;
    fclose(file);
    return buffer;
}


void readChars(char *buffer, int numberOfCharsToRead, FILE *file){
    int i;
    for(i=0; i<numberOfCharsToRead; i++){
        char c = getc(file);
        buffer[i] = c;
        if(c==0 || c==END_OF_LINE_CHARACTER) break;
    }
    buffer[i] = 0;
}
