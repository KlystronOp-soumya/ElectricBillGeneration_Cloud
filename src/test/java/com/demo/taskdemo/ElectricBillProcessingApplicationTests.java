package com.demo.taskdemo;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.taskdemo.repo.TariffRepo;

@SpringBootTest
@RunWith(SpringRunner.class)
class ElectricBillProcessingApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private TariffRepo tariffRepo;

	@Test
	void contextLoads() {
	}

	@Test

	@Order(value = 1)
	public void check_DataBaseConnection() throws SQLException {
		assertThat(dataSource.getConnection()).isNotNull();
	}

	@Test

	@Order(value = 2)
	public void check_TableCreation() {
		int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM TARIFF", Integer.class);
		assertThat(count).isEqualTo(3);

	}

	@Test
	public void check_persisted_TableCreation() {
		int count = (int) this.tariffRepo.count();
		assertThat(count).isEqualTo(3);
	}

	@Test
	@Order(value = 4)
	public void check_JdbcTemplate_Insert_Tariff_Data() {
		// jdbcTemplate.execute("INSERT INTO TARIFF VALUES('T113' , '2015-01-01')");
		// int count = entityManager.createNativeQuery("INSERT INTO tariff VALUES('T114'
		// ,'2015-01-01')").executeUpdate();
		// entityManager.getTransaction().begin();
		jdbcTemplate.execute("INSERT INTO tariff VALUES('T117' ,'2015-01-01')");
		// entityManager.joinTransaction();
		// entityManager.getTransaction().commit();

	}

}
