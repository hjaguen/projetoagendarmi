package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class Evento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String descricao;
	private Date data;
	private Date horaInicio;
	private Date horaFim;
	private TreeMap<String, Contato> contatos;
	
	public Evento(){
		contatos = new TreeMap<String, Contato>();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public TreeMap<String, Contato> getContatos() {
		return contatos;
	}

	public void setContatos(TreeMap<String, Contato> contatos) {
		this.contatos = contatos;
	}
	public void addContato(Contato c) {
		contatos.put(c.getNome(), c);
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraFim(Date horaFim) {
		this.horaFim = horaFim;
	}

	public Date getHoraFim() {
		return horaFim;
	}
	
	

}
