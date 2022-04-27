void nadoveziString(char* str1, char* str2, char* novString){

  int i=0;
  while(str1[i] > 0){
      novString[i] = str1[i];
      i++;
  }

  int j=0;
  while(str2[j] > 0){
      novString[i] = str2[j];
      i++;
      j++;
  }

  novString[i] = 0;

}
