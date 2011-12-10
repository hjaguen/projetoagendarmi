package classes;

import interfaces.IAgenda;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Agenda implements IAgenda{
	
	private List<Evento> eventos;
	
	public Agenda(){
		
		eventos = new ArrayList<Evento>();
		
	}

	@Override
	public void consultarDisponibilidade() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void adicionarEvento() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listarEventos() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
