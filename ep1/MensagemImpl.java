// Modulo: MensagemImpl.java
// Autores: Paulo Marcel Caldeira Yokosawa         Numero USP: 3095430
//          Rodrigo Mendes Leme                    Numero USP: 3151151
// Exercicio Programa 1                            Data: 31/03/2002
// Descricao: implementa as funcionalidades de uma mensagem de bate-papo.

import comp_idl.*;

public class MensagemImpl extends MensagemPOA
{
  private String conteudo;
  private String destinatario;
  private String remetente;


  public MensagemImpl(String c, String d, String r)
  {
    this.conteudo     = c;
    this.destinatario = d;
    this.remetente    = r;
  }


  public MensagemImpl(MensagemImpl mensagem)
  {
    this.conteudo     = mensagem.getConteudo();
    this.destinatario = mensagem.getDestinatario();
    this.remetente    = mensagem.getRemetente();
  }


  public String getConteudo()
  {
    return this.conteudo;
  }


  public String getDestinatario()
  {
    return this.destinatario;
  }


  public String getRemetente()
  {
    return this.remetente;
  }
}
