package classes;

import interfaces.IAgenda;
import interfaces.IServidor;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Servidor extends UnicastRemoteObject implements IServidor{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> agendas;

	protected Servidor() throws RemoteException {
		super();
		agendas = new ArrayList<String>();
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
	public String registraAgenda(String n){
		if (agendas.contains(n)){
			return "Nome existente!";
		}
		agendas.add(n);
		return "Sucesso!";
	}

	@Override
	public boolean consultaAgenda(String n) throws RemoteException {
		if (agendas.contains(n))
			return true;
		return false;
	}

	@Override
	public void excluiAgenda(String n) throws RemoteException {
		if (agendas.contains(n)){
			agendas.remove(n);
		}
		
	}

	@Override
	public ArrayList<String> listarAgendas() throws RemoteException {
		return agendas;
	}

}
