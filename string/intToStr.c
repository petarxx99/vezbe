
int intToStr(int broj, char* str){

if (broj == 0){

*str = 0x30; // Brojevi su pomereni za 0x30 u ASCII

*(str + 1) = 0; // Null karakter zavrsava string.

return 1;

}

int brojCifara = 0;

int brojCopy = broj;

while (brojCopy != 0){

    brojCifara++;

    brojCopy /= 10;

}

bool brojJeNegativan = (broj < 0)? 1 : 0;

brojCopy = (brojJeNegativan)? -broj : broj;

/* Negativni brojevi imaju - na pocetku i zato imaju 1 karakter vise. */

for(int i=0; i<brojCifara; i++){

    *(str + brojCifara-1 - i + brojJeNegativan) = brojCopy % 10 + 0x30;

    brojCopy /= 10;

}

str[brojCifara + brojJeNegativan] = 0; /* Poslednji karakter u stringu
se zavrsava nulom. Ako je broj negativan ima jednu cifru vise za -
znak na pocetku. */

if (brojJeNegativan) str[0] = '-';

return brojCifara;

}
