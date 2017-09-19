package main.java.simplebean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


@ManagedBean(eager=true, name="searchUser")
@SessionScoped
public class SearchUser implements Serializable {
	String candidateId;
	
	private String degree;
	private String grade;
	private String marks;
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
	private boolean verificationStatus;
	
	private Integer activeIndex;
	
	private List<EducationBean> eduDetails;
	private int count;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Integer getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(Integer activeIndex) {
		this.activeIndex = activeIndex;
	}

	public SearchUser() { eduDetails = new ArrayList<EducationBean>(); count = 0;}

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
	
	public boolean getVerificationStatus() {
		return verificationStatus;
	}
	
	public void setVerificationStatus(boolean verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public String returnResult()
	{
		String res = getPersonalDetails();
		System.out.println("result is: " + res);
		if (res.equals("error"))
			return "error";
		else
			return "personalDetails";
	}
	
	public String getEducationDetails() {
		if (eduDetails.size() > 0)
			eduDetails.clear();
		if (verificationStatus)
		{
		Client client = Client.create();
		WebResource webResource = client.resource("https://edb67a1a3e8d47e09e35ca067b2da364-vp0.us.blockchain.ibm.com:5004/chaincode");
			
		String input = "{\"jsonrpc\": \"2.0\",\"method\": \"query\",\"params\": {\"type\": 1,\"chaincodeID\": {\"name\": \"561230a20bf00f6a420da1fb69e78d29dc546f9355494c96179377c01c4cb7ce8c6469e57a715e96d206cee17d4dd832176c959fd89573e2435731d466c7a4a6\"},\"ctorMsg\": {\"function\": \"getAllCertificateByCandidateId\",\"args\": [ \"" + candidateId + "\"]},\"secureContext\": \"user_type1_0\"},\"id\": 1}";
		
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
			return "newEducationDetails";
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
													((JSONObject)jsonObjCertificate).getString("year"),
													((JSONObject)jsonObjCertificate).getInt("certificateId"));
				eduDetails.add(educationBean);
			}
		}
		return "educationDetails";
		}
		else
			return "pendingVerification";
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
			return "error";
		
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
		return "personalDetails";
	}
	
	public String addNewDetails()
	{
		this.degree = "";
		this.grade = "";
		this.yop = "";
		this.marks = "";
		this.university = "";
		return "educationAdd";
	}
	
	public String addNew()
	{
		this.degree = getDegree();
		this.grade = getGrade();
		this.yop = getYop();
		this.marks = getMarks();
		this.university = "WBUT";
		
		Client client = Client.create();
		WebResource webResource = client.resource("https://edb67a1a3e8d47e09e35ca067b2da364-vp0.us.blockchain.ibm.com:5004/chaincode");
			
		String input = "{\"jsonrpc\": \"2.0\",\"method\": \"invoke\",\"params\": {\"type\": 1,\"chaincodeID\": "
				+ "{\"name\": \"561230a20bf00f6a420da1fb69e78d29dc546f9355494c96179377c01c4cb7ce8c6469e57a715e96d206cee17d4dd832176c959fd89573e2435731d466c7a4a6\"},\"ctorMsg\": "
				+ "{\"function\": \"CertificateIssue\",\"args\": [ \"" + getCandidateId() + "\","
				+ "\"" + this.degree + "\",\"" + this.marks + "\",\"" + this.grade + "\", \"" + this.yop + "\", \"" + this.university + "\""  
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
			EducationBean educationBean = new EducationBean(degree, grade, marks, university, yop, 0);
			eduDetails.add(0, educationBean);
			return "educationDetails";
		}
		else
			return "error";
	}
	
	public String callEducationEdit(int index) {
		this.degree = eduDetails.get(index).getDegree();
		this.grade = eduDetails.get(index).getGrade();
		this.yop = eduDetails.get(index).getYop();
		this.marks = eduDetails.get(index).getMarks();
		this.university = eduDetails.get(index).getUniversity();
		this.count = eduDetails.get(index).getCount();
		return "educationEdit";
	}
	
	public String edit()
	{
		System.out.println("degree: " + this.degree);
		EducationBean educationBean = new EducationBean(this.degree, this.grade, this.marks, this.university, this.yop, this.count);
		eduDetails.set(this.count, educationBean);
		return "educationDetails";
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("searchUser", null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("simpleLogin", null);
		return "index.xhtml?faces-redirect=true";
	}
}
