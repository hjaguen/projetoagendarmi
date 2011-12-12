package interfaces;

import java.rmi.RemoteException;

import classes.Contato;

public interface IAgenda {
	
	public void consultarDisponibilidade() throws RemoteException;
	
	public void adicionarEvento() throws RemoteException;
	
	public void listarEventos() throws RemoteException;
}
