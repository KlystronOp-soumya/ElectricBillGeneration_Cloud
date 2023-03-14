package com.demo.taskdemo.entities.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TariffDTO {

	private int slabStart;

	private int slabEnd;

	private int slabCount;

	private BigDecimal slabRate;

	private String tariffId;

	private String tariffType;

	private String tariffStartDate;

}
