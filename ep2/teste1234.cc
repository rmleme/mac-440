#include <iostream>
#include <fstream>
#include <sys/time.h>
#include "server.h"
#include "serverimpl.h"

using namespace CORBA;
using namespace PortableServer;
using namespace Server;

long count(int c);
int gravaMatrizDouble(double **dados, int m, int n, char *narq);
void calculaMediaDP(double *media, double *desvio_padrao, double *dados, int qtos_dados);

#define QTOS_TESTES 10

void test1234(TC_ptr server){
  int i;
  CORBA::Long ret_long;
  Server::TC::ts1 p1, p2, p3, p4, p5, ret_struct;
  double *tempos,
         **dados,
         media,
         desvio_padrao;

  dados = (double **) malloc(4 * sizeof(double *));
  for (i = 0; i < 4; i++)
    dados[i] = (double *) malloc(3 * sizeof(double));

  tempos = (double *) malloc(QTOS_TESTES * sizeof(double));

  cout << "Executando experimento 1...\n";
  media = 0;  
  for (i = 0; i < QTOS_TESTES; i++) {
    count(1);
    server->f1();
    tempos[i] = count(0);
  }
  calculaMediaDP(&media, &desvio_padrao, tempos, QTOS_TESTES);
  dados[0][0] = 1;
  dados[0][1] = media / 10;
  dados[0][2] = desvio_padrao / 10;

  cout << "Executando experimento 2...\n";
  media = 0;
  for (i = 0; i < QTOS_TESTES; i++) {
    count(1);
    ret_long = server->f2(1000);
    tempos[i] = count(0);
  }
  calculaMediaDP(&media, &desvio_padrao, tempos, QTOS_TESTES);
  dados[1][0] = 2;
  dados[1][1] = media / 10;
  dados[1][2] = desvio_padrao / 10;

  cout << "Executando experimento 3...\n";
  media = 0;
  for (i = 0; i < QTOS_TESTES; i++) {
    count(1);
    ret_long = server->f3(1000, 1000, 1000, 1000, 1000);
    tempos[i] = count(0);
  }
  calculaMediaDP(&media, &desvio_padrao, tempos, QTOS_TESTES);
  dados[2][0] = 3;
  dados[2][1] = media / 10;
  dados[2][2] = desvio_padrao / 10;

  cout << "Executando experimento 4...\n";
  p1.x = p2.x = p3.x = p4.x = p5.x = 1000;
  p1.y = p2.y = p3.y = p4.y = p5.y = 1000;
  p1.z = p2.z = p3.z = p4.z = p5.z = 1000;
  media = 0;
  for (i = 0; i < QTOS_TESTES; i++) {
    count(1);
    ret_struct = server->f4(p1, p2, p3, p4, p5);
    tempos[i] = count(0);
  }
  calculaMediaDP(&media, &desvio_padrao, tempos, QTOS_TESTES);
  dados[3][0] = 4;
  dados[3][1] = media / 10;
  dados[3][2] = desvio_padrao / 10;

  gravaMatrizDouble(dados, 4, 3, "exp1-4.dat");

  for (i = 0; i < 4; i++)
    free(dados[i]);
  free(dados);
  free(tempos);
}
