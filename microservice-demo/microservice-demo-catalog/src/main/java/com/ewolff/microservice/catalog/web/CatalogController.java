package com.ewolff.microservice.catalog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ewolff.microservice.catalog.Item;
import com.ewolff.microservice.catalog.ItemRepository;

@Controller
public class CatalogController {

	private final ItemRepository itemRepository;

	@Autowired
	public CatalogController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@GetMapping(value = "/{id}.html", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView Item(@PathVariable("id") long id) {
		return new ModelAndView("item", "item", itemRepository.findById(id).get());
	}

	@GetMapping(value = "/list.html")
	public ModelAndView ItemList() {
		return new ModelAndView("itemlist", "items", itemRepository.findAll());
	}

	@GetMapping(value = "/form.html")
	public ModelAndView add() {
		return new ModelAndView("item", "item", new Item());
	}

	@PostMapping(value = "/form.html")
	public ModelAndView post(Item Item) {
		Item = itemRepository.save(Item);
		return new ModelAndView("success");
	}

	@PutMapping(value = "/{id}.html")
	public ModelAndView put(@PathVariable("id") long id, Item item) {
		item.setId(id);
		itemRepository.save(item);
		return new ModelAndView("success");
	}

	@GetMapping(value = "/searchForm.html", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView searchForm() {
		return new ModelAndView("searchForm");
	}

	@GetMapping(value = "/searchByName.html", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView search(@RequestParam("query") String query) {
		return new ModelAndView("itemlist", "items", itemRepository.findByNameContaining(query));
	}

	@DeleteMapping(value = "/{id}.html")
	public ModelAndView delete(@PathVariable("id") long id) {
		itemRepository.deleteById(id);
		return new ModelAndView("success");
	}

}
