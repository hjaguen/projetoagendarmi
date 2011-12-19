package view;

import interfaces.IServidor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class CriaContato extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Cliente pai;
	private JList listContatos;

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
		
		DefaultListModel model = new DefaultListModel();
		listContatos = new JList(model);
		listContatos.setBorder(new LineBorder(new Color(0, 0, 0)));
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
							Object[] c = listContatos.getSelectedValues();
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
			DefaultListModel model = (DefaultListModel) listContatos.getModel();
			model.removeAllElements();
			IServidor s = pai.getServidor();
			String nomePai = pai.getAgenda().getUsuario().getNome();
			for (String agenda : s.listarAgendas()) {
				if(!(nomePai.equals(agenda)) && !(pai.getAgenda().getContatos().containsKey(agenda)))
					model.addElement(agenda);
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
