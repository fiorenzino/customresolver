package weld.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import weld.model.attachment.Immagine;
import weld.model.type.CategoriaAttivita;

@Entity
public class Attivita implements Serializable {

	private String id;
	private Date data;
	private String autore;
	private CategoriaAttivita categoria;
	private String indirizzo;
	private String city;
	private String provincia;
	private String telefono;
	private String fax;
	private String orariEchiusura;
	private String email;
	private String sitoInternet;
	private String descrizione;
	private Immagine immagine;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne
	public CategoriaAttivita getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaAttivita categoria) {
		this.categoria = categoria;
	}

	@Lob
	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	@Lob
	public String getOrariEchiusura() {
		return orariEchiusura;
	}

	public void setOrariEchiusura(String orariEchiusura) {
		this.orariEchiusura = orariEchiusura;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSitoInternet() {
		return sitoInternet;
	}

	public void setSitoInternet(String sitoInternet) {
		this.sitoInternet = sitoInternet;
	}

	@Lob
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@OneToOne
	public Immagine getImmagine() {
		return immagine;
	}

	public void setImmagine(Immagine immagine) {
		this.immagine = immagine;
	}

}
