package main.java.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.restbean.Education;

@RestController
public class EducationController {
	
	@RequestMapping(value = "/education", method = RequestMethod.GET,headers="Accept=application/json")
	public List<Education> getEducation()
	{
		List<Education> listOfEducation = new ArrayList<Education>();
		listOfEducation=createEducationList();
		return listOfEducation;
	}

	@RequestMapping(value = "/education/{id}", method = RequestMethod.GET,headers="Accept=application/json")
	public Education getEducationById(@PathVariable int id)
	{
		List<Education> listOfEducation = new ArrayList<Education>();
		listOfEducation=createEducationList();

		for (Education education: listOfEducation) {
			if(education.getId()==id)
				return education;
		}
		
		return null;
	}

// Utiliy method to create education list.
	public List<Education> createEducationList()
	{
		Education indiaCountry=new Education(1, "EEE", "JNTU", "A", "2012-05-27", 78);
		Education chinaCountry=new Education(4, "CS", "JNTU", "B", "2000-04-30", 55);
		Education nepalCountry=new Education(3, "Electrical", "JNTU", "A", "2012-05-27", 72);
		Education bhutanCountry=new Education(2, "Mechanical", "JNTU", "A", "2001-04-19", 64);

		List<Education> listOfCountries = new ArrayList<Education>();
		listOfCountries.add(indiaCountry);
		listOfCountries.add(chinaCountry);
		listOfCountries.add(nepalCountry);
		listOfCountries.add(bhutanCountry);
		return listOfCountries;
	}
}
