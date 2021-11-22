package cliente;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {

	private ExecutorService threadPool;
	private AtomicBoolean emLoop;
	private static ServerSocket servidor;

	public ServidorTarefas() throws IOException {
		System.out.println("--- Iniciando servidor ---");
		ServidorTarefas.servidor = new ServerSocket(12345);
		this.threadPool = Executors.newCachedThreadPool();
		this.emLoop = new AtomicBoolean(true);
	}

	private void rodar() throws IOException {
		while (this.emLoop.get()) {
			try {
				Socket socket = servidor.accept();
				System.out.println("Aceitando novo cliente na porta " + socket.getPort());
				DistribuirTarefas distribuiTarefas = new DistribuirTarefas(socket, this, threadPool);
				threadPool.execute(distribuiTarefas);
			} catch (SocketException e) {
				System.out.println("SocketExcepetion, est� rodando? " + this.emLoop);
			}
		}
	}

	public void parar() throws IOException {
		this.emLoop.set(false);
		threadPool.shutdown();
		servidor.close();
	}

	public static void main(String[] args) throws Exception {
		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
		servidor.parar();
	}
}
