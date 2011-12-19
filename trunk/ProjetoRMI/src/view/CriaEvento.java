package view;

import interfaces.IAgenda;
import interfaces.ICliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.Executors;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import threads.RespostaThread;
import classes.Contato;
import classes.Evento;

import com.toedter.calendar.JTextFieldDateEditor;

public class CriaEvento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Cliente pai;
	private JList listContatos;
	private JTextFieldDateEditor textFieldDateEditorData;
	private TextArea textAreaDescricao;
	private JTextFieldDateEditor textFieldDateEditorHoraInicio;
	private JTextFieldDateEditor textFieldDateEditorHoraFim;

	/**
	 * Create the dialog.
	 */
	public CriaEvento(Cliente p) {
		pai = p;
		setTitle("Criar Evento");
		setBounds(100, 100, 449, 448);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textFieldDateEditorData = new JTextFieldDateEditor();
		textFieldDateEditorData.setBounds(10, 37, 93, 20);
		contentPanel.add(textFieldDateEditorData);
		
		Label lblData = new Label("Data");
		lblData.setFont(new Font("Dialog", Font.BOLD, 12));
		lblData.setBounds(10, 9, 62, 22);
		contentPanel.add(lblData);
		
		Label lblHoraInicio = new Label("Hora de In\u00EDcio");
		lblHoraInicio.setFont(new Font("Dialog", Font.BOLD, 12));
		lblHoraInicio.setBounds(10, 66, 93, 22);
		contentPanel.add(lblHoraInicio);
		
		textFieldDateEditorHoraInicio = new JTextFieldDateEditor();
		textFieldDateEditorHoraInicio.setDateFormatString("HH:mm");
		textFieldDateEditorHoraInicio.setBounds(10, 94, 93, 20);
		contentPanel.add(textFieldDateEditorHoraInicio);
		
		Label lblHoraTermino = new Label("Hora de T\u00E9rmino");
		lblHoraTermino.setFont(new Font("Dialog", Font.BOLD, 12));
		lblHoraTermino.setBounds(119, 66, 108, 22);
		contentPanel.add(lblHoraTermino);
		
		textFieldDateEditorHoraFim = new JTextFieldDateEditor();
		textFieldDateEditorHoraFim.setDateFormatString("HH:mm");
		textFieldDateEditorHoraFim.setBounds(119, 94, 108, 20);
		contentPanel.add(textFieldDateEditorHoraFim);
		
		Label lblContatos = new Label("Contatos");
		lblContatos.setFont(new Font("Dialog", Font.BOLD, 12));
		lblContatos.setBounds(246, 9, 62, 22);
		contentPanel.add(lblContatos);
		
		DefaultListModel model = new DefaultListModel();
		listContatos = new JList(model);
		listContatos.setBorder(new LineBorder(new Color(0, 0, 0)));
		listContatos.setBounds(246, 37, 177, 320);
		contentPanel.add(listContatos);
		
		textAreaDescricao = new TextArea();
		textAreaDescricao.setBounds(10, 130, 217, 237);
		contentPanel.add(textAreaDescricao);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							String data = getTextFieldDateEditorData().getText();
							String horaInicio = getTextFieldDateEditorHoraInicio().getText();
							String horaFim = getTextFieldDateEditorHoraFim().getText();
							DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
							Date dataInicio = (Date) formatter.parse(data+" "+horaInicio);
							Date dataFim = (Date) formatter.parse(data+" "+horaFim);
							ArrayList<String> res = pai.getAgenda().consultarDisponibilidade(dataInicio,dataFim,listContatos.getSelectedValues());
							if (res.size() > 0){
								String msg = "O(s) Contato(s) a seguir: ";
								for (String string : res) {
									msg+=string+" ";
								}
								msg+="já possuem compromissos agendados nesse horário";
								JOptionPane.showMessageDialog(pai.ce, msg);
							}
							else if(listContatos.getSelectedIndices().length==0){
								JOptionPane.showMessageDialog(pai.ce, "Ao menos um contato deve ser selecionado!");
							}
							else{
								Evento e = new Evento();
								e.setData(dataInicio);
								e.setHoraInicio(dataInicio);
								e.setHoraFim(dataFim);
								e.setDescricao(getTextAreaDescricao().getText());
								TreeMap<String, Contato> tmp = new TreeMap<String, Contato>();
								for (Object val : listContatos.getSelectedValues()) {
									String nome = (String) val;
									if(pai.getServidor().consultaAgenda(nome) != null){
										IAgenda ia = (IAgenda)Naming.lookup(nome);
										tmp.put(nome, ia.getUsuario());
									}
								}
								e.setContatos(tmp);
								RespostaThread r = new RespostaThread(e, pai, tmp.size());								
								new Thread(r).start();
								pai.ce.setVisible(false);
							}
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							JOptionPane.showMessageDialog(pai.ce, "Preenche corretamente os campos de hora!");
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NotBoundException e) {
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
						pai.ce.setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
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

	public void setListContatos(JList listContatos) {
		this.listContatos = listContatos;
	}

	public JList getListContatos() {
		return listContatos;
	}

	public void setTextFieldDateEditorData(JTextFieldDateEditor textFieldDateEditorData) {
		this.textFieldDateEditorData = textFieldDateEditorData;
	}

	public JTextFieldDateEditor getTextFieldDateEditorData() {
		return textFieldDateEditorData;
	}

	public void setTextAreaDescricao(TextArea textAreaDescricao) {
		this.textAreaDescricao = textAreaDescricao;
	}

	public TextArea getTextAreaDescricao() {
		return textAreaDescricao;
	}

	public JTextFieldDateEditor getTextFieldDateEditorHoraInicio() {
		return textFieldDateEditorHoraInicio;
	}

	public void setTextFieldDateEditorHoraInicio(
			JTextFieldDateEditor textFieldDateEditorHoraInicio) {
		this.textFieldDateEditorHoraInicio = textFieldDateEditorHoraInicio;
	}

	public JTextFieldDateEditor getTextFieldDateEditorHoraFim() {
		return textFieldDateEditorHoraFim;
	}

	public void setTextFieldDateEditorHoraFim(
			JTextFieldDateEditor textFieldDateEditorHoraFim) {
		this.textFieldDateEditorHoraFim = textFieldDateEditorHoraFim;
	}
	
	
}
