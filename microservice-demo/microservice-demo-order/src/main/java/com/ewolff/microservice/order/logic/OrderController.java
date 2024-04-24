package com.ewolff.microservice.order.logic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ewolff.microservice.order.clients.CatalogClient;
import com.ewolff.microservice.order.clients.Customer;
import com.ewolff.microservice.order.clients.CustomerClient;
import com.ewolff.microservice.order.clients.Item;

@Controller
class OrderController {

	private OrderRepository orderRepository;

	private OrderService orderService;

	private CustomerClient customerClient;
	private CatalogClient catalogClient;

	@Autowired
	private OrderController(OrderService orderService, OrderRepository orderRepository, CustomerClient customerClient,
			CatalogClient catalogClient) {
		super();
		this.orderRepository = orderRepository;
		this.customerClient = customerClient;
		this.catalogClient = catalogClient;
		this.orderService = orderService;
	}

	@ModelAttribute("items")
	public Collection<Item> items() {
		return catalogClient.findAll();
	}

	@ModelAttribute("customers")
	public Collection<Customer> customers() {
		return customerClient.findAll();
	}

	@GetMapping(value = "/")
	public ModelAndView orderList() {
		return new ModelAndView("orderlist", "orders", orderRepository.findAll());
	}

	@GetMapping(value = "/form.html")
	public ModelAndView form() {
		return new ModelAndView("orderForm", "order", new Order());
	}

	@PostMapping(value = "/line")
	public ModelAndView addLine(Order order) {
		order.addLine(0, catalogClient.findAll().iterator().next().getItemId());
		return new ModelAndView("orderForm", "order", order);
	}

	@GetMapping(value = "/{id}")
	public ModelAndView get(@PathVariable("id") long id) {
		return new ModelAndView("order", "order", orderRepository.findById(id).get());
	}

	@PostMapping(value = "/")
	public ModelAndView post(Order order) {
		order = orderService.order(order);
		return new ModelAndView("success");
	}

	@DeleteMapping(value = "/{id}")
	public ModelAndView post(@PathVariable("id") long id) {
		orderRepository.deleteById(id);

		return new ModelAndView("success");
	}

}
