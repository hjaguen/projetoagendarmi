package view;

import interfaces.IServidor;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextArea;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import classes.Agenda;
import classes.Contato;

import com.toedter.calendar.JCalendar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.List;

public class Cliente {

	private JFrame frame;
	private Agenda agenda;
	private IServidor servidor;
	public CriaContato cc;
	private List listContatos;

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
					
					if (s.consultaAgenda(c.getNome())) {
						System.out
								.println("Agenda com o mesmo nome já existe!");
						System.exit(0);
					} else {
						window.setAgenda(a);
						window.setServidor(s);
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
	public Cliente() {
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

		JCalendar calendar = new JCalendar();
		calendar.setBounds(10, 11, 191, 153);
		frame.getContentPane().add(calendar);
		
		cc = new CriaContato(this);

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
					String[] c = listContatos.getSelectedItems();
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

		TextArea textAreaEventos = new TextArea();
		textAreaEventos.setBounds(218, 256, 440, 174);
		frame.getContentPane().add(textAreaEventos);
		
		listContatos = new List();
		listContatos.setMultipleSelections(true);
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
			public void windowClosing(WindowEvent e){
				try {
					servidor.excluiAgenda(agenda.getUsuario().getNome());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	public void listarContatos(){
		listContatos.removeAll();
		for (Contato c : agenda.getContatos().values()) {
			listContatos.add(c.getNome());
		}
		this.frame.repaint();
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Agenda getAgenda() {
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

	public void setListContatos(List listContatos) {
		this.listContatos = listContatos;
	}

	public List getListContatos() {
		return listContatos;
	}
}
