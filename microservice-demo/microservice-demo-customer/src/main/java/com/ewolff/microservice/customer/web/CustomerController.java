package com.ewolff.microservice.customer.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ewolff.microservice.customer.Customer;
import com.ewolff.microservice.customer.CustomerRepository;

@Controller
public class CustomerController {

	private CustomerRepository customerRepository;

	@Autowired
	public CustomerController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@GetMapping(value = "/{id}.html", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView customer(@PathVariable("id") long id) {
		return new ModelAndView("customer", "customer", customerRepository.findById(id).get());
	}

	@GetMapping(value = "/list.html")
	public ModelAndView customerList() {
		return new ModelAndView("customerlist", "customers", customerRepository.findAll());
	}

	@GetMapping(value = "/form.html")
	public ModelAndView add() {
		return new ModelAndView("customer", "customer", new Customer());
	}

	@PostMapping(value = "/form.html")
	public ModelAndView post(Customer customer, HttpServletRequest httpRequest) {
		customer = customerRepository.save(customer);
		return new ModelAndView("success");
	}

	@PutMapping(value = "/{id}.html")
	public ModelAndView put(@PathVariable("id") long id, Customer customer, HttpServletRequest httpRequest) {
		customer.setId(id);
		customerRepository.save(customer);
		return new ModelAndView("success");
	}

	@DeleteMapping(value = "/{id}.html")
	public ModelAndView delete(@PathVariable("id") long id) {
		customerRepository.deleteById(id);
		return new ModelAndView("success");
	}

}
