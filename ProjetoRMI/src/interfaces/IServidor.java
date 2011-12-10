package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import classes.Agenda;

public interface IServidor extends Remote{
	
	public void registraAgenda() throws RemoteException;
	
	public Agenda retornaAgenda() throws RemoteException;

}
