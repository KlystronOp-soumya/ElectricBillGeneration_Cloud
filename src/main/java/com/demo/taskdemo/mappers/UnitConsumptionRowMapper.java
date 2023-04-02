package com.demo.taskdemo.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.demo.taskdemo.entities.DTO.UnitConsumptionDTO;

public class UnitConsumptionRowMapper implements RowMapper<UnitConsumptionDTO> {

	private static final Logger LOGGER = LogManager.getLogger(UnitConsumptionRowMapper.class);

	@Override
	public UnitConsumptionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		UnitConsumptionDTO unitConsumptionDTO = new UnitConsumptionDTO();
		unitConsumptionDTO.setConsumerId(rs.getString("CONSUMER_ID"));
		unitConsumptionDTO.setMeterId(rs.getString("METER_ID"));
		unitConsumptionDTO.setMeterNo(rs.getString("METER_NO"));
		unitConsumptionDTO.setBillingMonth(rs.getInt("BILL_MONTH"));
		unitConsumptionDTO.setBillingYear(rs.getInt("BILL_YEAR"));
		unitConsumptionDTO.setUnitConsumed(rs.getInt("UNIT_CONSUMED"));
		unitConsumptionDTO.setTariffType(rs.getString("TARIFF_TYPE"));
		unitConsumptionDTO.setTariffId(rs.getString("TARIFF_ID"));
		unitConsumptionDTO.setTariffStartDate(rs.getString("TARIFF_START_DATE"));
		LOGGER.info("Row processed: " + rowNum++);
		return unitConsumptionDTO;

	}

}
