package com.example.demo.controller;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.entity.Contact;
import com.example.demo.service.ContactService;

import com.fasterxml.jackson.databind.ObjectMapper;
@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ContactService contactService;

	@Autowired
	private ObjectMapper objectMapper;
	@Test
	void testSaveContact() throws Exception {
		Contact contact=new Contact(101,9866261809L,"simha");
		when(contactService.saveContact(contact)).thenReturn(contact);
		// Act & Assert
		        mockMvc.perform(MockMvcRequestBuilders.post("/contact").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(contact)))
		        .andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testGetContactAll() throws Exception{
		Contact c1 = new Contact(101,9866261809L,"simha");
		Contact c2 = new Contact(102,8309117650L,"nandu");
		List<Contact> contacts = Arrays.asList(c1, c2);

		when(contactService.getContactAll()).thenReturn(contacts);

		mockMvc.perform(MockMvcRequestBuilders.get("/contacts")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))

				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(101))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].contactNo").value(9866261809L))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("simha"))

				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(102))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].contactNo").value(8309117650L))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("nandu"));




		verify(contactService, times(1)).getContactAll();

	}

	@Test
	void testGetContactById() throws Exception {
		Contact contact=new Contact(101,9866261809l,"simha");
		when(contactService.getContactById(101)).thenReturn(contact);

		mockMvc.perform(MockMvcRequestBuilders.get("/contact/{id}", 101).contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(contact)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(101))

		.andExpect(MockMvcResultMatchers.jsonPath("$.contactNo").value(9866261809l))
		.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("simha"));


				verify(contactService,times(1)).getContactById(101);
	}

	@Test
	void testDeleteContact() throws  Exception {
		Contact contact=new Contact(101,9866261809l,"simha");
		doNothing().when(contactService).deleteContact(contact);
		mockMvc.perform(delete("/delete")
		.contentType(MediaType.APPLICATION_JSON)
		.content(new ObjectMapper().writeValueAsString(contact)))
		.andExpect(status().isOk());

		}
	@Test
	void testUpdateContact() throws  Exception{

		Contact contact = new Contact(101,9866261809l,"simha");


		when(contactService.updateContact(101, contact)).thenReturn("contact updated successfully");

		mockMvc.perform(MockMvcRequestBuilders.put("/update/{id}",101)
				.contentType(MediaType.APPLICATION_JSON)
	               .content(new ObjectMapper().writeValueAsString(contact)))

				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("contact updated successfully"));

		verify(contactService, times(1)).updateContact(101,contact);

	}

}
