// Classe: Servidor.java
// Autores: Glaucio de Siqueira Le                 Numero USP: 3139021
//          Paulo Marcel Caldeira Yokosawa         Numero USP: 3095430
//          Rodrigo Mendes Leme                    Numero USP: 3151151
//          Rodrigo Simoes de Almeida              Numero USP: 3132218
// Exercicio Programa 3                            Data: 1/07/2002
// Descricao: inicializa a aplicacao servidora, configurando o ambiente
//            (inicializando o ORB e o POA) e registrando um objeto e um
//            interceptador de mensagens no ORB.

import comp_idl.*;
import java.io.*;
import org.omg.PortableServer.*;

public class Servidor
{
  public static void main(String[] args) throws
                org.omg.CORBA.ORBPackage.InvalidName,
                org.omg.PortableServer.POAManagerPackage.AdapterInactive
  {
    // Inicializacao do ORB, do POA e do servente
    jacorb.orb.ORB orb = (jacorb.orb.ORB) org.omg.CORBA.ORB.init(args,null);
    POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
    TesteImpl testeServente    = new TesteImpl();
    org.omg.CORBA.Object teste = testeServente._this_object(orb);

    // Cria referencia para o servidor no ORB
    try
    {
      PrintWriter iorFile = new PrintWriter(new FileWriter("ep3.ref"));
      iorFile.println(orb.object_to_string(teste));
      iorFile.close();
    }
    catch (IOException e) {
      System.out.println("Erro: nao e possiver obter referencia para o " +
                         "servidor.");
      return;
    }

    poa.the_POAManager().activate();
    orb.addServerInterceptor(new InterceptadorServidor());
    orb.run();
  }
}
