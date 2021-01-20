// Classe: Cliente.java
// Autores: Glaucio de Siqueira Le                 Numero USP: 3139021
//          Paulo Marcel Caldeira Yokosawa         Numero USP: 3095430
//          Rodrigo Mendes Leme                    Numero USP: 3151151
//          Rodrigo Simoes de Almeida              Numero USP: 3132218
// Exercicio Programa 3                            Data: 1/07/2002
// Descricao: inicializa a aplicacao cliente, configurando o ambiente
//            (inicializando o ORB e o POA), obtendo uma referencia para um
//            servente, registrando um interceptador de mensagens no ORB e
//            executando experimentos.

import comp_idl.*;
import java.io.*;
import org.omg.PortableServer.*;

public class Cliente
{
  private static int qtos_testes = 10;

  // Metodo: executaExperimento
  // Entrada: teste: servente remoto chamado pelo cliente.
  //          tamanhoMax: tamanho maximo da mensagem a ser computada.
  //          incremento: intervalo das amostras a serem plotadas (eixo x).
  // Descricao: chama um metodo remoto n vezes, calcula a media do tempo de
  //            execucao do mesmo e grava o resultado num arquivo.

  private static void executaExperimento(Teste teste, int tamanhoMax,
                                         int incremento)
  {
    Auxiliar aux        = new Auxiliar();
    double dados[][]    = new double[(int) java.lang.Math.ceil((double)
                                     tamanhoMax / incremento)][2];
    double tempos[]     = new double[qtos_testes];
    StringBuffer cadeia = new StringBuffer();
    long t_inic,     // Tempo inicial da execucao do experimeto
         t_fim;      // Tempo final da execucao do experimento
    int i,
        j,
        k = 0;

    for (i = 0; i < tamanhoMax; i++)
    {
      cadeia.append("X");
      if (i % incremento == 0)
      {
        for (j = 0; j < qtos_testes; j++)
        {
          t_inic = System.currentTimeMillis();
          teste.f1(new String(cadeia));
          t_fim     = System.currentTimeMillis();
          tempos[j] = t_fim - t_inic;
        }
        dados[k][0]   = i;
        dados[k++][1] = aux.calculaMedia(tempos);
      }
    }
    aux.gravaResultados(dados,(int) java.lang.Math.ceil((double) tamanhoMax /
                        incremento),2,"saida.dat");
  }

  public static void main(String[] args)
  {
    if (args.length != 1)
    {
      System.out.println("Uso: cliente.sh <tamanho-maximo>");
      System.out.println("<tamanho-maximo>: tamanho maximo da mensagem a " +
                         "ser enviada ao servidor.");
    }
    else
    {
      int tamanhoMax = Integer.parseInt(args[0]);
      int incremento = args[0].length();
      try
      {
        // Inicializacao do ORB
        jacorb.orb.ORB orb = (jacorb.orb.ORB)org.omg.CORBA.ORB.init(args,null);

        // Obtem referencia para o servente com o ORB
        BufferedReader iorFile = new BufferedReader(new FileReader("ep3.ref"));
        String s               = iorFile.readLine();
        iorFile.close();
        Teste teste = TesteHelper.narrow(orb.string_to_object(s));

        // Inicializacao do POA
        POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        poa.the_POAManager().activate();

        orb.addInterceptor(new InterceptadorCliente());
        executaExperimento(teste,tamanhoMax,incremento);
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }
  }
}
