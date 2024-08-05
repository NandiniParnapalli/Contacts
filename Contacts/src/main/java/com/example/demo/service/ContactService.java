package com.example.demo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContactService {
@Autowired
ContactRepository contactRepository;
 public Contact saveContact(Contact contact)
 {
	 log.info("saving contacts");
	 return contactRepository.save(contact);
	 
 }
 public List<Contact> getContactAll()
 {
	 log.info("get all contacts");
	 return contactRepository.findAll();
 }
 public Contact getContactById(Integer id) {
	 log.debug("get contact by id",id);
	 return contactRepository.findById(id).orElse(null);
	 
 }
 public void deleteContact(Contact contact)
 {
	 log.info("delete contact");
	 contactRepository.delete(contact);
 }
 public String updateContact(Integer id, Contact contact) {
	 log.debug("update contact by id",id);
		Contact contact1 = contactRepository.findById(id).orElse(null);
		if (contact1!= null) {
			contactRepository.save(contact1);
			return "contact updated successfully";
		} else {
			return "Product not found";
		}
	}
/* public List<Contact> sortByField(String field)
 {
	 return contactRepository.findAll(Sort.by(Sort.Direction.DESC, field));
 }*/

 public Page<Contact> getPageSort(int offset,int size,String field)
 {
	 log.info("paginate and sorting");
	 return contactRepository.findAll(PageRequest.of(offset, size).withSort(Sort.by(Sort.Direction.DESC,field)));
 }
}
