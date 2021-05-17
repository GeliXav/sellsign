package com.sellandsign.test.dto;

import com.sellandsign.test.model.recipients.Recipient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipientDTO {
	private Integer civility;
	private String firstname;
	private String lastname;
	private String email;
	private String address1;
	private String postalCode;
	private String city;
	private String cellPhone;
	private String localisation;

	public Recipient toEntity() {
		return Recipient.builder()
				       .civility(civility)
				       .firstname(firstname)
					   .lastname(lastname)
				       .cell_phone(cellPhone)
				       .email(email)
				       .address_1(address1)
				       .postal_code(postalCode)
				       .city(city)
				       .last_modification_date(new Timestamp(System.currentTimeMillis()).getTime())
				       .last_modification_place(1)
				       .localisation(localisation)
				       .build();
	}
}
