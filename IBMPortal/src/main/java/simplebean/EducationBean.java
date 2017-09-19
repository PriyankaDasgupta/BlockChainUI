package main.java.simplebean;

import java.util.ArrayList;
import java.util.List;

public class EducationBean 
{
	private String degree;
	private String grade;
	private String marks;
	private String university;
	private String yop;
	
	private List<EducationBean> eduDetails;
	
	public EducationBean() {
		eduDetails = new ArrayList<EducationBean>();
	}
	
	public EducationBean(String degree, String grade, String marks, String university, String yop)
	{
		this.degree = degree;
		this.grade = grade;
		this.marks = marks;
		this.university = university;
		this.yop = yop;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getMarks() {
		return marks;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getYop() {
		return yop;
	}

	public void setYop(String yop) {
		this.yop = yop;
	}

	public List<EducationBean> getEduDetails() {
		return eduDetails;
	}

	public void setEduDetails(List<EducationBean> eduDetails) {
		this.eduDetails = eduDetails;
	}
}
