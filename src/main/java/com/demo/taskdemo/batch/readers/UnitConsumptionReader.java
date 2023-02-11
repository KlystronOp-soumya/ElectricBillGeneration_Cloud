package com.demo.taskdemo.batch.readers;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;

import com.demo.taskdemo.entities.UnitConsumption;
import com.demo.taskdemo.mappers.UnitConsumptionRowMapper;

public class UnitConsumptionReader {

	private DataSource dataSource;

	public UnitConsumptionReader(final DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.dataSource = dataSource;
	}

	public JdbcCursorItemReader<UnitConsumption> reader() {
		JdbcCursorItemReader<UnitConsumption> unitConsumptionItemReader = null;
		try {
			unitConsumptionItemReader = new JdbcCursorItemReaderBuilder<UnitConsumption>().dataSource(null)
					.rowMapper(new UnitConsumptionRowMapper()).sql(getSql()).build();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return unitConsumptionItemReader;
	}

	protected String getSql() {
		final String fetchUnitConsumptionQuery = "SELECT CONSUMER_ID , METER_ID , METER_NO, BILL_MONTH , BILL_YEAR , UNIT_CONSUMED , TARIFF_TYPE ,  "
				+ " FROM UNIT_CONSUMPTION";

		return fetchUnitConsumptionQuery;
	}

}
