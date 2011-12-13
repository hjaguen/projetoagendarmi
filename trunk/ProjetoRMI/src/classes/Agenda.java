package classes;

import interfaces.IAgenda;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Agenda extends UnicastRemoteObject implements IAgenda{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Evento> eventos;
	private TreeMap<String, Contato> contatos;
	private Contato usuario;
	
	public ArrayList<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(ArrayList<Evento> eventos) {
		this.eventos = eventos;
	}
	public void addEventos(Evento e) {
		eventos.add(e);
	}

	public TreeMap<String, Contato> getContatos() {
		return contatos;
	}

	public void setContatos(TreeMap<String, Contato> contatos) {
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

	
	//Métodos da Agenda
	@Override
	public void consultarDisponibilidade() throws RemoteException {
		
		
	}

	@Override
	public boolean adicionarEvento(Evento e) throws RemoteException {
		
		return true;
	}

	@Override
	public void listarEventos() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
