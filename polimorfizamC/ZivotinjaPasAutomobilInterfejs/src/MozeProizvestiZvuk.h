#ifndef MOZE_PROIZVESTI_ZVUK_H
#define MOZE_PROIZVESTI_ZVUK_H


typedef struct MozeProizvestiZvukStruct{
    void (*proizvediZvuk)(void *strukturaKojuMetodeInterfejsaKoriste);
    unsigned int (*getKolikoPutaJeProizveoZvuk)(void *strukturaKojaImplementiraInterfejs);
    void *strukturaKojuMetodeInterfejsaKoriste;
}MozeProizvestiZvuk;

#endif



