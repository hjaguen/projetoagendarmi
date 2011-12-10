package classes;

import interfaces.IServidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Servidor extends UnicastRemoteObject implements IServidor{

	protected Servidor() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registraAgenda() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Agenda retornaAgenda() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
