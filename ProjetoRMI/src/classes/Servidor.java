package classes;

import interfaces.IAgenda;
import interfaces.IServidor;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.TreeMap;

public class Servidor extends UnicastRemoteObject implements IServidor{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Agenda> agendas;

	protected Servidor() throws RemoteException {
		super();
		agendas = new TreeMap<String, Agenda>();
		// TODO Auto-generated constructor stub
	}
	
	public static void main (String[] args){
		try{
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			Servidor s = new Servidor();
			Naming.bind("servidor", s);
		} catch (Exception E) {
			
		}
	}

	@Override
	public void registraAgenda(String n){
		if (agendas.containsKey(n)){
			System.out.println("Nome existente!");
			return;
		}
		Agenda a;
		try {
			a = new Agenda();
			agendas.put(n, a);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Agenda retornaAgenda() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
