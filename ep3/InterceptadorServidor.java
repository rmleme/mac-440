// Classe: InterceptadorServidor.java
// Autores: Glaucio de Siqueira Le                 Numero USP: 3139021
//          Paulo Marcel Caldeira Yokosawa         Numero USP: 3095430
//          Rodrigo Mendes Leme                    Numero USP: 3151151
//          Rodrigo Simoes de Almeida              Numero USP: 3132218
// Exercicio Programa 3                            Data: 1/07/2002
// Descricao: intercepta mensagens GIOP enviadas e recebidas pelo servidor.

import java.util.zip.*;

public class InterceptadorServidor implements jacorb.orb.ServerMessageInterceptor
{

  // Metodo: proximaPot2
  // Entrada: valor
  // Saida: a primeira potencia de 2 maior ou igual a valor.
  // Descricao: calcula a primeira potencia de 2 maior ou igual um dado valor.

  public int proximaPot2(int valor)
  {
    int i;

    for (i = 0; i < 32 && valor > java.lang.Math.pow(2,i); i++);
    return (int) java.lang.Math.pow(2,i);
  }

  // Metodo: obtemTamanho
  // Entrada: buf: mensagem GIOP
  // Saida: o tamanho real da mensagem.
  // Descricao: devolve o tamanho da mensagem GIOP.

  private int obtemTamanho(byte[] buf)
  {
    int unidade = buf[11],
        dezena  = buf[10],
        centena = buf[9],
        milhar  = buf[8];
    
    // Converte numeros binarios negativos nos seus equivalentes positivos
    if (unidade < 0)
      unidade = 256 + unidade;
    if (dezena < 0)
      dezena = 256 + dezena;
    if (centena < 0)
      centena = 256 + centena;
    if (milhar < 0)
      milhar = 256 + milhar;

    return milhar * 1000 + centena * 100 + dezena * 10 + unidade;
  }

  // Metodo: alteraTamanho
  // Entrada: buf: mensagem GIOP.
  //          tamanho: o tamanho da mensagem.
  // Descricao: altera no cabecalho GIOP o tamanho da mensagem.

  private void alteraTamanho(byte[] buf, int tamanho)
  {
    buf[8]  = (byte) ((tamanho >>> 24) & 0xFF);
    buf[9]  = (byte) ((tamanho >>> 16) & 0xFF);
    buf[10] = (byte) ((tamanho >>>  8) & 0xFF);
    buf[11] = (byte) (tamanho & 0xFF);
  }

  // Metodo: pre_invoke
  // Entrada: buf: mensagem GIOP.
  // Saida: a mensagem GIOP descomprimida.
  // Descricao: descomprime a mensagem GIOP recebida do cliente.

  public byte[] pre_invoke(byte[] buf)
  {
    int i,
        tamanhoOrig = obtemTamanho(buf),
        tamanhoDescomp;
    byte tmp[]      = new byte[tamanhoOrig],
         tmp2[]     = new byte[10 * buf.length];

    // Copia a mensagem (sem o cabecalho e os bytes finais) para tmp
    for (i = 12; i < tamanhoOrig + 12; i++)
      tmp[i - 12] = buf[i];

    // Descomprime a mensagem
    Inflater descompressor = new Inflater();
    descompressor.setInput(tmp);
    try {
      tamanhoDescomp = descompressor.inflate(tmp2);
    }
      catch(DataFormatException dfe) {
      System.out.println("Erro: arquivo compactado com defeito.");
      return buf;
    }
    descompressor.end();

    // Cria novo buffer com a mensagem descomprimida
    int tamanhoBuf = proximaPot2(tamanhoDescomp + 12);
    byte buf2[]    = new byte[tamanhoBuf];
    System.arraycopy(buf,0,buf2,0,12);                   // Cabecalho GIOP
    System.arraycopy(tmp2,0,buf2,12,tamanhoDescomp);     // Corpo da mensagem
    for (i = tamanhoDescomp + 12; i < tamanhoBuf; i++)
      buf2[i] = 0;
    alteraTamanho(buf2,tamanhoDescomp);

    return buf2;
  }

  // Metodo: post_invoke
  // Entrada: buf: mensagem GIOP.
  // Saida: a mensagem GIOP comprimida.
  // Descricao: comprime a mensagem GIOP a ser enviada para o cliente.

  public byte[] post_invoke(byte[] buf)
  {
    int i;
    byte tmp[]  = new byte[buf.length - 12],
         tmp2[] = new byte[2 * buf.length];

    // Copia a mensagem (sem o cabecalho) para tmp
    System.arraycopy(buf,12,tmp,0,buf.length - 12);

    // Comprime a mensagem
    Deflater compressor = new Deflater(Deflater.BEST_COMPRESSION);
    compressor.setInput(tmp);
    compressor.finish();
    int tamanhoComp = compressor.deflate(tmp2);

    // Cria novo buffer com a mensagem comprimida
    byte buf2[] = new byte[tamanhoComp + 12];
    System.arraycopy(buf,0,buf2,0,12);                // Cabecalho GIOP
    System.arraycopy(tmp2,0,buf2,12,tamanhoComp);     // Corpo da mensagem
    alteraTamanho(buf2,tamanhoComp);
    
    return buf2;
  }
}
