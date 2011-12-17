package view;

import interfaces.IServidor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextArea;
import java.awt.Label;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JList;
import java.awt.List;

public class CriaContato extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Cliente pai;
	private List listContatos;

	/**
	 * Create the dialog.
	 */
	
	public CriaContato(Cliente p) {
		pai = p;
		setTitle("Adicionar Contato");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		Label label = new Label("Contatos Dispon\u00EDveis");
		label.setFont(new Font("Dialog", Font.BOLD, 14));
		label.setBounds(10, 10, 164, 22);
		contentPanel.add(label);
		
		listContatos = new List();
		listContatos.setMultipleSelections(true);
		listContatos.setBounds(10, 38, 414, 181);
		contentPanel.add(listContatos);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {						
						try {
							String[] c = listContatos.getSelectedItems();
							pai.getAgenda().adicionarContatos(c);
							pai.listarContatos();
							pai.cc.setVisible(false);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						pai.cc.setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	}
	
	public void listarContatos(ArrayList<String> c){
		try {
			listContatos.removeAll();
			IServidor s = pai.getServidor();
			String nomePai = pai.getAgenda().getUsuario().getNome();
			for (String agenda : s.listarAgendas()) {
				if(!(nomePai.equals(agenda)) && !(pai.getAgenda().getContatos().containsKey(agenda)))
					listContatos.add(agenda);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void setPai(Cliente pai) {
		this.pai = pai;
	}

	public Cliente getPai() {
		return pai;
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}
}
