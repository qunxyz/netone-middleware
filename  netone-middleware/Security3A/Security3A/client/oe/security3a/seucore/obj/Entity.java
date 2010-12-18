package oe.security3a.seucore.obj;

public class Entity implements java.io.Serializable {
	private Long id;
	/**
	 * –’√˚
	 */
	private String name;
	/**
	 * ’ ∫≈
	 */
	private String description;
	/**
	 * –’√˚∆¥“Ù
	 */
	private String naturalname;

	public Entity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getDescription() {

		return description;
	}

	public String getNaturalname() {
		return naturalname;
	}

	public void setNaturalname(String naturalname) {
		this.naturalname = naturalname;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return this.getName() + '(' + this.getNaturalname() + ')';
	}

}
