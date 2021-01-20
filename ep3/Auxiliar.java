// Classe: Auxiliar.java
// Autores: Glaucio de Siqueira Le                 Numero USP: 3139021
//          Paulo Marcel Caldeira Yokosawa         Numero USP: 3095430
//          Rodrigo Mendes Leme                    Numero USP: 3151151
//          Rodrigo Simoes de Almeida              Numero USP: 3132218
// Exercicio Programa 3                            Data: 1/07/2002
// Descricao: implementacao de metodos auxiliares para o cliente e o servidor.

import java.io.*;

public class Auxiliar
{
  private long long_min = -1;
  private long long_max = 300000;

  // Metodo: calculaMedia
  // Entrada: dados: conjunto de numeros.
  // Saida: a media dos dados de entrada.
  // Descricao: calcula a media de um conjunto de numeros, descartando os
  //            valores extremos dos mesmos.

  public double calculaMedia(double dados[])
  {
    int i,
        qtos_dados_filt = 0,
        ind_max         = 0,
        ind_min         = 0;
    double soma = 0,
           max  = long_min,
           min  = long_max;

    // Obtem os valores extremos da amostra
    for (i = 0; i < dados.length; i++)
    {
      if (dados[i] > max)
      {
        max     = dados[i];
        ind_max = i;
      }
      if (dados[i] < min)
      {
        min     = dados[i];
        ind_min = i;
      }
    }

    // Descarta os valores extremos da amostra
    double dados_filtrados[] = new double[dados.length];
    for (i = 0; i < dados.length; i++)
      if ((i != ind_max) && (i != ind_min))
        dados_filtrados[qtos_dados_filt++] = dados[i];

    // Calcula a media
    for (i = 0; i < qtos_dados_filt; i++)
      soma += dados_filtrados[i];
    return soma / qtos_dados_filt;
  }

  // Metodo: gravaResultados
  // Entrada: dados: vetor contendo os resultados dos experimentos.
  //          n: numero de linhas da matriz de dados.
  //          m: numero de colunas da matriz de dados.
  //          nomeArquivo: nome do arquivo onde os resultados serao gravados.
  // Descricao: recebe os resultados de experimentos e os salva num dado
  //            arquivo.

  public void gravaResultados(double[][] dados, int n, int m,
                              String nomeArquivo)
  {
    int i,
        j;

    try {
      PrintWriter arquivo = new PrintWriter(new FileWriter(nomeArquivo));
      arquivo.println("#" + n + " 2");
      for (i = 0; i < n; i++)
      {
        for (j = 0; j < m; j++)
          arquivo.print(dados[i][j] + " ");
        arquivo.println();
      }
      arquivo.close();
    }
    catch(IOException ioe) {
      System.out.println("Erro: nao pode gravar arquivo de resultados.");
      return;
    }
  }
}
