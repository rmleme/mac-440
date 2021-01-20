#include <stdlib.h>
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <sys/time.h>

#define LONG_MIN -1
#define LONG_MAX 300000

void calculaMediaDP(double *media, double *desvio_padrao, double *dados,
                    int qtos_dados) {
  int i,
      qtos_dados_filt = 0,
      ind_max,
      ind_min;
  double soma = 0,
         soma_quad = 0,
         *dados_filtrados,
         max = LONG_MIN,
         min = LONG_MAX;

  // Obtem os valores extremos da amostra
  for (i = 0; i < qtos_dados; i++) {
    if (dados[i] > max) {
      max = dados[i];
      ind_max = i;
    }
    if (dados[i] < min) {
      min = dados[i];
      ind_min = i;
    }
  }

  // Descarta os valores extremos da amostra
  dados_filtrados = (double *) malloc(qtos_dados * sizeof(double));
  for (i = 0; i < qtos_dados; i++)
    if ((i != ind_max) && (i != ind_min))
      dados_filtrados[qtos_dados_filt++] = dados[i];

  // Calcula media e desvio padrao
  for (i = 0; i < qtos_dados_filt; i++) {
    soma += dados_filtrados[i];
    soma_quad += dados_filtrados[i] * dados_filtrados[i];
  }
  *media = soma / qtos_dados_filt;
  *desvio_padrao = sqrt(soma_quad / qtos_dados_filt - *media * *media);
}

int gravaMatrizDouble(double **dados, int m, int n, char *narq) {
  int i, j;
  FILE *arq;
  
  arq = fopen(narq, "w+");
  if (arq == NULL) return -1;

  fprintf(arq,"#%d %d\n", m, n);
  for (i = 0; i < m; i++) {
    for (j = 0; j < n; j++)
      fprintf(arq,"%lf ", dados[i][j]);
    fprintf(arq, "\n");
  }    

  fclose(arq);
  return 1;
}


long count(int c) {

  static struct timeval tvalb, tval;
  static struct timezone tz;

  if (c == 1) {
    gettimeofday(&tval, &tz);
    return -1;
  }
  else {
    gettimeofday(&tvalb, &tz);
    return tvalb.tv_usec - tval.tv_usec;
  }
}
