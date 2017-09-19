package main.java.simplebean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;

@ManagedBean(eager=true, name="simpleLogin")
@SessionScoped
public class SimpleLogin implements Serializable
{
	String userid;
	String password;
	HtmlInputText textid = new HtmlInputText();
	
	public SimpleLogin() {}

	public String getUserid() {
		//return userid;
		return (String)textid.getValue();
	}

	public void setUserid(String userid) {
		//this.userid = userid;
		textid.setValue(userid);
		
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
