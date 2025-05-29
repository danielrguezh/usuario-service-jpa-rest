package es.danielrguezh.entites;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name", nullable = false)
	private String name;

    /**
     * constructor vacio
     */
	public Rol() {
	}
	
    /**
     * constructor con el nombre
     * @param name
     */
	public Rol(String name) {
		this.name = name;
	}

	/**
	 * Constructor con id y nombre
	 * @param id
	 * @param name
	 */
	public Rol(int id, String name) {
		this.id = id;
		this.name = name;
	}

	
    //getters y setters
	public int getId() {
		return id;
	}
	
    public void setId(int id) {
		this.id = id;
	}
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", name=" + name + "]";
	}

}
