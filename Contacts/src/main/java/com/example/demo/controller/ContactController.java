package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Contact;
import com.example.demo.service.ContactService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ContactController {
	@Autowired
	ContactService contactService;
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome all";
	}
	
	@GetMapping("/user/userProfile")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userProfile() {
		return "welcome user";
	}
	
	@GetMapping("/admin/adminProfile")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String adminProfile() {
		return "welcome admin";
	}
	@PostMapping("/contact")
	public ResponseEntity<Contact> saveContact(@RequestBody Contact contact)
	{
		log.info("adding contacts");
		return ResponseEntity.ok(contactService.saveContact(contact));
	}
	@GetMapping("/contacts")
	public List<Contact> getContactAll()
	{
		log.info("see all contacts");
		return contactService.getContactAll();
	}
	@GetMapping("/contact/{id}")
	public Contact getContactById(@PathVariable Integer id)
	{
		log.debug("finding contact by id",id);
		return contactService.getContactById(id);
	}
	@DeleteMapping("/delete")
	public void deleteContact(@RequestBody Contact contact)
	{
		log.info("removing contact");
		contactService.deleteContact(contact);
	}
	@PutMapping("/update/{id}")
public String updateContact(@PathVariable Integer id, @RequestBody Contact contact) {
		log.debug("contact updated by id",id);
		return contactService.updateContact(id, contact);
	}
	@GetMapping("/pagesort/{offset}/{size}/{field}")
	public Page<Contact> getPageSort(@PathVariable int offset,@PathVariable int size,@PathVariable String field)
	 {
		return contactService.getPageSort(offset, size, field);
	 }
}
