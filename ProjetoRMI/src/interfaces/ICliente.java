package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import threads.RespostaThread;

public interface ICliente extends Remote{
	
	public IServidor getServidor() throws RemoteException;
	
	public void atualizar() throws RemoteException;
	
	public int responderConvite(String msg) throws RemoteException;

}
