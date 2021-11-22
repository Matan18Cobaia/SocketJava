package cliente;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class ComandoC2ChamaWS implements Callable<String> {

	private PrintStream saida;

	public ComandoC2ChamaWS(PrintStream saidaCliente) {
		this.saida = saidaCliente;
	}

	@Override
	public String call() throws Exception {
		System.out.println("Executando comando c2 - WS");
		saida.println("processando comando c2 - WS");

		int numero = new Random().nextInt(100) + 1;
		System.out.println("Processo terminado c2 - WS");

		return Integer.toString(numero);
	}

}
