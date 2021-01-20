// Modulo: TelaCliente.java
// Autores: Paulo Marcel Caldeira Yokosawa         Numero USP: 3095430
//          Rodrigo Mendes Leme                    Numero USP: 3151151
// Exercicio Programa 1                            Data: 31/03/2002
// Descricao: implementa uma interface grafica para o cliente, com metodos que
//            gerenciam os componentes visuais usados pelos participantes.

import java.awt.*;

class TelaCliente extends Frame
{
  private Participante participante;
  private ActionListenerTimer taskPerformerTimer;
  private ActionListenerBtnEnviar taskPerformerBtnEnviar;
  private ActionListenerBtnSair taskPerformerBtnSair;
  private javax.swing.Timer timer;
  private TextArea display;
  private Label titulo;
  private TextField entrada;
  private Choice participantes;
  private Button btnEnviar;
  private Button btnSair;


  public TelaCliente(Participante p)
  {
    // Configura a janela do cliente
    super();
    setTitle("MAC 440 - EP1 - " + p.getNome());
    setSize(700,450);
    setLayout(null);

    // Area onde aparecem as mensagens recebidas pelo usuario
    display = new TextArea();
    display.setEditable(false);
    display.setBounds(5,5,683,300);
    add(display);

    // Uma legenda explicativa
    titulo = new Label("Mensagem");
    titulo.setBounds(5,315,70,15);
    add(titulo);

    // Caixa de texto de onde serao lidas as mensagens
    entrada = new TextField();
    entrada.setBounds(5,330,500,30);
    add(entrada);

    // Caixa de selecao dos participantes da sessao de bate-papo
    participantes = new Choice();
    participantes.add("broadcast");
    String[] nomePartics = p.getSessao().getNomePartics();
    for (int i = 0; i < nomePartics.length; i++)
      if (!nomePartics[i].equals(p.getNome()) && !existe_item(p.getNome()))
        participantes.add(nomePartics[i]);
    participantes.setLocation(new Point(590,330));
    add(participantes);

    // Botao que manda as mensagens para o servidor
    btnEnviar = new Button("Enviar");
    btnEnviar.setBounds(510,330,70,30);
    this.taskPerformerBtnEnviar = new ActionListenerBtnEnviar(this);
    btnEnviar.addActionListener(this.taskPerformerBtnEnviar);
    add(btnEnviar);

    // Botao que encerra o programa
    btnSair = new Button("Sair");
    btnSair.setBounds(510,380,70,30);
    this.taskPerformerBtnSair = new ActionListenerBtnSair(this);
    btnSair.addActionListener(this.taskPerformerBtnSair);
    add(btnSair);

    // Timer que checa mensagens no servidor
    this.timer = new javax.swing.Timer(1000,this.taskPerformerTimer);
    this.taskPerformerTimer = new ActionListenerTimer(this);
    this.timer.addActionListener(this.taskPerformerTimer);

    pack();
    show();

    this.participante = p;
    this.timer.start();
    this.participante.cria_mensagem(this.participante.getNome() +
                                    " entrou na sala.","broadcast","");
  }


  public Participante getParticipante()
  {
    return this.participante;
  }


  public javax.swing.Timer getTimer()
  {
    return this.timer;
  }


  public TextField getEntrada()
  {
    return this.entrada;
  }


  public Choice getParticipantes()
  {
    return this.participantes;
  }


  // Metodo: existe_item
  // Entrada: o item a ter a existencia checada.
  // Saida: true se o item existe em this.participantes; false caso contrario.
  // Descricao: verifica se um dado item existe na lista visual de participan-
  //            tes.

  public boolean existe_item(String item)
  {
    int i;

    // i comeca com 1 pois o primeiro item sempre eh "broadcast"
    for (i = 1; i < this.participantes.getItemCount(); i++)
      if (this.participantes.getItem(i).equals(item))
        return true;
    return false;
  }    


  // Metodo: imprime_mensagem
  // Entrada: a mensagem a ser impressa e o autor da mesma.
  // Descricao: imprime uma mensagem na tela do participante.

  public void imprime_mensagem(String mensagem, String remetente)
  {
    if (remetente.equals(""))       // Mensagem especial do sistema
      display.append(mensagem + "\n\n");
    else
      display.append("Mensagem de " + remetente + ": " + mensagem + "\n\n");
  }
}
