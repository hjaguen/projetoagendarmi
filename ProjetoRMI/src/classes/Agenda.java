package classes;

import interfaces.IAgenda;
import interfaces.ICliente;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import javax.swing.JFrame;

import view.Cliente;

public class Agenda extends UnicastRemoteObject implements IAgenda{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Evento> eventos;
	private TreeMap<String, Contato> contatos;
	private Contato usuario;
	private ICliente cliente;
	
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

	@Override
	public void listarEventos() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	public void adicionarContatos(Object[] nomes) throws RemoteException{
		try {
			for (Object nome : nomes) {
				String n = (String) nome;
				IAgenda ia = (IAgenda)Naming.lookup(n);
				addContatos(n,ia.getUsuario());
				System.out.println("Contato "+nome+" adicionado com sucesso!");
			}			
		} catch (Exception e) {
			System.out.println("Contato está offline!");
			e.printStackTrace();
		}
	}

	@Override
	public void removerContatos(Object[] nomes)
			throws RemoteException {
		try {
			for (Object nome : nomes) {
				String n = (String) nome;
				contatos.remove(n);
				System.out.println("Contato "+n+" removido com sucesso!");
			}			
		} catch (Exception e) {
			System.out.println("Contato está offline!");
			e.printStackTrace();
		}		
	}

	public void setCliente(ICliente cliente) {
		this.cliente = cliente;
	}

	public ICliente getCliente(){
		return this.cliente;
	}

	@Override
	public ArrayList<String> consultarDisponibilidade(Date dataInicio, Date dataFim,
			Object[] nomes) throws RemoteException {
		ArrayList<String> resultado = new ArrayList<String>();
		try {
			for (Object nome : nomes) {
				String n = (String) nome;
				IAgenda ia = (IAgenda)Naming.lookup(n);
				for (Evento evento : ia.getEventos()) {
					if((dataInicio.after(evento.getHoraInicio())&& dataInicio.before(evento.getHoraFim()))
							|| (dataFim.after(evento.getHoraInicio())&& dataFim.before(evento.getHoraFim()))){
						resultado.add(n);
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("Contato está offline!");
			e.printStackTrace();
			
		}
		return resultado;
		
	}

	@Override
	public void confirmarEvento(Evento e) throws RemoteException {
		for (String nome : e.getContatos().keySet()) {
			IAgenda ia = cliente.getServidor().consultaAgenda(nome);
			ia.addEventos(e);
			ICliente c = ia.getCliente();
			c.atualizar();
		}
		
	}

	@Override
	public void enviarConvites() throws RemoteException {
		
		
	}

}
