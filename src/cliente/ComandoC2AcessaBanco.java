package cliente;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2AcessaBanco implements Callable<String> {

	private PrintStream saida;

	public ComandoC2AcessaBanco(PrintStream saidaCliente) {
		this.saida = saidaCliente;
	}

	@Override
	public String call() throws Exception {
		System.out.println("Executando comando c2 - Banco");
		saida.println("processando comando c2 - Banco");
		Thread.sleep(20000);

		int numero = new Random().nextInt(100) + 1;
		System.out.println("Servidor finalizou comando c2 - Banco");
		return Integer.toString(numero);
	}

}
