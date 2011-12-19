package classes;

import interfaces.IAgenda;
import interfaces.IServidor;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Servidor extends UnicastRemoteObject implements IServidor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TreeMap<String,String> agendas;

	protected Servidor() throws RemoteException {
		super();
		agendas = new TreeMap<String, String>();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			Servidor s = new Servidor();
			String ip = InetAddress.getLocalHost().getHostAddress();
			Naming.bind("rmi://"+ip+"/servidor", s);
		} catch (Exception E) {

		}
	}

	@Override
	public String registraAgenda(String n,String ip) {
		if (agendas.containsKey(n)) {
			return "Nome existente!";
		}
		agendas.put(n,ip);
		return "Sucesso!";
	}

	@Override
	public IAgenda consultaAgenda(String n) throws RemoteException {
		IAgenda ia = null;
		try {
			if (agendas.containsKey(n)) {
				ia = (IAgenda) Naming.lookup("rmi://"+agendas.get(n)+"/"+n);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ia;
	}

	@Override
	public void excluiAgenda(String n) throws RemoteException {
		if (agendas.containsKey(n)) {
			agendas.remove(n);
		}

	}

	@Override
	public ArrayList<String> listarAgendas() throws RemoteException {
		ArrayList<String> lista = new ArrayList<String>();
		for (String string : agendas.keySet()) {
			lista.add(string);
		}
		return lista;
	}

}
