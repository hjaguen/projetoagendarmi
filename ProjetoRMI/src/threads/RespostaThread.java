package threads;

import interfaces.IAgenda;
import interfaces.ICliente;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;

import view.Cliente;
import classes.Contato;
import classes.Evento;

public class RespostaThread implements Runnable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Cliente criador;
	Evento evento;
	int tamanho;
	TreeMap<String, Integer> respostas;
	Thread thr;

	public RespostaThread(Evento e, Cliente c, int t) {
		evento = e;
		this.criador = c;
		tamanho = t;
		respostas = new TreeMap<String, Integer>();
	}

	public void confirmaEvento(Evento e) {
		int resp = JOptionPane.showConfirmDialog(
				criador.getCalendar(),
				"Você confirma a realização do seguinte evento?\n"
						+ e.getDescricao(), "Confirma Evento",
				JOptionPane.YES_NO_OPTION);
		if (resp == 0) {

			try {
				criador.getAgenda().addEventos(e);
				criador.getAgenda().confirmarEvento(e);
				criador.getFrame().repaint();				
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void run() {
		thr = Thread.currentThread();
		ExecutorService tpes = Executors.newCachedThreadPool();
		try {
			
			for (String key : evento.getContatos().keySet()) {
				String msg = "O usuario "
						+ criador.getAgenda().getUsuario().getNome()
						+ "deseja iniciar um evento com voce.\n";
				msg += "Descrição: " + evento.getDescricao();
				
				IAgenda ia = criador.getServidor().consultaAgenda(key);
				ICliente cli = (ICliente) ia.getCliente();
				
				EnvioConviteThread ect = new EnvioConviteThread(cli, msg, key, this);
				tpes.execute(ect);				
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		tpes.shutdown();
		while (respostas.size() < tamanho) {
			thr.interrupt();		
		}

		TreeMap<String, Contato> aux = new TreeMap<String, Contato>();

		for (String key : respostas.keySet()) {
			if (respostas.get(key) == 0)
				aux.put(key, evento.getContatos().get(key));
		}

		evento.setContatos(aux);

		confirmaEvento(evento);

	}

	public Cliente getCriador() {
		return criador;
	}

	public void setCriador(Cliente criador) {
		this.criador = criador;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public TreeMap<String, Integer> getRespostas() {
		return respostas;
	}

	public void setRespostas(TreeMap<String, Integer> respostas) {
		this.respostas = respostas;
	}
	
	public void addResposta(String r,Integer i){
		respostas.put(r, i);
	}
	
	public boolean terminou(){
		return true;
	}

}
