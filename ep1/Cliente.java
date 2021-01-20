// Modulo: Cliente.java
// Autores: Paulo Marcel Caldeira Yokosawa         Numero USP: 3095430
//          Rodrigo Mendes Leme                    Numero USP: 3151151
// Exercicio Programa 1                            Data: 31/03/2002
// Descricao: executa a aplicacao do cliente, inicializando o ORB, acessando o
//            servidor e abrindo a interface com o participante.

import comp_idl.*;
import java.io.*;
import org.omg.PortableServer.*;

public class Cliente
{
  public static void main(String[] args)
  {
    if (args.length != 1)
      System.out.println("Erro: passar como argumento o nome.");
    else
      try
      {
        BufferedReader iorFile = new BufferedReader(new FileReader("ep1.ref"));
        String s               = iorFile.readLine();
        iorFile.close();
        org.omg.CORBA.ORB orb     = org.omg.CORBA.ORB.init(args,null);
        org.omg.CORBA.Object obj  = orb.string_to_object(s);
        Sessao sessao             = SessaoHelper.narrow(obj);
        POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        poa.the_POAManager().activate();
        Participante participante = new Participante(sessao,poa);
        if (participante.entrar(args[0]))
          new TelaCliente(participante);
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
  }
}
