package com.demo.taskdemo.batch.readers;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;

import com.demo.taskdemo.entities.DTO.UnitConsumptionDTO;
import com.demo.taskdemo.exceptions.BillProcessException;
import com.demo.taskdemo.mappers.UnitConsumptionRowMapper;

public class UnitConsumptionReader {

	private static final Logger LOGGER = LogManager.getLogger(UnitConsumptionReader.class);

	private JdbcCursorItemReader<UnitConsumptionDTO> unitConsumptionItemReader;
	private DataSource dataSource;

	public UnitConsumptionReader(final DataSource dataSource) {
		// TODO Auto-generated constructor stub
		this.dataSource = dataSource;
	}

	public JdbcCursorItemReader<UnitConsumptionDTO> getReader() throws BillProcessException {
		LOGGER.info("Preparing item reader to read unit consumption data");
		try {
			this.unitConsumptionItemReader = new JdbcCursorItemReaderBuilder<UnitConsumptionDTO>()
					.name("unitconsumptionReader").dataSource(this.dataSource).rowMapper(new UnitConsumptionRowMapper())
					.sql(getSql()).build();

		} catch (Exception e) {
			// TODO: handle exception
			throw new BillProcessException(e.getCause(), e.getMessage());
		}

		return this.unitConsumptionItemReader;
	}

	protected String getSql() {
		final String fetchUnitConsumptionQuery = "SELECT unt.CONSUMER_ID , unt.METER_ID , unt.METER_NO, unt.BILL_MONTH , unt.BILL_YEAR , unt.UNIT_CONSUMED , unt.TARIFF_TYPE as TARIFF_TYPE, trf.TARIFF_ID as TARIFF_ID, trf.TARIFF_START_DATE as TARIFF_START_DATE\r\n"
				+ "FROM UNIT_CONSUMPTION unt , TARIFF trf \r\n" + "where unt.TARIFF_TYPE = trf.TARIFF_TYPE ";

		LOGGER.info("Fetch query returned");
		return fetchUnitConsumptionQuery;
	}

}
