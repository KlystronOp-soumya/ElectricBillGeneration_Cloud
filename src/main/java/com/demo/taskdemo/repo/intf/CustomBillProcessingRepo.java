package com.demo.taskdemo.repo.intf;

import java.util.List;
import java.util.Map;

import com.demo.taskdemo.entities.ConstantCharges;
import com.demo.taskdemo.entities.DTO.TariffDTO;
import com.demo.taskdemo.exceptions.BillProcessException;

public interface CustomBillProcessingRepo {

	Map<String, List<TariffDTO>> getLatestTariffSlabDetails(String tariffType) throws BillProcessException;

	ConstantCharges getConstantChargeByTariffType(String tariffType) throws BillProcessException;
}
