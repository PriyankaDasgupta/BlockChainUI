package main.java.simplebean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.format.datetime.DateFormatter;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@ManagedBean(eager=true, name="employmentDetails")
@SessionScoped
public class EmploymentDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value="#{simpleLogin}")
	private SimpleLogin simpleLogin;
	
	@ManagedProperty(value="#{searchUser}")
	private SearchUser searchUser;
	
	private String organization;
	private Date doj;
	private String designation;
	private String skillSet;
	private String certification;
	private String salary;
	private Date dol;
	private List<EmploymentDetails> empDetails;
	private int count;
	private boolean editMode;
	private boolean editMode1;
	private Integer rowCnt;
	
	private String companyName;
	private String loginId;
	private boolean companyMatch;
	
	public EmploymentDetails() {
		System.out.println("Calling constructor");
		editMode = true;
		editMode1 = false;
		companyMatch = false;
		rowCnt = 0;
		
		/*if (getSimpleLogin().getUserid().toLowerCase().contains("ibm"))
			companyName = "IBM";
		else if (getSimpleLogin().getUserid().toLowerCase().contains("cts") || getSimpleLogin().getUserid().toLowerCase().contains("cognizant"))
			companyName = "Cognizant";
		else if (getSimpleLogin().getUserid().toLowerCase().contains("tcs"))
			companyName = "TCS";
		else if (getSimpleLogin().getUserid().toLowerCase().contains("wipro"))
			companyName = "Wipro";*/
	}
	
	public EmploymentDetails(String org, Date doj, String desig, String skill, String cert, String sal, Date dol, int count, boolean companyMatch)
	{
		this.organization = org;
		this.doj = doj;
		this.designation = desig;
		this.skillSet = skill;
		this.certification = cert;
		this.salary = sal;
		this.dol = dol;
		this.count = count;
		this.companyMatch = companyMatch;
	}
	
	public String getEmploymentDetails() {
		empDetails = new ArrayList<EmploymentDetails>();
		
		if (!(getSearchUser().isVerificationStatus()))
			return "pendingVerification";
		
		if (!(getSearchUser().isCandidateExists()))
			return "noCandidateDetails";
		
		if (getSimpleLogin().getUserid().toLowerCase().contains("ibm"))
			this.companyName = "IBM";
		else if (getSimpleLogin().getUserid().toLowerCase().contains("cts") || getSimpleLogin().getUserid().toLowerCase().contains("cognizant"))
			this.companyName = "Cognizant";
		else if (getSimpleLogin().getUserid().toLowerCase().contains("tcs"))
			this.companyName = "TCS";
		else if (getSimpleLogin().getUserid().toLowerCase().contains("wipro"))
			this.companyName = "Wipro";
			
		Client client = Client.create();
		WebResource webResource = client.resource("https://edb67a1a3e8d47e09e35ca067b2da364-vp0.us.blockchain.ibm.com:5004/chaincode");
			
		String input = "{\"jsonrpc\": \"2.0\",\"method\": \"query\",\"params\": {\"type\": 1,\"chaincodeID\": {\"name\": \"561230a20bf00f6a420da1fb69e78d29dc546f9355494c96179377c01c4cb7ce8c6469e57a715e96d206cee17d4dd832176c959fd89573e2435731d466c7a4a6\"},\"ctorMsg\": {\"function\": \"getAllExperienceByCandidateId\",\"args\": [ \"" + getSearchUser().getCandidateId() + "\"]},\"secureContext\": \"user_type1_0\"},\"id\": 1}";
		
		ClientResponse response = webResource.accept("application/json").post(ClientResponse.class, input);  
		
		if (response.getStatus() != 200)
		{
			System.out.println("Response not received 1");
		}
			
		String output = response.getEntity(String.class);
		System.out.println("Output from chaincode for employment ....");
		System.out.println(output);

		JSONObject jsonOutput = new JSONObject(output);
		
		if (!jsonOutput.has("result"))
			return "newEmployment";
		JSONObject jsonResult = jsonOutput.getJSONObject("result");
		JSONObject jsonMsg = new JSONObject(jsonResult.getString("message"));
		JSONArray jsonArrayExperience = jsonMsg.getJSONArray("experiencedetails");
		
		int noOfData = 0;
		
		for (Object jsonObjExp : jsonArrayExperience)
		{
			if (jsonObjExp instanceof JSONObject)
			{
				try {
					SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
					if (((JSONObject)jsonObjExp).getString("doj") == null)
						continue;
					else if (((JSONObject)jsonObjExp).getString("doj").equals("null"))
						continue;
					String dojString = ((JSONObject)jsonObjExp).getString("doj");
					String dolString = ((JSONObject)jsonObjExp).getString("dol");
					Date dol = null;
					if (dolString.trim().length() != 0)
						dol = format.parse(dolString);
					
					companyMatch = false;
					String org = ((JSONObject)jsonObjExp).getString("organization");
					
					if (getSimpleLogin().getUserid().toLowerCase().contains(org.toLowerCase()))
					{
						companyMatch = true;
						this.companyName = org;
					}
				
					EmploymentDetails empBean = new EmploymentDetails(org, 
													format.parse(dojString), 
													((JSONObject)jsonObjExp).getString("designation"), 
													((JSONObject)jsonObjExp).getString("skillset"), 
													((JSONObject)jsonObjExp).getString("certification"),
													((JSONObject)jsonObjExp).getString("salary"),
													dol,
													//((JSONObject)jsonObjExp).getInt("experienceId"),
													noOfData,
													companyMatch);
					empDetails.add(empBean);
					noOfData++;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return "resultEmployment";
	}
	
	public SimpleLogin getSimpleLogin() {
		return simpleLogin;
	}

	public void setSimpleLogin(SimpleLogin simpleLogin) {
		this.simpleLogin = simpleLogin;
	}
	
	public SearchUser getSearchUser() {
		return searchUser;
	}
	
	public void setSearchUser(SearchUser searchUser) {
		this.searchUser = searchUser;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(String skillSet) {
		this.skillSet = skillSet;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public Date getDol() {
		return dol;
	}

	public void setDol(Date dol) {
		this.dol = dol;
	}
	
	public List<EmploymentDetails> getEmpDetails() {
		return empDetails;
	}

	public void setEmpDetails(List<EmploymentDetails> empDetails) {
		this.empDetails = empDetails;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public boolean getEditMode() {
		return editMode;
	}
	
	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	
	public boolean getEditMode1() {
		return editMode1;
	}
	
	public void setEditMode1(boolean editMode1) {
		this.editMode1 = editMode1;
	}
	
	public int getRowCnt() {
		return rowCnt;
	}
	
	public void setRowCnt(Integer rowCnt) {
		System.out.println("set row cnt: " + rowCnt);
		this.rowCnt = rowCnt;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isCompanyMatch() {
		return companyMatch;
	}

	public void setCompanyMatch(boolean companyMatch) {
		this.companyMatch = companyMatch;
	}

	public void fieldEditEnabled() {
		//setRowCnt(rowCnt);
		System.out.println("rowcnt: " + rowCnt);
		editMode = false;
		editMode1 = true;
	}

	public String saveChanges() {
		System.out.println("designation in save changes: " + designation + " and count: " + count);
		EmploymentDetails emp1 = new EmploymentDetails(organization, doj, designation, skillSet, certification, salary, dol, count, true);
		
		empDetails.set(count, emp1);
		return "resultEmployment";
	}
	
	public String addNewAction() {
		this.organization = "";
		this.doj = new Date();
		this.designation = "";
		this.skillSet = "";
		this.certification = "";
		this.salary = "";
		this.dol = null;
		this.count = 0;
		return "employmentAddTemplate";
	}
	
	public String addNew() {
		System.out.println("organisation in add changes: " +companyName);
		
		Format formatter = new SimpleDateFormat("MM/dd/yyyy");
		String dojString = "";
		String dolString = "";
		if (doj != null)
			dojString = formatter.format(doj);
		
		if (dol != null)
			dolString = formatter.format(dol);
		System.out.println("after dol");
		
		Client client = Client.create();
		WebResource webResource = client.resource("https://edb67a1a3e8d47e09e35ca067b2da364-vp0.us.blockchain.ibm.com:5004/chaincode");
			
		String input = "{\"jsonrpc\": \"2.0\",\"method\": \"invoke\",\"params\": {\"type\": 1,\"chaincodeID\": "
				+ "{\"name\": \"561230a20bf00f6a420da1fb69e78d29dc546f9355494c96179377c01c4cb7ce8c6469e57a715e96d206cee17d4dd832176c959fd89573e2435731d466c7a4a6\"},\"ctorMsg\": "
				+ "{\"function\": \"addExperienceDetails\",\"args\": [ \"" + getSearchUser().getCandidateId() + "\","
				+ "\"" + companyName + "\",\"" + dojString + "\",\"" + designation + "\", \"" + skillSet + "\", \"" + certification + "\", \"" + salary + "\", \"" + dolString + "\""  
				+ "]},\"secureContext\": \"user_type1_0\"},\"id\": 1}";
		
		ClientResponse response = webResource.accept("application/json").post(ClientResponse.class, input);  
		
		if (response.getStatus() != 200)
		{
			System.out.println("Response not received 1");
		}
			
		String output = response.getEntity(String.class);
		System.out.println("Output from chaincode for employment ....");
		System.out.println(output);

		JSONObject jsonOutput = new JSONObject(output);
		JSONObject jsonResult = jsonOutput.getJSONObject("result");
		String jsonStatus = jsonResult.getString("status");
		
		if ("OK".equalsIgnoreCase(jsonStatus.trim()))
		{
			EmploymentDetails emp1 = new EmploymentDetails(companyName, doj, designation, skillSet, certification, salary, dol, 0, true);
			if (empDetails.size() > 0)
				empDetails.add(0, emp1);
			else
				empDetails.add(emp1);
			return "resultEmployment";
		}
		else
			return "blockChainError";
	}
	
	public void changeOrg(ValueChangeEvent eventOrg) {
		if (eventOrg.getNewValue() != null && !eventOrg.getNewValue().toString().trim().equals(""))
			organization = eventOrg.getNewValue().toString();
	}
	
	public void changeDoj(ValueChangeEvent eventDoj) {
		try {
			if (eventDoj.getNewValue() != null && !eventDoj.getNewValue().toString().trim().equals(""))
			{
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				String date = format.format(eventDoj.getNewValue());
				doj = (Date)eventDoj.getNewValue();
			}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void changeDesignation(ValueChangeEvent eventDesignation) {
		if (eventDesignation.getNewValue() != null && !eventDesignation.getNewValue().toString().trim().equals(""))
			designation = eventDesignation.getNewValue().toString();
	}
	
	public void changeSkillSet(ValueChangeEvent eventSkillSet) {
		if (eventSkillSet.getNewValue() != null && !eventSkillSet.getNewValue().toString().trim().equals(""))
			skillSet = eventSkillSet.getNewValue().toString();
	}
	
	public void changeCertification(ValueChangeEvent eventCert) {
		if (eventCert.getNewValue() != null && !eventCert.getNewValue().toString().trim().equals(""))
			certification = eventCert.getNewValue().toString();
	}
	
	public void changeSalary(ValueChangeEvent eventSalary) {
		if (eventSalary.getNewValue() != null && !eventSalary.getNewValue().toString().trim().equals(""))
			salary = eventSalary.getNewValue().toString();
	}
	
	public void changeDol(ValueChangeEvent eventDol) {
		try {
			if (eventDol.getNewValue() != null && !eventDol.getNewValue().toString().trim().equals(""))
			{
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
				String date = format.format(eventDol.getNewValue());
				doj = (Date)eventDol.getNewValue();
			}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public String openEditPage(Integer count1)
	{
		System.out.println("index in openEditPage: " + count1);
		System.out.println("org in open edit page1: " + organization);
		organization = empDetails.get(count1).organization;
		System.out.println("org in open edit page: " + organization);
		doj = empDetails.get(count1).doj;
		dol = empDetails.get(count1).dol;
		certification = empDetails.get(count1).certification;
		System.out.println("cer: " + certification);
		skillSet = empDetails.get(count1).skillSet;
		designation = empDetails.get(count1).designation;
		salary = empDetails.get(count1).salary;
		count = count1;
		return "resultEmploymentEdit";
	}
}
