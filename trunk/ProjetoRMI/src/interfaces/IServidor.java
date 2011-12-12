package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import classes.Agenda;

public interface IServidor extends Remote{
	
	public String registraAgenda(String n) throws RemoteException;
	
	public boolean consultaAgenda(String n) throws RemoteException;

}
