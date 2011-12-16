package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import classes.*;

public interface IAgenda extends Remote {
	
	public void consultarDisponibilidade() throws RemoteException;
	
	public boolean adicionarEvento(Evento e) throws RemoteException;
	
	public void listarEventos() throws RemoteException;
	
	public void addEventos(Evento e) throws RemoteException;
	
	public Contato getUsuario() throws RemoteException;
	
	public ArrayList<Evento> getEventos() throws RemoteException;
}
