package com.condigence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallet {

	@Id
	private Long id;

	private Long userId;

	private Integer balance;

	private String isActive;

	private String lastRecharged;

}
