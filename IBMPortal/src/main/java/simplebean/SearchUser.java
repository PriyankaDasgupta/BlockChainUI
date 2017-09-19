package main.java.simplebean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.xml.bind.ParseConversionEvent;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@ManagedBean(eager=true, name="searchUser")
@SessionScoped
public class SearchUser implements Serializable {
	String candidateId;
	
	private String degree;
	private String grade;
	private int marks;
	private String university;
	private String yop;
	
	private String firstName;
	private String lastName;
	private String dob;
	private String gender;
	private String email;
	private String country;
	private String address;
	private String city;
	private String state;
	private String zipCode;
	private String title;
	private String phoneNo;
	private String uniqueIdType;
	private String uniqueIdNo;
	private String nationality;
	
	private String organization;
	private String doj;
	private String designation;
	private String skillSet;
	private String certification;
	private String salary;
	private String dol;
	private boolean verificationStatus;
	private boolean candidateExists = false;
	
	private List<EducationBean> eduDetails;
	private int count;
	
	public SearchUser() {
		count = 0;
		eduDetails = new ArrayList<EducationBean>();
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
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

	public int getMarks() {
		return marks;
	}

	public void setMarks(int marks) {
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getGender() {
		return gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
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

	public String getDol() {
		return dol;
	}

	public void setDol(String dol) {
		this.dol = dol;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getUniqueIdType() {
		return uniqueIdType;
	}

	public void setUniqueIdType(String uniqueIdType) {
		this.uniqueIdType = uniqueIdType;
	}
	
	public String getUniqueIdNo() {
		return uniqueIdNo;
	}

	public void setUniqueIdNo(String uniqueIdNo) {
		this.uniqueIdNo = uniqueIdNo;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public List<EducationBean> getEduDetails() {
		return eduDetails;
	}

	public void setEduDetails(List<EducationBean> eduDetails) {
		this.eduDetails = eduDetails;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public boolean isVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(boolean verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	
	public boolean isCandidateExists() {
		return candidateExists;
	}

	public void setCandidateExists(boolean candidateExists) {
		this.candidateExists = candidateExists;
	}
	
	public String returnResult()
	{
		String res = getPersonalDetails();
		if (res.equals("success"))
			return "personalDetails";
		else
			return "blockChainError";
	}
	
	public String getPersonalDetails() {
		Client client = Client.create();
		WebResource webResource = client.resource("https://edb67a1a3e8d47e09e35ca067b2da364-vp0.us.blockchain.ibm.com:5004/chaincode");
			
		String input = "{\"jsonrpc\": \"2.0\",\"method\": \"query\",\"params\": {\"type\": 1,\"chaincodeID\": {\"name\": \"561230a20bf00f6a420da1fb69e78d29dc546f9355494c96179377c01c4cb7ce8c6469e57a715e96d206cee17d4dd832176c959fd89573e2435731d466c7a4a6\"},\"ctorMsg\": {\"function\": \"getCandidate\",\"args\": [ \"" + getCandidateId() + "\"]},\"secureContext\": \"user_type1_0\"},\"id\": 1}";
		
		ClientResponse response = webResource.accept("application/json").post(ClientResponse.class, input);  
		
		if (response.getStatus() != 200)
		{
			System.out.println("Response not received 1");
		}
			
		String output = response.getEntity(String.class);
		System.out.println("Output from chaincode ....");
		System.out.println(output);

		JSONObject jsonOutput = new JSONObject(output);
		if (!jsonOutput.has("result"))
		{
			candidateExists = false;
			return "blockChainError";
		}
		
		JSONObject jsonResult = jsonOutput.getJSONObject("result");
		JSONObject jsonMsg = new JSONObject(jsonResult.getString("message"));
		
		setVerificationStatus(jsonMsg.getBoolean("verifyStatus"));
		if (!verificationStatus)
			return "pendingVerification";
		
		setFirstName(jsonMsg.getString("firstName"));
		setLastName(jsonMsg.getString("lastName"));
		setDob(jsonMsg.getString("dob"));
		if (jsonMsg.getString("gender").equalsIgnoreCase("M"))
			setGender("Male");
		else if (jsonMsg.getString("gender").equalsIgnoreCase("F"))
			setGender("Female");
		else
			setGender(jsonMsg.getString("gender"));
		setEmail(jsonMsg.getString("emailId"));
		setCountry(jsonMsg.getString("country"));
		String addr = jsonMsg.getString("address");
		setCity(jsonMsg.getString("city"));
		setState(jsonMsg.getString("state"));
		setZipCode(jsonMsg.getString("zip"));
		setTitle(jsonMsg.getString("title"));
		setPhoneNo(jsonMsg.getString("phoneNumber"));
		setUniqueIdType(jsonMsg.getString("uniqueIdType"));
		setUniqueIdNo(jsonMsg.getString("uniqueIdNumber"));
		setNationality(jsonMsg.getString("nationality"));
			
		setAddress(addr + "\n" + getCity() + "\n" + getState() + "\n" + getCountry() + "\n" + getZipCode());
		candidateExists = true; 
		return "personalDetails";
	}
	
	public String getEducationDetails() {
		if (!verificationStatus)
			return "pendingVerification";
		
		Client client = Client.create();
		WebResource webResource = client.resource("https://edb67a1a3e8d47e09e35ca067b2da364-vp0.us.blockchain.ibm.com:5004/chaincode");
			
		String input = "{\"jsonrpc\": \"2.0\",\"method\": \"query\",\"params\": {\"type\": 1,\"chaincodeID\": {\"name\": \"561230a20bf00f6a420da1fb69e78d29dc546f9355494c96179377c01c4cb7ce8c6469e57a715e96d206cee17d4dd832176c959fd89573e2435731d466c7a4a6\"},\"ctorMsg\": {\"function\": \"getAllCertificateByCandidateId\",\"args\": [ \"" + getCandidateId() + "\"]},\"secureContext\": \"user_type1_0\"},\"id\": 1}";
		
		ClientResponse response = webResource.accept("application/json").post(ClientResponse.class, input);  
		
		if (response.getStatus() != 200)
		{
			System.out.println("Response not received 1");
		}
			
		String output = response.getEntity(String.class);
		System.out.println("Output from chaincode for education ....");
		System.out.println(output);

		JSONObject jsonOutput = new JSONObject(output);
		if (!jsonOutput.has("result"))
		{
			candidateExists = false;
			return "blockChainError";
		}
		JSONObject jsonResult = jsonOutput.getJSONObject("result");
		JSONObject jsonMsg = new JSONObject(jsonResult.getString("message"));
		JSONArray jsonArrayCertificate = jsonMsg.getJSONArray("certificatedetails");
		
		for (Object jsonObjCertificate : jsonArrayCertificate)
		{
			if (jsonObjCertificate instanceof JSONObject)
			{
				EducationBean educationBean = new EducationBean(((JSONObject)jsonObjCertificate).getString("degree"), 
													((JSONObject)jsonObjCertificate).getString("grade"), 
													((JSONObject)jsonObjCertificate).getString("marks"), 
													((JSONObject)jsonObjCertificate).getString("universityName"), 
													((JSONObject)jsonObjCertificate).getString("year"));
				eduDetails.add(educationBean);
			}
		}
		candidateExists = true;
		return "educationDetails";
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("searchUser", null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("simpleLogin", null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("employmentDetails", null);
		return "index.xhtml?faces-redirect=true";
	}
}
