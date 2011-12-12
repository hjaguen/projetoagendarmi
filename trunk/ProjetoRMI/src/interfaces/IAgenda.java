package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAgenda extends Remote {
	
	public void consultarDisponibilidade() throws RemoteException;
	
	public void adicionarEvento() throws RemoteException;
	
	public void listarEventos() throws RemoteException;
}
