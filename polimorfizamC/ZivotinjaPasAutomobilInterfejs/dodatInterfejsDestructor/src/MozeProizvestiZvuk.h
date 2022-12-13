#ifndef MOZE_PROIZVESTI_ZVUK_H
#define MOZE_PROIZVESTI_ZVUK_H


typedef struct MozeProizvestiZvukStruct{

    void (*primiPorukuDaJeInstancaInterfejsaObrisana)(void *strukturaKojaImplementiraInterfejs, void *_this);

    void (*proizvediZvuk)(void *strukturaKojuMetodeInterfejsaKoriste);

    unsigned int (*getKolikoPutaJeProizveoZvuk)(void *strukturaKojaImplementiraInterfejs);

    void *strukturaKojuMetodeInterfejsaKoriste;

}MozeProizvestiZvuk;

void MozeProizvestiZvuk_obrisi(MozeProizvestiZvuk **adresaPointeraMozeProizvestiZvuk);

#endif

