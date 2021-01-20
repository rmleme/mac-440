// Modulo: ActionListenerTimer.java
// Autores: Paulo Marcel Caldeira Yokosawa         Numero USP: 3095430
//          Rodrigo Mendes Leme                    Numero USP: 3151151
// Exercicio Programa 1                            Data: 31/03/2002
// Descricao: tratador de eventos de um objeto Timer.

import comp_idl.*;
import java.awt.event.*;

public class ActionListenerTimer implements ActionListener
{
  private TelaCliente tela;    // Interface grafica a qual este ActionListener
                               // esta vinculado

  public ActionListenerTimer(TelaCliente t)
  {
    this.tela = t;
  }


  // Metodo: actionPerformed
  // Entrada: um evento disparado por um timer.
  // Descricao: busca uma mensagem no servidor para um dado participante; caso
  //            exista alguma, imprime-a na tela.

  public void actionPerformed(ActionEvent event)
  {
    Mensagem mensagem = this.tela.getParticipante().getSessao().checa_mensagem(this.tela.getParticipante().getNome());
    if (mensagem != null)
      if (mensagem.getRemetente().equals("especial - entrar") &&
          !this.tela.existe_item(mensagem.getConteudo()))
        this.tela.getParticipantes().add(mensagem.getConteudo());
      else
        if (mensagem.getRemetente().equals("especial - sair"))
          this.tela.getParticipantes().remove(mensagem.getConteudo());
        else
          this.tela.imprime_mensagem(mensagem.getConteudo(),
                                     mensagem.getRemetente());
  }
}

