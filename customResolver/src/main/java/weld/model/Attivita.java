package weld.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Attivita implements Serializable {

	private Long id;
	private String foto;
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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
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

}
