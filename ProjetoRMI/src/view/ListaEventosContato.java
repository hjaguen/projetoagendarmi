package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import classes.Evento;

public class ListaEventosContato extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Cliente pai;
	private String nome;
	private List listEventos;

	/**
	 * Create the dialog.
	 */
	public ListaEventosContato(Cliente p) {
		pai = p;
		setBounds(100, 100, 719, 426);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		listEventos = new List();
		listEventos.setBounds(10, 11, 683, 333);
		contentPanel.add(listEventos);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public void setPai(Cliente pai) {
		this.pai = pai;
	}

	public Cliente getPai() {
		return pai;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setListEventos(List listEventos) {
		this.listEventos = listEventos;
	}

	public List getListEventos() {
		return listEventos;
	}

	public void exibirEventosUsuario(String n, ArrayList<Evento> evs) {
		setTitle("Lista de Eventos do Contato "+n);
		listEventos.removeAll();
		for (Evento ev : evs) {
			Calendar ctmp = Calendar.getInstance();
			Calendar ctmpfim = Calendar.getInstance();
			ctmp.setTime(ev.getHoraInicio());
			ctmpfim.setTime(ev.getHoraFim());
			int diatmp = ctmp.get(Calendar.DAY_OF_MONTH);
			int anotmp = ctmp.get(Calendar.YEAR);
			int mestmp = ctmp.get(Calendar.MONTH);
			int horaini = ctmp.get(Calendar.HOUR_OF_DAY);
			int horafim = ctmpfim.get(Calendar.HOUR_OF_DAY);
			int minfim = ctmpfim.get(Calendar.MINUTE);
			int minini = ctmp.get(Calendar.MINUTE);
			String linha = ev.getDescricao() + " - ";
			linha += "Data Inicio: " + diatmp + "/" + mestmp + "/" + anotmp + " " + horaini + ":" + minini + " | ";
			linha += "Data Fim: " + diatmp + "/" + mestmp + "/" + anotmp + " "  + horafim + ":" + minfim;
			listEventos.add(linha);

		}
		contentPanel.repaint();
	}
}
