package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import threads.RespostaThread;
import view.Cliente;

import classes.Contato;
import classes.Evento;

public interface IAgenda extends Remote {
	
	public ArrayList<String> consultarDisponibilidade(Date dataInicio,Date dataFim,Object[] nomes) throws RemoteException;
	
	public void listarEventos() throws RemoteException;
	
	public void addEventos(Evento e) throws RemoteException;
	
	public Contato getUsuario() throws RemoteException;
	
	public ICliente getCliente() throws RemoteException;
	
	public TreeMap<String, Contato> getContatos() throws RemoteException;
	
	public void adicionarContatos(Object[] nomes) throws RemoteException;
	
	public void removerContatos(Object[] nomes) throws RemoteException;
	
	public ArrayList<Evento> getEventos() throws RemoteException;
	
	public void confirmarEvento(Evento e) throws RemoteException;
	
	public void enviarConvites() throws RemoteException;

}
