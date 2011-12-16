package classes;

import interfaces.IAgenda;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
	public void addContatos(String n,Contato c) {
		this.contatos.put(n, c);
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
		Scanner entrada = new Scanner(System.in); 
		System.out.println("Você recebeu um novo convite!");
		System.out.println("Dia e hora: " + e.getData());
		System.out.println("Descrição do Evento: " + e.getDescricao());
		System.out.println("Deseja aceitar esse Convite? (S/N)");
		String resp = entrada.nextLine();
		resp = resp.toUpperCase();
		if (resp.equals("S") || resp.equals("SIM")) {
			return true;
		}
		else
			return false;
		
		
	}

	@Override
	public void listarEventos() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
