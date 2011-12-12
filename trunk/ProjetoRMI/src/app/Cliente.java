package app;

import interfaces.IServidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import classes.Agenda;
import classes.Contato;

public class Cliente {

	/**
	 * @param args
	 */
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
