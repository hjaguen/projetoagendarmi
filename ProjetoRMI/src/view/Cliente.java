package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JCalendarBeanInfo;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollBar;
import java.awt.TextArea;
import java.awt.Choice;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import java.awt.Font;

public class Cliente {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente window = new Cliente();
					window.frame.setVisible(true);
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
		
		JList list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setBounds(10, 232, 189, 198);
		frame.getContentPane().add(list);
		
		JButton btnAdicionarContato = new JButton("Adicionar");
		btnAdicionarContato.setBounds(10, 198, 77, 23);
		frame.getContentPane().add(btnAdicionarContato);
		
		JButton btnNewButton = new JButton("Criar Evento");
		btnNewButton.setBounds(424, 11, 112, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Listar Eventos");
		btnNewButton_1.setBounds(546, 11, 112, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblContatos = new JLabel("Contatos");
		lblContatos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblContatos.setBounds(10, 175, 91, 19);
		frame.getContentPane().add(lblContatos);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(103, 198, 77, 23);
		frame.getContentPane().add(btnExcluir);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(218, 54, 440, 376);
		frame.getContentPane().add(textArea);
	}
}
