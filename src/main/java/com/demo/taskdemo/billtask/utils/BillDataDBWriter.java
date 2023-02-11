package com.demo.taskdemo.billtask.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;

import com.demo.taskdemo.billtask.entites.UnitConsumption;

public class BillDataDBWriter {
	private static final Logger LOGGER = LogManager.getLogger(BillDataDBWriter.class);
	private DataSource dataSource;
	// private JdbcBatchItemWriter<UnitConsumption> unitConsumptionBatchItemWriter;
	private BillDataLoadJobParams billDataLoadJobParams;

	public BillDataDBWriter(final BillDataLoadJobParams billDataLoadJobParams, final DataSource dataSource) {
		// TODO Auto-generated constructor stub
		// .unitConsumptionBatchItemWriter = new JdbcBatchItemWriter<>();
		this.billDataLoadJobParams = billDataLoadJobParams;
		this.dataSource = dataSource;
	}

	public JdbcBatchItemWriter<UnitConsumption> itemWriter() {
		LOGGER.info("item writer setup");
		return new JdbcBatchItemWriterBuilder<UnitConsumption>().dataSource(this.dataSource).sql(getSql())
				.itemPreparedStatementSetter(new ItemPreparedStatementSetter<UnitConsumption>() {

					@Override
					public void setValues(UnitConsumption item, PreparedStatement ps) throws SQLException {
						// TODO Auto-generated method stub
						SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-mm");
						LOGGER.info(item.toString());
						ps.setString(1, item.getConsumerId());
						ps.setString(2, item.getMeterId());
						ps.setString(3, item.getMeterNo());
						ps.setInt(4, item.getBillingMonth());
						ps.setInt(5, item.getBillingYear());
						ps.setInt(6, item.getUnitConsumed());
						ps.setString(7, item.getTariffType());
						ps.setString(8, format.format(item.getBillingInsertionDate()));
						ps.setInt(9, billDataLoadJobParams.getCurrDay());
						ps.setInt(10, billDataLoadJobParams.getCurrMon());
						ps.setInt(11, billDataLoadJobParams.getCurrYear());

					}
				}).build();

	}

	protected String getSql() {

		final String query = "INSERT INTO UNIT_CONSUMPTION VALUES(?,?,?,?,?,?,?,?,?,?,?)";

		return query;
	}

	/*
	 * @Autowired public void setDataSource(DataSource dataSource) { this.dataSource
	 * = dataSource; }
	 */

}
