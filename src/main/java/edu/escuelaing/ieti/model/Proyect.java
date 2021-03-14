package edu.escuelaing.ieti.model;

public class Proyect {
	private String nombre;
	private String imagen;
	private User usuario;
	private String video;
	private String pais;
	private String descripcion;

	public Proyect() {

	}

	public Proyect(String nombre, String imagen, User usuario, String video, String pais, String descripcion) {
		this.nombre = nombre;
		this.imagen = imagen;
		this.usuario = usuario;
		this.video = video;
		this.pais = pais;
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
