// Modulo: SessaoImpl.java
// Autores: Paulo Marcel Caldeira Yokosawa         Numero USP: 3095430
//          Rodrigo Mendes Leme                    Numero USP: 3151151
// Exercicio Programa 1                            Data: 31/03/2002
// Descricao: implementa as funcionalidades de uma sessao de bate-papo, com me-
//            todos para gerenciar os participantes e as mensagens enviadas
//            pelos mesmos.

import comp_idl.*;
import java.util.*;

public class SessaoImpl extends SessaoPOA
{
  private Vector mensagens;        // Mensagens enviadas pelos participantes
  private Vector nomePartics;      // Nomes dos participantes da sessao
  private org.omg.PortableServer.POA poa;


  public SessaoImpl(org.omg.PortableServer.POA poa)
  {
    this.mensagens   = new Vector();
    this.nomePartics = new Vector();
    this.poa         = poa;
  }


  public String[] getNomePartics()
  {
    int i;
    String[] aux = new String[this.nomePartics.size()];

    for (i = 0; i < this.nomePartics.size(); i++)
      aux[i] = (String) this.nomePartics.elementAt(i);
    return aux;
  }


  // Metodo: insere_partic
  // Entrada: novoNome: o nome do novo participante.
  // Saida: true se o novo participante foi incluido na sessao; false caso con-
  //        trario.
  // Descricao: tenta incluir um novo participante na sessao de bate-papo.

  public boolean insere_partic(String novoNome)
  {
    if (!this.nomePartics.contains(novoNome))
    {
      this.nomePartics.add(novoNome);
      return true;
    }
    else
      return false;
  }


  // Metodo: remove_partic
  // Entrada: nome: o nome do participante.
  // Descricao: remove um participante da sessao de bate-papo. Quaisquer mensa-
  //            gens pendentes para o mesmo sao removidas.

  public void remove_partic(String nome)
  {
    this.nomePartics.remove(nome);
    for (Enumeration e = this.mensagens.elements(); e.hasMoreElements() ;)
    {
      Mensagem mensagem = (Mensagem) e.nextElement();
      if (mensagem.getDestinatario().equals(nome))
        this.mensagens.remove(mensagem);
    }
  }


  // Metodo: manda_mensagem
  // Entrada: uma mensagem enviada por um participante.
  // Saida: true se a mensagem foi processada com sucesso; false caso contrario
  // Descricao: recebe a mensagem mandada pelo participante e armazena-a na
  //            lista de mensagens da sessao.

  public boolean manda_mensagem(Mensagem mensagem)
  {
    // Mensagem broadcast, reencaminha-a para todos os participantes
    if (mensagem.getDestinatario().equals("broadcast"))
    {
      // Para cada participante, cria uma nova mensagem
      for (Enumeration e = this.nomePartics.elements(); e.hasMoreElements(); )
      {
        String nomePartic    = new String((String) e.nextElement());
        MensagemImpl msgImpl = new MensagemImpl(mensagem.getConteudo(),
                                                nomePartic,
                                                mensagem.getRemetente());
        try
        {
          org.omg.CORBA.Object objMsg = poa.servant_to_reference(msgImpl);
          this.mensagens.add(MensagemHelper.narrow(objMsg));
        }
        catch (org.omg.PortableServer.POAPackage.ServantNotActive e1)
        {
          e1.printStackTrace();
        }
        catch (org.omg.PortableServer.POAPackage.WrongPolicy e2)
        {
          e2.printStackTrace();
        }
      }
      return true;
    }

    // Mensagem para um unico destinatario
    else
      if (this.nomePartics.contains(mensagem.getDestinatario()))
      {
        this.mensagens.add(mensagem);
        return true;
      }
      else
        return false;
  }


  // Metodo: checa_mensagem
  // Entrada: o destinatario das mensagens.
  // Saida: null se nao ha mensagens para aquele destinatario; uma mensagem ca-
  //        so contrario.
  // Descricao: para um dado destinatario, checa a lista de mensagens em busca
  //            de alguma para ele.

  public Mensagem checa_mensagem(String destinatario)
  {
    for (Enumeration e = this.mensagens.elements(); e.hasMoreElements() ;)
    {
      Mensagem mensagem = (Mensagem) e.nextElement();
      if (mensagem.getDestinatario().equals(destinatario))
      {
        this.mensagens.remove(mensagem);
        return mensagem;
      }
    }
    return null;
  }
}
