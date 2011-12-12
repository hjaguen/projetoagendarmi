package classes;

import interfaces.IAgenda;
import interfaces.IServidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class Agenda extends UnicastRemoteObject implements IAgenda{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Evento> eventos;
	private ArrayList<Contato> contatos;
	private Contato usuario;
	
	public ArrayList<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(ArrayList<Evento> eventos) {
		this.eventos = eventos;
	}

	public ArrayList<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(ArrayList<Contato> contatos) {
		this.contatos = contatos;
	}

	public Contato getUsuario() {
		return usuario;
	}

	public void setUsuario(Contato usuario) {
		this.usuario = usuario;
	}

	public Agenda() throws RemoteException {
		
		eventos = new ArrayList<Evento>();
		
	}

	public static void main (String[] args){
		try {
			Agenda a = new Agenda();
			Contato c = new Contato();
			Scanner entrada = new Scanner(System.in);
			System.out.println("Digite seu nome:");
			c.setNome(entrada.nextLine());
			System.out.println("Digite seu email:");
			c.setEmail(entrada.nextLine());
			a.setUsuario(c);
			IServidor s = (IServidor) Naming.lookup("servidor");
			s.registraAgenda(c.getNome(), a);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
	//Métodos da Agenda
	@Override
	public void consultarDisponibilidade() throws RemoteException {
		
		
	}

	@Override
	public void adicionarEvento() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listarEventos() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
