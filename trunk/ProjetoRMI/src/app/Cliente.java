package app;

import interfaces.IServidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.TreeMap;

import classes.*;
import interfaces.*;

public class Cliente {

	/**
	 * @param args
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 * @throws ParseException 
	 */
	public void fazerConvite(IServidor s, Agenda a) throws RemoteException, MalformedURLException, NotBoundException, ParseException {
		int contador = 0;
		Evento ev = new Evento();
		ArrayList<Contato> c = new ArrayList<Contato>();
		Scanner entrada = new Scanner(System.in); 
		System.out.println("Digite o Contato que você deseja convidar (em branco para sair): ");
		String nome = entrada.nextLine();
		TreeMap<String, Contato> contatoTree = a.getContatos();
		
		while (nome != "") {
			c.add(contatoTree.get(nome));
			System.out.println("Digite o Contato que você deseja convidar (em branco para sair): ");
			nome = entrada.nextLine();
		}
		try {
		ev.setContatos(c);
		System.out.println("Digite a descrição do Evento: ");
		ev.setDescricao(entrada.nextLine());
		
		
		   System.out.println("Digite a data do Evento: (Formato: dd/MM/yyyy HH:mm:ss) ");
		   String dataString = entrada.nextLine();
		   Date data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dataString);
		   ev.setData(data);

		for (int i = 0; i < c.size(); i++) {
			Contato cliente = c.get(i);
		
			if (s.consultaAgenda(cliente.getNome())) {
	
				IAgenda agenda = (IAgenda) Naming.lookup(cliente.getNome());
				//Se retorno for true, adiciona
				if(agenda.adicionarEvento(ev)) {
					a.addEventos(ev);
					contador++;
				}
			}
			else {
				System.out.println("Contato "+ cliente.getNome() + " não existe!");
			}
		}
		if (contador == 0) {
			System.out.println("Nenhum evento foi adicionado, os contatos recusaram o convite ou já estavam ocupados");
		}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		try {
			Agenda a = new Agenda();
			Contato c = new Contato();
			Scanner entrada = new Scanner(System.in);
			System.out.println("Digite seu nome:");
			c.setNome(entrada.nextLine());
			System.out.println("Digite seu email:");
			c.setEmail(entrada.nextLine());
			a.setUsuario(c);
			IServidor s = (IServidor) Naming.lookup("servidor");
			if(s.consultaAgenda(c.getNome())){
				System.out.println("Agenda com o mesmo nome já existe!");
				System.exit(0);
			}
			else{
				Naming.rebind(c.getNome(), a);
				s.registraAgenda(c.getNome());
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
