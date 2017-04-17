package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import model.Mensagem.Acao;

public class ServicoServidor {
	private ServerSocket serverSocket;
	private Socket socket;
	private Map<String, ObjectOutputStream> mapOnlines = new HashMap<String, ObjectOutputStream>();
	
	public ServicoServidor() {
		try {
			serverSocket = new ServerSocket(2017);
			
			while(true){
				socket = serverSocket.accept();
				
				new Thread(new ListenerSocket(socket)).start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class ListenerSocket implements Runnable{

		private ObjectOutputStream output;
		private ObjectInputStream input;
		
		public ListenerSocket(Socket socket){
			
			try {
				this.input = new ObjectInputStream(socket.getInputStream());
				this.output = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			Mensagem mensagem = null;
			
			try {
				while((mensagem = (Mensagem) input.readObject()) != null){
					Acao acao = mensagem.getAcao();
					
					if(acao.equals(Acao.conectar)){
						conectar(mensagem, output);
					}else if(acao.equals(Acao.disconectar)){
						
					}else if(acao.equals(Acao.enviar_um)){
						
					}else if(acao.equals(Acao.enviar_todos)){
						
					}else if(acao.equals(Acao.utilOn)){
						
					}
					
				}
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private void conectar(Mensagem mensagem, ObjectOutputStream output){
		enviar_um(mensagem, output);
	}
	
	private void enviar_um(Mensagem mensagem, ObjectOutputStream output){
		try {
			output.writeObject(mensagem);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
