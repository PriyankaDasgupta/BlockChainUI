package main.java.simplebean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlInputText;

@ManagedBean(eager=true, name="employmentBean")
@RequestScoped
public class EmploymentBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String organization = "IBM";
	private String doj = "2011-01-20";
	private String designation = "Software Engineer";
	private String skillSet = "Java, Struts, Oracle";
	private String certification = "OCJP";
	private String salary = "Rs. 10000";
	private String dol = "2012-04-09";
	
	private HtmlInputText inputOrg = new HtmlInputText();
	private HtmlInputText inputDoj = new HtmlInputText();
	private HtmlInputText inputDesignation = new HtmlInputText();
	private HtmlInputText inputSkillSet = new HtmlInputText();
	private HtmlInputText inputCertification = new HtmlInputText();
	private HtmlInputText inputSalary = new HtmlInputText();
	private HtmlInputText inputDol = new HtmlInputText();
	
	public EmploymentBean() {}

	@PostConstruct
	public void init() {
		System.out.println("default employment details in employment details");
		inputOrg.setValue(organization);
		inputDoj.setValue(doj);
		inputDesignation.setValue(designation);
		inputSkillSet.setValue(skillSet);
		inputCertification.setValue(certification);
		inputSalary.setValue(salary);
		inputDol.setValue(dol);
	}
	
	public String getOrganization() {
		return (String)inputOrg.getValue();
	}

	public void setOrganization(String organization) {
		inputOrg.setValue(organization);
	}

	public String getDoj() {
		return (String)inputDoj.getValue();
	}

	public void setDoj(String doj) {
		inputDoj.setValue(doj);
	}

	public String getDesignation() {
		return (String)inputDesignation.getValue();
	}

	public void setDesignation(String designation) {
		inputDesignation.setValue(designation);
	}

	public String getSkillSet() {
		return (String)inputSkillSet.getValue();
	}

	public void setSkillSet(String skillSet) {
		inputSkillSet.setValue(skillSet);
	}

	public String getCertification() {
		return (String)inputCertification.getValue();
	}

	public void setCertification(String certification) {
		inputCertification.setValue(certification);
	}

	public String getSalary() {
		return (String)inputSalary.getValue();
	}

	public void setSalary(String salary) {
		inputSalary.setValue(salary);
	}

	public String getDol() {
		return (String)inputDol.getValue();
	}

	public void setDol(String dol) {
		inputDol.setValue(dol);
	}
	
	public HtmlInputText getInputOrg() {
		return inputOrg;
	}

	public void setInputOrg(HtmlInputText inputOrg) {
		this.inputOrg = inputOrg;
	}
	
	public HtmlInputText getInputDoj() {
		return inputDoj;
	}

	public void setInputDoj(HtmlInputText inputDoj) {
		this.inputDoj = inputDoj;
	}
	
	public HtmlInputText getInputDesignation() {
		return inputDesignation;
	}

	public void setInputDesignation(HtmlInputText inputDesignation) {
		this.inputDesignation = inputDesignation;
	}
	
	public HtmlInputText getInputSkillSet() {
		return inputSkillSet;
	}

	public void setInputSkillSet(HtmlInputText inputSkillSet) {
		this.inputSkillSet = inputSkillSet;
	}
	
	public HtmlInputText getInputCertification() {
		return inputCertification;
	}

	public void setInputCertification(HtmlInputText inputCertification) {
		this.inputCertification = inputCertification;
	}
	
	public HtmlInputText getInputSalary() {
		return inputSalary;
	}

	public void setInputSalary(HtmlInputText inputSalary) {
		this.inputSalary = inputSalary;
	}
	
	public HtmlInputText getInputDol() {
		return inputDol;
	}

	public void setInputDol(HtmlInputText inputDol) {
		this.inputDol = inputDol;
	}
}
