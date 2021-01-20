// Modulo: ActionListenerBtnEnviar.java
// Autores: Paulo Marcel Caldeira Yokosawa         Numero USP: 3095430
//          Rodrigo Mendes Leme                    Numero USP: 3151151
// Exercicio Programa 1                            Data: 31/03/2002
// Descricao: tratador de eventos de um objeto BtnEnviar (Button).

import java.awt.event.*;

public class ActionListenerBtnEnviar implements ActionListener
{
  private TelaCliente tela;    // Interface grafica a qual este ActionListener
                               // esta vinculado


  public ActionListenerBtnEnviar(TelaCliente t)
  {
    this.tela = t;
  }


  // Metodo: actionPerformed
  // Entrada: um evento disparado por um btnEnviar (Button).
  // Descricao: monta uma nova mensagem para o participante, e reinicializa a
  //            entrada de texto de mensagens.

  public void actionPerformed(ActionEvent event)
  {
    this.tela.getParticipante().cria_mensagem(this.tela.getEntrada().getText(),
                                this.tela.getParticipantes().getSelectedItem(),
                                this.tela.getParticipante().getNome());
    this.tela.getEntrada().setText("");
  }
}
