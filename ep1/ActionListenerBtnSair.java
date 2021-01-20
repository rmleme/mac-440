// Modulo: ActionListenerBtnSair.java
// Autores: Paulo Marcel Caldeira Yokosawa         Numero USP: 3095430
//          Rodrigo Mendes Leme                    Numero USP: 3151151
// Exercicio Programa 1                            Data: 31/03/2002
// Descricao: tratador de eventos de um objeto BtnSair (Button).

import java.awt.event.*;

public class ActionListenerBtnSair implements ActionListener
{
  private TelaCliente tela;    // Interface grafica a qual este ActionListener
                               // esta vinculado

  public ActionListenerBtnSair(TelaCliente t)
  {
    this.tela = t;
  }


  // Metodo: actionPerformed
  // Entrada: um evento disparado por um btnSair (Button).
  // Descricao: retira o participante da sessao de bate-papo, notificando todos
  //            os outros do fato, e fecha a interface grafica.

  public void actionPerformed(ActionEvent event)
  {
    this.tela.getParticipante().cria_mensagem(this.tela.getParticipante().getNome() + " deixou a sala.","broadcast","");
    this.tela.getTimer().stop();
    this.tela.dispose();
    this.tela.getParticipante().sair();
  }
}
