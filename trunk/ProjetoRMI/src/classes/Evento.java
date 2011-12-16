package classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Evento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String descricao;
	private Date data;
	private List<Contato> contatos;
	
	public Evento(){
		contatos = new ArrayList<Contato>();
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

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}
	public void addContato(Contato c) {
		contatos.add(c);
	}

}
