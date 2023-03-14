package com.demo.taskdemo.entities;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessedSlabDetails {
	private int slabStart;
	private int slabEnd;
	private BigDecimal slabRate;
	private BigDecimal amountPerSlab;
}
