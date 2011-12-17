package classes;

import interfaces.IAgenda;
import interfaces.IServidor;

import java.rmi.Naming;
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
		if((contatos.containsKey(n))) {
			System.out.println("Este contato já está adicionado!");
			return;
		}
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
		contatos = new TreeMap<String,Contato>();
		
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
	
	public void adicionarContatos(String[] nomes) throws RemoteException{
		try {
			for (String nome : nomes) {
				IAgenda ia = (IAgenda)Naming.lookup(nome);
				addContatos(nome,ia.getUsuario());
				System.out.println("Contato "+nome+" adicionado com sucesso!");
			}			
		} catch (Exception e) {
			System.out.println("Contato está offline!");
			e.printStackTrace();
		}
	}

	@Override
	public void removerContatos(String[] nomes)
			throws RemoteException {
		try {
			for (String nome : nomes) {
				contatos.remove(nome);
				System.out.println("Contato "+nome+" removido com sucesso!");
			}			
		} catch (Exception e) {
			System.out.println("Contato está offline!");
			e.printStackTrace();
		}		
	}

}
