package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import classes.Agenda;

public interface IServidor extends Remote{
	
	public ArrayList<String> listarAgendas() throws RemoteException;
	
	public String registraAgenda(String n,String i) throws RemoteException;
	
	public void excluiAgenda (String n) throws RemoteException;
	
	public IAgenda consultaAgenda(String n) throws RemoteException;

}
