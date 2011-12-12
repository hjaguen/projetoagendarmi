package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import classes.Agenda;

public interface IServidor extends Remote{
	
	public void registraAgenda(String n, Agenda a) throws RemoteException;
	
	public Agenda retornaAgenda() throws RemoteException;

}
