package com.demo.taskdemo;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.taskdemo.repo.TariffRepo;

@SpringBootTest(classes = ElectricBillProcessingApplication.class)
@RunWith(SpringRunner.class)
public class ElectricBillProcessingApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private TariffRepo tariffRepo;

	@PersistenceContext(unitName = "billProcessingPersistenceUnit")
	private EntityManager entuEntityManager;

	@Test
	void contextLoads() {
	}

	@Test
	public void check_DataBaseConnection() throws SQLException {
		assertThat(dataSource.getConnection()).isNotNull();
	}

	@Test
	public void check_persistenceContext() {
		assertThat(this.entuEntityManager).isNotNull();
	}

	@Test
	public void check_tariffTable() {
		Query query = this.entuEntityManager.createQuery("SELECT COUNT(*) FROM TARIFF");
		int totalRows = Integer.parseInt(query.getSingleResult().toString());

		assertThat(totalRows).isEqualTo(8);
	}

}
