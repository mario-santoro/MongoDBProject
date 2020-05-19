package bean;

import java.util.ArrayList;

public class Person {
	private String PersonSex;
	private String PersonRazza;
	private ArrayList<String> rangePersonAge;

	public Person() {
		rangePersonAge= new ArrayList<String>();

	}
	public Person(String PersonSex, String PersonRazza, ArrayList<String> rangePersonAge) {
		this.PersonSex= PersonSex;
		this.PersonRazza=PersonRazza;
		this.rangePersonAge=rangePersonAge;

	}
	public String getPersonSex() {
		return PersonSex;
	}
	public void setPersonSex(String personSex) {
		PersonSex = personSex;
	}
	public String getPersonRazza() {
		return PersonRazza;
	}
	public void setPersonRazza(String personRazza) {
		PersonRazza = personRazza;
	}
	public ArrayList<String> getRangePersonAge() {
		return rangePersonAge;
	}
	public void setRangePersonAge(ArrayList<String> rangePersonAge) {
		this.rangePersonAge = rangePersonAge;
	}


}
