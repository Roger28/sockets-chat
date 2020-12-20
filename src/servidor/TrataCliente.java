package servidor;

import java.io.InputStream;
import java.util.Scanner;

public class TrataCliente implements Runnable {

	private InputStream cliente;
	private Servidor servidor;

	public TrataCliente(InputStream cliente, Servidor servidor) {
		this.cliente = cliente;
		this.servidor = servidor;
	}

	@Override
	public void run() {
		// verifica se é pra produzir ou consumir e executa a thread correspondente
		Scanner scanner = new Scanner(this.cliente);
		while (scanner.hasNextLine()) {
			this.servidor.distribuiMensagem(scanner.nextLine());
		}
		scanner.close();
	}

}
