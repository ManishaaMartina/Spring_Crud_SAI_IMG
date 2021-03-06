package person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PersonController {
	//private static int counter = 0;

	@Autowired
	private CustomerRepository repo;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView Hello() {
		ModelAndView model = new ModelAndView("personX.jsp");
		model.addObject("list", repo.findAll());
		return model;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public synchronized String additem(@ModelAttribute("person") Person p) {
		if (p.getName() != null && p.getAge() != 0) {
			//p.setId(++counter);
			repo.saveAndFlush(p);
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Integer id) {
		ModelAndView model = new ModelAndView("personX.jsp");
		model.addObject("newPerson", repo.findOne(id));
		model.addObject("list", repo.findAll());
		return model;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("person") Person p) {
		if (p.getName() != null && p.getAge() != 0 && p.getId() != 0) {
			repo.saveAndFlush(p);
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Integer id) {
		repo.delete(id);
		return "redirect:/";
	}
}