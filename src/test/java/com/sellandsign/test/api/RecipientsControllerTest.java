package com.sellandsign.test.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sellandsign.test.controller.api.recipients.RecipientController;
import com.sellandsign.test.dto.RecipientDTO;
import com.sellandsign.test.error.ResponseExceptionHandler;
import com.sellandsign.test.model.recipients.Recipient;
import com.sellandsign.test.service.RecipientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecipientsControllerTest {

	private MockMvc mockMvc;

	private RecipientController recipientController;

	private RecipientService recipientService;

	@BeforeEach
	public void init() {
		recipientService = mock(RecipientService.class);
		recipientController = new RecipientController(recipientService);
		mockMvc = MockMvcBuilders.standaloneSetup(recipientController).setControllerAdvice(ResponseExceptionHandler.class).build();
	}

	@Test
	public void getRecipientsTest() throws Exception {
		List<Recipient> recipients = List.of(getMockedRecipient(), getMockedRecipient());
		when(recipientService.getRecipients()).thenReturn(recipients);
		mockMvc.perform(get("/v1/recipients")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[1].id").value(1))
				.andExpect(jsonPath("$[1].address_1").value("address_1"))
				.andExpect(jsonPath("$[1].cell_phone").value("01"))
				.andExpect(jsonPath("$[1].city").value("city"))
				.andExpect(jsonPath("$[1].email").value("xgeli@mail.com"))
				.andExpect(jsonPath("$[1].firstname").value("firstname"))
				.andExpect(jsonPath("$[1].lastname").value("lastname"))
				.andExpect(jsonPath("$[1].civility").value(1))
				.andExpect(jsonPath("$[1].localisation").value("fr_Fr"));
	}

	@Test
	public void getRecipientByEmailTest() throws Exception {
		when(recipientService.getRecipientByEmail("xgeli@mail")).thenReturn(Optional.of(getMockedRecipient()));
		mockMvc.perform(get("/v1/recipients/email").param("email", "xgeli@mail"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.address_1").value("address_1"))
				.andExpect(jsonPath("$.cell_phone").value("01"))
	            .andExpect(jsonPath("$.city").value("city"))
				.andExpect(jsonPath("$.email").value("xgeli@mail.com"))
				.andExpect(jsonPath("$.firstname").value("firstname"))
				.andExpect(jsonPath("$.lastname").value("lastname"))
				.andExpect(jsonPath("$.civility").value(1))
				.andExpect(jsonPath("$.localisation").value("fr_Fr"));
	}

	@Test
	public void getRecipientByEmailNotfound() throws Exception {
		when(recipientService.getRecipientByEmail("xgeli@mail")).thenReturn(Optional.empty());
		mockMvc.perform(get("/v1/recipients/email").param("email", "xgeli@mail"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void getRecipientByEmailWithNoParam() throws Exception {
		mockMvc.perform(get("/v1/recipients/email"))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void createRecipient() throws Exception {
		ArgumentCaptor<Recipient> recipientCaptor = ArgumentCaptor.forClass(Recipient.class);
		when(recipientService.addRecipient(any())).thenReturn(1L);
		ObjectMapper mapper = new ObjectMapper();
		RecipientDTO recipientDTO = getMockedRecipientDTO();
		String json = mapper.writeValueAsString(recipientDTO);
		mockMvc.perform(post("/v1/recipients")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(jsonPath("$").value(1))
				.andExpect(status().isCreated());

		verify(recipientService).addRecipient(recipientCaptor.capture());
		Recipient recipient = recipientCaptor.getValue();
		verifyRecipient(recipient, recipientDTO);
	}

	private void verifyRecipient(Recipient recipient, RecipientDTO recipientDTO) {
		assertEquals(recipient.getAddress_1(), recipientDTO.getAddress1());
		assertEquals(recipient.getCity(), recipientDTO.getCity());
		assertEquals(recipient.getCivility(), recipientDTO.getCivility());
		assertEquals(recipient.getEmail(), recipientDTO.getEmail());
		assertEquals(recipient.getFirstname(), recipientDTO.getFirstname());
		assertEquals(recipient.getLastname(), recipientDTO.getLastname());
		assertEquals(recipient.getCell_phone(), recipientDTO.getCellPhone());
		assertEquals(recipient.getLocalisation(), recipientDTO.getLocalisation());

	}

	private Recipient getMockedRecipient() {
		return Recipient.builder()
				.id(1L)
				.address_1("address_1")
				.cell_phone("01")
				.city("city")
				.email("xgeli@mail.com")
				.firstname("firstname")
				.lastname("lastname")
				.civility(1)
				.localisation("fr_Fr")
				.build();
	}

	private RecipientDTO getMockedRecipientDTO () {
		return RecipientDTO.builder()
				       .address1("address_1")
				       .cellPhone("01")
				       .city("city")
				       .email("xgeli@mail.com")
				       .firstname("firstname")
				       .lastname("lastname")
				       .civility(1)
				       .localisation("fr_Fr")
				       .build();
	}


}
