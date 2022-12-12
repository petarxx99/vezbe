#include"IncludeFajl.h"
#include"StringBuilder.h"
#include"VectorString.h"
#include"inputOutput.h"



int isALetter(char c){
    if(c >= LOWEST_ASCII_LOWER_CASE_LETTER && c <= HIGHEST_ASCII_LOWER_CASE_LETTER){
        return 1;
    }

    if(c>= LOWEST_ASCII_UPPER_CASE_LETER && c<= HIGHEST_ASCII_UPPER_CASE_LETTER){
        return 1;
    }

    return 0;
}



char* getFileNameFromUser(){
     const int MAX_NUMBER_OF_CHARACTERS = 30;
     char *fileName = (char*) malloc(sizeof(char) * (MAX_NUMBER_OF_CHARACTERS+1));

     printf("Please enter the name of the file whose longest words per line you want to know: ");
     readChars(fileName, MAX_NUMBER_OF_CHARACTERS, stdin);
     return fileName;
}

void askAndTellTheUserTheLongestWordOfEachLine(char* (*getFileNameFromUser)(),
                                  char* (*getFileContent)(char *fileName),
                                  VectorString* (*getLongestWordsOfEachLine)(char *text),
                                  void (*giveUserLongestWordsOfEachLine)(VectorString *longestWordsOfEachLine)){

    char *fileName = getFileNameFromUser();
    char *text = getFileContent(fileName);
    VectorString *longestWordOfEachLine = getLongestWordsOfEachLine(text);
    giveUserLongestWordsOfEachLine(longestWordOfEachLine);
    free(fileName);
    free(text);
}


int main(void)
{
    askAndTellTheUserTheLongestWordOfEachLine(getFileNameFromUser, readFile, getLongestWordsInEachLine, VectorString_printAll);
    return 0;
}
