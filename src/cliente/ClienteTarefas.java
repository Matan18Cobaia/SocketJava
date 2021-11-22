package cliente;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import java.io.PrintStream;

public class ClienteTarefas {

	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 12345);
		System.out.println("Conexao estabelecida");

		Thread threadEnviaComando = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					PrintStream saida = new PrintStream(socket.getOutputStream());
					Scanner teclado = new Scanner(System.in);
					while (teclado.hasNextLine()) {
						String linha = teclado.nextLine();
						if (linha.trim().equals("")) {
							break;
						}
						saida.println(linha);
					}
					saida.close();
					teclado.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});

		Thread ThreadRecebeResposta = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					System.out.println("recebendo dados do servidor");
					Scanner respostaServido = new Scanner(socket.getInputStream());
					while (respostaServido.hasNextLine()) {
						String linha = respostaServido.nextLine();
						System.out.println(linha);
					}
					respostaServido.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});

		threadEnviaComando.start();
		ThreadRecebeResposta.start();

		threadEnviaComando.join();

		System.out.println("Fechando conex�o");
		socket.close();

	}
}
