package miniNet;

import java.util.ArrayList;
import java.util.Scanner;

public class User {
	private String name;
	private String image;
	private String status;
	private char gender;
	private int age;
	private String state;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	Scanner sc = new Scanner(System.in);
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public char getGender() {
		return gender;
	}
	
	public void setGender(char gender) {
		this.gender = gender;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	
	public User(String name, char gender, int age, String state) {
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.state = state;
	}
	
	public User(String name, char gender, String image, int age, String state) {
		this(name,gender,age,state);
		this.image = image;
	}
	
	public User(String name, String status,char gender, int age, String state) {
		this(name,gender,age,state);
		this.status = status;
	}
	
	public User(String name, String image, String status,char gender, int age, String state) {
		this(name,gender,state,age,image);
		this.status = status;
	}

	@Override
	public String toString() {
		
		return name + ", \""  + image + "\", \"" + status + "\", " + gender + ", " + age
				+ ", " + state;
	}

	
	

}
