package interfaces;

import java.rmi.RemoteException;

public interface IAgenda {
	
	public void consultarDisponibilidade() throws RemoteException;
	
	public void adicionarEvento() throws RemoteException;
	
	public void listarEventos() throws RemoteException;

}
