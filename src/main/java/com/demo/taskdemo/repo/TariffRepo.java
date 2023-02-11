package com.demo.taskdemo.repo;

import org.springframework.data.repository.CrudRepository;

import com.demo.taskdemo.entities.Tariff;
import com.demo.taskdemo.entities.TariffPK;
import com.demo.taskdemo.repo.intf.CustomBillProcessingRepo;

public interface TariffRepo extends CrudRepository<Tariff, TariffPK>, CustomBillProcessingRepo {

}
