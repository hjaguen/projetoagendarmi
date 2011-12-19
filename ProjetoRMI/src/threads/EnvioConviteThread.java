package threads;

import interfaces.ICliente;

import java.io.Serializable;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

public class EnvioConviteThread implements Runnable, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ICliente cliente;
	private String msg,nome;
	private RespostaThread rt;

	
	public EnvioConviteThread(ICliente c,String m,String n,RespostaThread r){
		cliente = c;
		msg = m;
		nome = n;
		rt = r;
	}
	
	public void respostaConvite(String nome, Integer resp) {
		if (resp == 0){
			JOptionPane.showMessageDialog(rt.getCriador().getFrame(), "O usuário " + nome
					+ " aceitou o convite");
		}
		else{
			JOptionPane.showMessageDialog(rt.getCriador().getFrame(), "O usuário " + nome
					+ " declinou o convite");
		}
		rt.addResposta(nome, resp);
	}

	@Override
	public void run() {
		int resp;
		try {
			resp = cliente.responderConvite(msg);
			respostaConvite(nome, resp);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
