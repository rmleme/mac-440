// Modulo: Servidor.java       
// Autores: Paulo Marcel Caldeira Yokosawa         Numero USP: 3095430
//          Rodrigo Mendes Leme                    Numero USP: 3151151
// Exercicio Programa 1                            Data: 31/03/2002
// Descricao: executa a aplicacao servidora, inicializando o ORB, registrando o
//            servente e criando um object adapter.

import comp_idl.*;
import java.io.*;
import org.omg.PortableServer.*;

public class Servidor
{
  public static void main(String[] args) throws
                org.omg.CORBA.ORBPackage.InvalidName,
                org.omg.PortableServer.POAManagerPackage.AdapterInactive
  {
    org.omg.CORBA.ORB orb       = org.omg.CORBA.ORB.init(args,null);
    org.omg.CORBA.Object obj    = orb.resolve_initial_references("RootPOA");
    POA poa                     = POAHelper.narrow(obj);
    SessaoImpl sessaoServente   = new SessaoImpl(poa);
    org.omg.CORBA.Object sessao = sessaoServente._this_object(orb);
    try
    {
      PrintWriter iorFile = new PrintWriter(new FileWriter("ep1.ref"));
      iorFile.println(orb.object_to_string(sessao));
      iorFile.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    poa.the_POAManager().activate();
    orb.run();
  }
}
