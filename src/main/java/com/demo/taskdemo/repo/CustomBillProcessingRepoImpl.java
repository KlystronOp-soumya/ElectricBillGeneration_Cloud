package com.demo.taskdemo.repo;

import org.springframework.jdbc.core.JdbcTemplate;

import com.demo.taskdemo.repo.intf.CustomBillProcessingRepo;

public class CustomBillProcessingRepoImpl implements CustomBillProcessingRepo {

	private JdbcTemplate jdbcTemplate;

	public CustomBillProcessingRepoImpl(final JdbcTemplate jdbcTemplate) {
		// TODO Auto-generated constructor stub
		this.jdbcTemplate = jdbcTemplate;
	}
}
