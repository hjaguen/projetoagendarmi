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
		System.out.println("Digite o Contato que você deseja convidar: ");
		String nome = entrada.nextLine();
		TreeMap<String, Contato> contatoTree = a.getContatos();
		ArrayList<Contato> co = new ArrayList<Contato>();

		
		//Obtém nome dos Contatos para serem enviados os convites
		while (nome != "" || c.size() == 0) {
			if((contatoTree.get(nome)) != null) {
				
				c.add(contatoTree.get(nome));
		}
			else System.out.println("Contato inexistente");
			
			System.out.println("Digite o Contato que você deseja convidar (em branco para sair): ");
			nome = entrada.nextLine();
		}
		try {
			//Obtém a descrição do evento e adiciona ao evento
		System.out.println("Digite a descrição do Evento: ");
		ev.setDescricao(entrada.nextLine());
		
		//Obtém a data do evento e adiciona ao evento
		   System.out.println("Digite a data do Evento: (Formato: dd/MM/yyyy HH:mm:ss) ");
		   String dataString = entrada.nextLine();
		   Date data = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dataString);
		   ev.setData(data);

		   //Roda a lista de contatos pra quem vc escolheu enviar os convites
		for (int i = 0; i <= c.size(); i++) {
			Contato cliente = c.get(i);
		//Se a Agenda dele estiver rodando, continua
			if (s.consultaAgenda(cliente.getNome()) == true) {
	
				IAgenda agenda = (IAgenda) Naming.lookup(cliente.getNome());
				//Se o usuário aceitar o convite, adiciona a uma lista onde estarão os contatos que aceitaram o convite
				if(agenda.adicionarEvento(ev) == true) {
					co.add(cliente);
					contador++;
				}
				else
					System.out.println("Contato: " + cliente.getNome() + " rejeitou seu convite");
			}
			else {
				System.out.println("Contato "+ cliente.getNome() + " não tem uma Agenda!");
			}
		}
		//Se não ninguém aceitou o convite, manda mensagem
		if (contador == 0) {
			System.out.println("Nenhum evento foi adicionado, os contatos recusaram o convite ou já estavam ocupados");
		}
		else
			//Se alguém aceitou o convite, adiciona o usuário que fez os convites também na lista de contatos
			//Seta todos os contatos no evento
			co.add(a.getUsuario());
			ev.setContatos(co);
			//Itera a lista de contatos e adiciona o evento em cada um deles
			for (int i=0; i <= co.size(); i++) {
				
				Contato cliente = co.get(i);

				if (cliente != a.getUsuario()) {
				IAgenda agenda = (IAgenda) Naming.lookup(cliente.getNome());
				agenda.addEventos(ev);
				}
			}
			a.addEventos(ev);
			System.out.println("Contatos foram adicionados com sucesso ao evento!");
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
