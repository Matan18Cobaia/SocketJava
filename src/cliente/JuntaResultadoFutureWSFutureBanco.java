package cliente;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JuntaResultadoFutureWSFutureBanco implements Callable<Void> {

  private Future<String> futureWS;
  private Future<String> futureBanco;
  private PrintStream saidaCliente;

  public JuntaResultadoFutureWSFutureBanco(Future<String> futureWS, Future<String> futureBanco,
      PrintStream saidaCliente) {
    this.futureWS = futureWS;
    this.futureBanco = futureBanco;
    this.saidaCliente = saidaCliente;
  }

  @Override
  public Void call() throws Exception {
    System.out.println("Aguardando resultados do future WS e Banco");
    saidaCliente.println("Aguardando resultados");

    try {
      String conteudoSite = this.futureWS.get(15, TimeUnit.SECONDS);
      String numeroMagico = this.futureBanco.get(15, TimeUnit.SECONDS);
      this.saidaCliente.println("Resultado comando c2:" + numeroMagico + ", " + conteudoSite);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      this.saidaCliente.println("Timeout na execucao do comando c2");
      System.out.println("Timeout: Cancelando execucao do comando c2");
      this.futureBanco.cancel(true);
      this.futureWS.cancel(true);
    }
    System.out.println("Finalizou JuntaResultadosFutureWSEBanco");

    return null;
  }

}
