package com.demo.taskdemo.billtask.utils;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.demo.taskdemo.billtask.entites.UnitConsumption;

public class UnitConsumptionFieldSetMapper implements FieldSetMapper<UnitConsumption> {

	@Override
	public UnitConsumption mapFieldSet(FieldSet fieldSet) throws BindException {
		// TODO Auto-generated method stub

		UnitConsumption unitConsuptionObj = new UnitConsumption();
		unitConsuptionObj.setConsumerId(fieldSet.readString("consumerId"));
		unitConsuptionObj.setMeterId(fieldSet.readString("meterId"));
		unitConsuptionObj.setMeterNo(fieldSet.readString("meterNo"));
		unitConsuptionObj.setBillingMonth(fieldSet.readInt("billingMonth"));
		unitConsuptionObj.setBillingYear(fieldSet.readInt("billingYear"));
		unitConsuptionObj.setUnitConsumed(fieldSet.readInt("unitConsumed"));
		unitConsuptionObj.setTariffType(fieldSet.readString("type"));
		unitConsuptionObj.setBillingInsertionDate(fieldSet.readDate("billingInsertionDate", "yyyy-dd-mm"));

		return unitConsuptionObj;

	}

}
