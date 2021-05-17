package com.sellandsign.test.model.recipients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity class for recipients
 */
@Entity(name="recipients")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int civility;
	private String firstname;
	private String lastname;
	private String email;
	private String address_1;
	private String postal_code;
	private String city;
	private String cell_phone;
	private Long last_modification_date;
	private Integer last_modification_place;
	private String localisation;
}
