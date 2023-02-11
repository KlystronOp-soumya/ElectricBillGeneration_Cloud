package com.demo.taskdemo.billtask.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.demo.taskdemo.billtask.entites.UnitConsumption;
import com.demo.taskdemo.billtask.exceptions.BillLoadingException;

//@Component("billDataCSVReader")
public class BillDataCSVReader {

	private static final Logger LOGGER = LogManager.getLogger(BillDataCSVReader.class);

	private FlatFileItemReader<UnitConsumption> unitConsumptionCSVReader;
	private DefaultLineMapper<UnitConsumption> lineMapper;
	private Resource billDataResource;

	public BillDataCSVReader() {
		// TODO Auto-generated constructor stub
		this.unitConsumptionCSVReader = new FlatFileItemReader<>();
		this.lineMapper = new DefaultLineMapper<>();
	}

	public FlatFileItemReader<UnitConsumption> itemReader() {

		try {
			initResource();
			initReader();
			LOGGER.info("ItemReader setup");
		} catch (BillLoadingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.unitConsumptionCSVReader;
	}

	@SuppressWarnings("static-access")
	private void initReader() throws BillLoadingException {
		try {
			DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
			lineTokenizer.setDelimiter(lineTokenizer.DELIMITER_COMMA);
			lineTokenizer.setNames(new String[] { "consumerId", "meterId", "meterNo", "billingMonth", "billingYear",
					"unitConsumed", "type", "billingInsertionDate" });
			FieldSetMapper<UnitConsumption> fieldSetMapper = new UnitConsumptionFieldSetMapper();
			this.lineMapper.setLineTokenizer(lineTokenizer);
			this.lineMapper.setFieldSetMapper(fieldSetMapper);
			this.unitConsumptionCSVReader.setLinesToSkip(1);
			this.unitConsumptionCSVReader.setLineMapper(lineMapper);
			this.unitConsumptionCSVReader.setStrict(true);
			this.unitConsumptionCSVReader.setResource(this.billDataResource);
			LOGGER.info("Unit Consumption CSV Reader Setup");
		} catch (Exception e) {
			// TODO: handle exception
			throw new BillLoadingException(e.getCause(), e.getMessage());
		}
	}

	// getter for Resource
	private void initResource() throws BillLoadingException {
		try {
			// this.billDataResource = new
			// ClassPathResource(Constants.INPUT_CSV_PATH.getValue());
			this.billDataResource = new FileSystemResource(Constants.INPUT_CSV_PATH.getValue());
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("Could not load the CSV", e);
			throw new BillLoadingException(e.getCause(), e.getMessage());
		}

	}
}
