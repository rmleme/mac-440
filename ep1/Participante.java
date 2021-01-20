// Modulo: Participante.java
// Autores: Paulo Marcel Caldeira Yokosawa         Numero USP: 3095430
//          Rodrigo Mendes Leme                    Numero USP: 3151151
// Exercicio Programa 1                            Data: 31/03/2002
// Descricao: implementa as funcionalidades de um participante de bate-papo,
//            com metodos para entrar, sair e mandar mensagens para a sessao.

import comp_idl.*;

class Participante
{
  private Sessao sessao;         // Sessao de bate-papo
  private String nome;           // Nome do participante na sessao
  private org.omg.PortableServer.POA poa;


  public Participante(Sessao s, org.omg.PortableServer.POA p)
  {
    this.sessao = s;
    this.poa    = p;
  }


  public Sessao getSessao()
  {
    return this.sessao;
  }


  public String getNome()
  {
    return this.nome;
  }


  // Metodo: entrar
  // Entrada: novoNome: nome do novo participante.
  // Saida: true se o novo participante foi incluido na sessao; false caso con-
  //        trario.
  // Descricao: recebe o nome de um novo participante e tenta inclui-lo na
  //            sessao de bate-papo, notificando os outros participantes.

  public boolean entrar(String novoNome)
  {
    if (novoNome.equals("broadcast") || novoNome.equals("especial - entrar") ||
        novoNome.equals("especial - sair"))
      return false;
    else
      if (this.sessao.insere_partic(novoNome))
      {
        this.nome = new String(novoNome);
        this.cria_mensagem(this.nome,"broadcast","especial - entrar");
        return true;
      }
      else
      {
        System.out.println("Erro: ja existe usuario com esse apelido.");
        return false;
      }
  }


  // Metodo: sair
  // Descricao: remove o participante da sessao de bate-papo, notificando os
  //            outros participantes, e encerra o programa.

  public void sair()
  {
    this.sessao.remove_partic(this.nome);
    this.cria_mensagem(this.nome,"broadcast","especial - sair");
    System.exit(0);
  }


  // Metodo: cria_mensagem
  // Entrada: os elementos constituintes de uma mensagem.
  // Descricao: cria e envia para o servidor uma nova mensagem do participante.

  public void cria_mensagem(String conteudo, String destinatario,
                            String remetente)
  {
    MensagemImpl msg_impl = new MensagemImpl(conteudo,destinatario,remetente);
    try
    {
      org.omg.CORBA.Object obj_msg = this.poa.servant_to_reference(msg_impl);
      this.sessao.manda_mensagem(MensagemHelper.narrow(obj_msg));
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
}
