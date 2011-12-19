package view;

import interfaces.IAgenda;
import interfaces.ICliente;
import interfaces.IServidor;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import threads.RespostaThread;

import classes.Agenda;
import classes.Contato;

import com.toedter.calendar.JCalendar;

public class Cliente extends UnicastRemoteObject implements ICliente{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private IAgenda agenda;
	private IServidor servidor;
	public CriaContato cc;
	public CriaEvento ce;
	private JList listContatos;
	private JCalendar calendar;
	private TextArea textAreaEventos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IServidor s = (IServidor) Naming.lookup("servidor");
					Cliente window = new Cliente();
					Agenda a = new Agenda();
					Contato c = new Contato();
					Scanner entrada = new Scanner(System.in);
					System.out.println("Digite seu nome:");
					c.setNome(entrada.nextLine());
					System.out.println("Digite seu email:");
					c.setEmail(entrada.nextLine());
					a.setUsuario(c);

					if (s.consultaAgenda(c.getNome())!=null) {
						System.out
								.println("Agenda com o mesmo nome já existe!");
						System.exit(0);
					} else {
						window.setAgenda(a);
						a.setCliente(window);
						window.setServidor(s);
						window.getFrame().setTitle(c.getNome());
						Naming.rebind(c.getNome(), a);
						s.registraAgenda(c.getNome());
					}

					window.frame.setVisible(true);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Cliente() throws RemoteException{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 684, 479);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		calendar = new JCalendar();
		calendar.setBounds(10, 11, 191, 153);
		frame.getContentPane().add(calendar);

		cc = new CriaContato(this);
		ce = new CriaEvento(this);

		JButton btnAdicionarContato = new JButton("Adicionar");
		btnAdicionarContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					cc.listarContatos(servidor.listarAgendas());
					cc.setVisible(true);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnAdicionarContato.setBounds(10, 199, 91, 23);
		frame.getContentPane().add(btnAdicionarContato);

		JButton btnCriaEvento = new JButton("Criar Evento");
		btnCriaEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ce.getListContatos().setModel(listContatos.getModel());
				ce.getListContatos().setSelectedIndices(
						listContatos.getSelectedIndices());
				Date data = calendar.getDate();
				ce.getTextFieldDateEditorData().setDate(data);
				ce.getTextAreaDescricao().setText(textAreaEventos.getText());
				ce.setVisible(true);
			}
		});

		btnCriaEvento.setBounds(546, 227, 112, 23);
		frame.getContentPane().add(btnCriaEvento);

		JButton btnDetalhes = new JButton("Exibir Detalhes");
		btnDetalhes.setBounds(517, 11, 141, 23);
		frame.getContentPane().add(btnDetalhes);

		JLabel lblContatos = new JLabel("Contatos");
		lblContatos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblContatos.setBounds(10, 175, 91, 19);
		frame.getContentPane().add(lblContatos);

		JButton btnExcluiContato = new JButton("Excluir");
		btnExcluiContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Object[] c = listContatos.getSelectedValues();
					agenda.removerContatos(c);
					listarContatos();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnExcluiContato.setBounds(110, 199, 91, 23);
		frame.getContentPane().add(btnExcluiContato);

		textAreaEventos = new TextArea();
		textAreaEventos.setBounds(218, 256, 440, 174);
		frame.getContentPane().add(textAreaEventos);

		DefaultListModel model = new DefaultListModel();
		listContatos = new JList(model);
		listContatos.setBorder(new LineBorder(new Color(0, 0, 0)));
		listContatos.setBounds(10, 256, 191, 174);
		frame.getContentPane().add(listContatos);

		JLabel lblEventos = new JLabel("Eventos do Dia");
		lblEventos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEventos.setBounds(220, 11, 112, 19);
		frame.getContentPane().add(lblEventos);

		List list = new List();
		list.setBounds(218, 40, 440, 165);
		frame.getContentPane().add(list);

		JButton btnListarEventos = new JButton("Listar Eventos");
		btnListarEventos.setBounds(47, 227, 125, 23);
		frame.getContentPane().add(btnListarEventos);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					servidor.excluiAgenda(agenda.getUsuario().getNome());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public void listarContatos() {
		DefaultListModel model = (DefaultListModel) listContatos.getModel();
		model.removeAllElements();
		try {
			for (Contato c : agenda.getContatos().values()) {
				model.addElement(c.getNome());
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.frame.repaint();
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public IAgenda getAgenda() {
		return agenda;
	}

	public IServidor getServidor() {
		return servidor;
	}

	public void setServidor(IServidor servidor) {
		this.servidor = servidor;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void setListContatos(JList listContatos) {
		this.listContatos = listContatos;
	}

	public JList getListContatos() {
		return listContatos;
	}

	public JCalendar getCalendar() {
		return calendar;
	}

	public void setCalendar(JCalendar calendar) {
		this.calendar = calendar;
	}

	public void setTextAreaEventos(TextArea textAreaEventos) {
		this.textAreaEventos = textAreaEventos;
	}

	public TextArea getTextAreaEventos() {
		return textAreaEventos;
	}

	@Override
	public void atualizar() throws RemoteException {
		frame.repaint();
	}

	@Override
	public int responderConvite(String msg) throws RemoteException {
		return JOptionPane.showConfirmDialog(frame, msg,"Responder Convite", JOptionPane.YES_NO_OPTION);
	}
	
	

}
