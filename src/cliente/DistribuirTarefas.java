package cliente;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DistribuirTarefas implements Runnable {

	private Socket socket;
	private ServidorTarefas servidor;
	private ExecutorService threadPool;

	public DistribuirTarefas(Socket socket, ServidorTarefas servidor, ExecutorService threadPool) {
		this.socket = socket;
		this.servidor = servidor;
		this.threadPool = threadPool;
	}

	@Override
	public void run() {
		try {
			System.out.println("Distribuindo tarefas para " + socket);

			Scanner entradaCliente = new Scanner(socket.getInputStream());

			PrintStream saidaCliente = new PrintStream(socket.getOutputStream());
			while (entradaCliente.hasNextLine()) {
				String comando = entradaCliente.nextLine();
				System.out.println("Comando confirmado " + comando);
				switch (comando) {
				case "c1": {
					saidaCliente.println("Confirma��o comando c1");
					ComandoC1 c1 = new ComandoC1(saidaCliente);
					this.threadPool.execute(c1);
					break;
				}
				case "c2": {
					saidaCliente.println("Confirma��o comando c1");
					ComandoC2ChamaWS c2 = new ComandoC2ChamaWS(saidaCliente);
					ComandoC2AcessaBanco c2Banco = new ComandoC2AcessaBanco(saidaCliente);
					Future<String> futureWS = this.threadPool.submit(c2);
					Future<String> futureBanco = this.threadPool.submit(c2Banco);

					this.threadPool.submit(new JuntaResultadoFutureWSFutureBanco(futureWS, futureBanco, saidaCliente));

					break;
				}
				case "close": {
					saidaCliente.println("Confirma��o comando close");
					servidor.parar();
					break;
				}
				default: {

					saidaCliente.println("Comandoo n�o encontrado");
				}

				}
				System.out.println(entradaCliente.hasNextLine());
			}
			entradaCliente.close();
			System.out.println("Conex�o fechada");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
