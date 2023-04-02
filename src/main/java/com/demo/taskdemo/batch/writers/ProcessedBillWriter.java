package com.demo.taskdemo.batch.writers;

import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.demo.taskdemo.entities.ProcessedBillDetails;
import com.demo.taskdemo.entities.ProcessedSlabDetails;

public class ProcessedBillWriter implements ItemWriter<ProcessedBillDetails> {

	private static final Logger LOGGER = LogManager.getLogger(ProcessedBillWriter.class);
	// private DataSource dataSource ;
	private JdbcTemplate jdbcTemplate;

	private StepExecution stepExecution;

	public ProcessedBillWriter(JdbcTemplate jdbcTemplate) {
		// TODO Auto-generated constructor stub
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Transactional
	public void write(List<? extends ProcessedBillDetails> items) throws Exception {
		// TODO Auto-generated method stub
		Long i = 0L;
		try {
			// batch insert for granual slab break ups
			LOGGER.info("Inserting the billing history");

			for (ProcessedBillDetails eachItem : items) {
				List<ProcessedSlabDetails> processedSlabDetailsList = eachItem.getProcessedSlabDetailsList();
				i++;
				for (ProcessedSlabDetails eachSlabDetails : processedSlabDetailsList) {
					this.jdbcTemplate.update(getBillingHistoryQry(), (ps) -> {
						ps.setString(1, eachItem.getProcessedBilling().getBillSequenceNumber());
						ps.setString(2, eachItem.getUnitConsumptionDTO().getConsumerId());
						ps.setString(3, eachItem.getUnitConsumptionDTO().getMeterId());
						ps.setString(4, eachItem.getUnitConsumptionDTO().getMeterNo());
						ps.setString(5, eachItem.getUnitConsumptionDTO().getTariffId());
						ps.setString(6, eachItem.getUnitConsumptionDTO().getTariffType());
						ps.setInt(7, eachSlabDetails.getSlabStart());
						ps.setInt(8, eachSlabDetails.getSlabEnd());
						ps.setBigDecimal(9, eachSlabDetails.getSlabRate());
						ps.setBigDecimal(10, eachSlabDetails.getAmountPerSlab());
						ps.setDate(11, new Date(eachItem.getProcessedBilling().getJournalDate().getTime()));
						ps.setInt(12, eachItem.getProcessedBilling().getCalDay());
						ps.setInt(13, eachItem.getProcessedBilling().getCalMonth());
						ps.setInt(14, eachItem.getProcessedBilling().getCalYear());

					});
				}

			}
			LOGGER.info("Billing history inserted :: total count -> " + i);

			LOGGER.info("Inserting total billed amounts");
			// batch update the total billed amounts
			this.jdbcTemplate.batchUpdate(getBilledAmtQry(), items, items.size(), (ps, eachItem) -> {
				ps.setString(1, eachItem.getUnitConsumptionDTO().getConsumerId());
				ps.setString(2, eachItem.getUnitConsumptionDTO().getMeterId());
				ps.setString(3, eachItem.getUnitConsumptionDTO().getMeterNo());
				ps.setDate(4, new Date(eachItem.getProcessedBilling().getJournalDate().getTime()));
				ps.setInt(5, eachItem.getProcessedBilling().getCalDay());
				ps.setInt(6, eachItem.getProcessedBilling().getCalMonth());
				ps.setInt(7, eachItem.getProcessedBilling().getCalYear());
				ps.setBigDecimal(8, eachItem.getProcessedBilling().getTotalAmount());
				ps.setString(9, eachItem.getProcessedBilling().getBillSequenceNumber());
				ps.setBigDecimal(10, eachItem.getProcessedBilling().getDutyCharge());
				ps.setBigDecimal(11, eachItem.getProcessedBilling().getFixedCharge());
				ps.setBigDecimal(12, eachItem.getProcessedBilling().getVarCharge());
				ps.setBigDecimal(13, eachItem.getProcessedBilling().getMeterCharge());

			});
			LOGGER.info("Total billed amounts inserted");
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.info("Billing details insertion failed. Please check logs");
			LOGGER.error(e.getCause());
			LOGGER.error(e.getMessage());
			this.stepExecution.addFailureException(e);
		}

	}

	protected String getBilledAmtQry() {
		final String insertBilledAmtQry = "INSERT INTO BILLED_AMOUNT VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		return insertBilledAmtQry;
	}

	protected String getBillingHistoryQry() {
		final String insertBillingHistoryQry = "INSERT INTO BILLING_HISTORY (SEQ_NUM , CONSUMER_ID , METER_ID , METER_NO , "
				+ " TARIFF_ID , TARIFF_TYPE , SLAB_START , SLAB_END , SLAB_RATE , "
				+ " PER_SLAB_AMOUNT , JOURNAL_DATE , CAL_DAY , CAL_MONTH , CAL_YEAR ) "
				+ " VALUES ( ? , ? , ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?) ";

		return insertBillingHistoryQry;
	}

	@BeforeStep
	public void setStepExecution(final StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
}
