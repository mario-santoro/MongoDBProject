package bean;

import java.util.ArrayList;

public class Filtro {
	private String state;
	private String mounth;
	private ArrayList<Integer> rangeYears;
	private Person v;
	private Person a;
	private String relationship;
	private String weapon;

	public Filtro() {}
	public Filtro(String state, String mounth,  ArrayList<Integer> rangeYears, Person v, Person a, String relationship, String weapon) {
		this.state=state;
		this.mounth=mounth;
		this.rangeYears=rangeYears;
		this.v=v;
		this.a=a;
		this.relationship=relationship;
		this.weapon=weapon;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getMounth() {
		return mounth;
	}
	public void setMounth(String mounth) {
		this.mounth = mounth;
	}
	public  ArrayList<Integer> getRangeYears() {
		return rangeYears;	
	}
	public void setRangeYears( ArrayList<Integer> rangeYears) {
		this.rangeYears = rangeYears;
	}
	public Person getV() {
		return v;
	}
	public void setV(Person v) {
		this.v = v;
	}
	public Person getA() {
		return a;
	}
	public void setA(Person a) {
		this.a = a;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getWeapon() {
		return weapon;
	}
	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}



}
