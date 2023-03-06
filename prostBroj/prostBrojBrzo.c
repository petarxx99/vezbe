#include<stdio.h>
#include<stdlib.h>

unsigned int* prostiBrojevi;
unsigned int brojProstih;

void prostBroj(unsigned int broj){

   unsigned int log2_broj = 0;

   unsigned int brojCopy = broj;

   while (brojCopy > 1){
     log2_broj++;
     brojCopy = brojCopy >> 1;
   }

   unsigned int deli = log2_broj >> 1;

   unsigned int granica = broj >> deli;

   unsigned int i = 0;
   unsigned int trenutniDelilac;
   while (1){
         trenutniDelilac = prostiBrojevi[i];
         if (trenutniDelilac >= granica) break;
         if (trenutniDelilac == 0) break;

         if(broj % trenutniDelilac == 0){
           return;  // return 0;
         }
         i += 1;
   }

   prostiBrojevi[brojProstih] = broj;
   brojProstih++;
   return;   // return 1;
 }

int main(){

  unsigned int dokle;
  printf("Do kog broja treba da izracunam proste brojeve (the number of primes up to): ");
  scanf("%u", &dokle);
  prostiBrojevi = (unsigned int*) calloc(sizeof(unsigned int), dokle*4/10);
  prostiBrojevi[0] = 2;

  brojProstih = 1;

  for(unsigned int i = 3; i < dokle; i+=2){
      prostBroj(i);
  }

  printf("Broj prostih brojeva od 1 do %u je %u.\nThe number of primes up to %u is %u.\n",
  dokle, brojProstih, dokle, brojProstih);
  free(prostiBrojevi);


  return 0;
}
