package cn.goktech.entity;

public class Student {
	private int age;
	private String name;
	private String disc;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisc() {
		return disc;
	}
	public void setDisc(String disc) {
		this.disc = disc;
	}
	public Student() {
		
	}
	public Student(int age, String name, String disc) {
		this.age = age;
		this.name = name;
		this.disc = disc;
	}
}
