package cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class Cliente {
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Cliente("127.0.0.1", 12345).executa();
	}

	private String host;
	private int porta;

	public Cliente(String host, int porta) {
		this.host = host;
		this.porta = porta;
	}

	public void executa() throws UnknownHostException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Socket cliente = new Socket(host, porta);
		System.out.println("O cliente se conectou ao servidor!");

		// thread para receber mensagens do servidor
		Recebedor r = new Recebedor(cliente.getInputStream());
		new Thread(r).start();

		// lê mensagens do teclado e envia a mensagem para o servidor
		Scanner teclado = new Scanner(System.in);
		String nome = "";
		String mensagemParaServidor;

		PrintStream saida = new PrintStream(cliente.getOutputStream());
		System.out.print("Por favor, informe seu nome: ");
		nome = teclado.nextLine();

		System.out.println("Para sair digite /quit");

		while (teclado.hasNextLine()) {
			mensagemParaServidor = teclado.nextLine();
			if (mensagemParaServidor.startsWith("/quit"))
				break;
			saida.println(sdf.format(new Date()) + " <" + nome + ">: " + mensagemParaServidor);
		}

		System.out.println("Conexao encerrada...");
		teclado.close();
		saida.close();
		cliente.close();
	}
}
