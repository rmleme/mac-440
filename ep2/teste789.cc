#include <iostream>
#include <fstream>
#include "server.h"
#include "serverimpl.h"

using namespace CORBA;
using namespace PortableServer;
using namespace Server;

long count(int c);
int gravaMatrizDouble(double **dados, int m, int n, char *narq);
void calculaMediaDP(double *media, double *desvio_padrao, double *dados, int qtos_dados);

void test789 (TC_ptr server, char *arq) {

  
  long tempo, tmp;
  char *strTeste;
  double **dados;
  double *dt;
  int i,ne,ex,l,m,n,imax;

  ne = 200;       // numero de vezes que o experimento sera realizado
  m = 11;  n = 3; // dimensoes da matriz de dados
  imax = 1024;

  cout << "Executando experimento 7 ou 8 ou 9 ...\n";

  // aloca a matriz da dados
  dados = (double **) malloc (m * sizeof(double*));
  for (i = 0; i < m; i++)
    dados[i] = (double *) malloc (n * sizeof(double));

  dt = (double *) malloc  (ne * sizeof(double));
  
 
  //realiza o teste 
  for (l = 0, i = 1; i <= imax; i *= 2, l++) {
    // roda o experimento ne vezes e calcula a media do tempo
    tmp = 0;
    for (ex = 0; ex < ne; ex++) {
      strTeste = (char *) malloc (i * sizeof(char));
      
      count (1);
      server->f7(strTeste);
      tempo = count(0);
      
      free(strTeste);
     
      dt[ex] = i;
      dt[ex] = tempo;
    }
    dados[l][0] = i;
    calculaMediaDP(&(dados[l][1]), &(dados[l][2]), dt, ex);
  }
  
  gravaMatrizDouble(dados, m, n, arq);
  
  for (i = 0; i < m; i++) free (dados[i]);
  free(dados);
  free(dt);
}
