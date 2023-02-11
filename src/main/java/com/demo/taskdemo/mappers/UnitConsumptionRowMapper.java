package com.demo.taskdemo.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.demo.taskdemo.entities.UnitConsumption;
import com.demo.taskdemo.entities.UnitConsumptionPK;

public class UnitConsumptionRowMapper implements RowMapper<UnitConsumption> {

	@Override
	public UnitConsumption mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		UnitConsumptionPK unitConsumptionPK = new UnitConsumptionPK(rs.getString("CONSUMER_ID"),
				rs.getString("METER_ID"), rs.getString("METER_NO"), rs.getInt("BILL_MONTH"), rs.getInt("BILL_YEAR"));

		UnitConsumption unitConsumption = new UnitConsumption();
		unitConsumption.setUnitConsumptionPK(unitConsumptionPK);
		unitConsumption.setUnitConsumed(rs.getInt("UNIT_CONSUMED"));
		unitConsumption.setTariffType(rs.getString("TARIFF_TYPE"));

		return unitConsumption;

	}

}
