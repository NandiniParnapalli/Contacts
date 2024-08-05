package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;

class ContactServiceTest {
	@InjectMocks
ContactService contactService;
@Mock
ContactRepository contactRepository;
@BeforeEach
public void init() {
	MockitoAnnotations.openMocks(this);
}
	@Test
	void testSaveContact() {
		Contact contact=new Contact(101,9866261809l,"simha");
		when(contactRepository.save(contact)).thenReturn(contact);
		assertEquals(contact,  contactService.saveContact(contact));
	}

	@Test
	void testGetContactAll() {
		List<Contact> contacts=Arrays.asList(new Contact(101,9866261809l,"simha"));
		when(contactRepository.findAll()).thenReturn(contacts);
		assertEquals(1, contactService.getContactAll().size());
			}

	@Test
	void testGetContactById() {
	Contact contact=new Contact(101,9866261809l,"simha");
	when(contactRepository.findById(101)).thenReturn(Optional.of(contact));
	assertEquals(contact,contactService.getContactById(101));
	}

	@Test
	void testDeleteContact() {
		Contact contact=new Contact(101,9866261809l,"simha");
		contactService.deleteContact(contact);
		verify(contactRepository,times(1)).delete(contact);
	}
	 @Test
	void testUpdateContact() {
		  Contact contact = new Contact(101,9866261809l,"simha");
	        when(contactRepository.findById(101)).thenReturn(Optional.of(contact));
	        when(contactRepository.save(contact)).thenReturn(contact);

	        String result = contactService.updateContact(101, contact);

        verify(contactRepository, times(1)).findById(101);
	        verify(contactRepository, times(1)).save(contact);

	        assertEquals("contact updated successfully", result);
	}

}
