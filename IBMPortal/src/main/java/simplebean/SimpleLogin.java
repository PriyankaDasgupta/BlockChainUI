package main.java.simplebean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;

@ManagedBean(name="simpleLogin")
@SessionScoped
public class SimpleLogin implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	String userid;
	String password;
	HtmlInputText textid = new HtmlInputText();
	
	public SimpleLogin() {
		this.userid = "";
		this.password = "";
	}
	
	@PostConstruct
	public void init() {
		String userId = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("userid");
		System.out.println("userId in init: " + userId);
		textid.setValue(this.userid);
		System.out.println("text id in init: " + textid.getValue());
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public HtmlInputText getTextid() {
		return textid;
	}

	public void setTextid(HtmlInputText textid) {
		this.textid = textid;
	}

	public String checkValidUser()
	{
		return "search";
	}
}
