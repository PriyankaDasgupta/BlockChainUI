package main.java.restbean;

import org.springframework.stereotype.Component;

@Component("education")
public class Education{
	
	int id;
	String degree;
	String university;
	int marks;
	String grade;
	String yop;
	
	public Education(int id, String degree, String university, String grade, String yop, int marks) {
		super();
		this.id = id;
		this.degree = degree;
		this.university = university;
		this.grade = grade;
		this.yop = yop;
		this.marks = marks;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}	
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getYop() {
		return yop;
	}
	public void setYop(String yop) {
		this.yop = yop;
	}	
}