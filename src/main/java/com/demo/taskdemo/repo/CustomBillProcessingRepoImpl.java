package com.demo.taskdemo.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.demo.taskdemo.entities.ConstantCharges;
import com.demo.taskdemo.entities.DTO.TariffDTO;
import com.demo.taskdemo.exceptions.BillProcessException;
import com.demo.taskdemo.repo.intf.CreateMapKey;
import com.demo.taskdemo.repo.intf.CustomBillProcessingRepo;

public class CustomBillProcessingRepoImpl implements CustomBillProcessingRepo {

	private static final Logger LOGGER = LogManager.getLogger(CustomBillProcessingRepoImpl.class);
	private JdbcTemplate jdbcTemplate;

	public CustomBillProcessingRepoImpl(final JdbcTemplate jdbcTemplate) {
		// TODO Auto-generated constructor stub
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Map<String, List<TariffDTO>> getLatestTariffSlabDetails(String tariffType) throws BillProcessException {
		// TODO Auto-generated method stub
		LOGGER.info("getting the list of tariff slab details");
		Map<String, List<TariffDTO>> tariffSlabDetailsMap;
		List<TariffDTO> tariffRowObjectList;
		String key = null;
		String slabDetailsQuery = "select sl.slab_start as slb_st, sl.slab_end as slb_end , sl.slab_count as slb_cnt , sl.slab_rate as slb_rt , sl.tariff_id as slb_tarfId, "
				+ " tr.TARIFF_TYPE as trf_typ ,tr.TARIFF_START_DATE as trf_strt_dt \r\n"
				+ "from tariff_slabs sl , tariff tr\r\n" + " where sl.TARIFF_ID = tr.TARIFF_ID\r\n"
				+ " and tr.TARIFF_ID = (select a.TARIFF_ID from cyolasbcom.tariff as a where trim(TARIFF_TYPE)=trim(?) "
				+ " and TARIFF_START_DATE = (select MAX(b.TARIFF_START_DATE) from cyolasbcom.tariff as b "
				+ " where b.TARIFF_TYPE=? ) ) ";

		List<TariffDTO> tariffSlabRecords = this.jdbcTemplate.query(slabDetailsQuery, (ps) -> {
			ps.setString(1, tariffType);
			ps.setString(2, tariffType);
		}, new RowMapper<TariffDTO>() {

			@Override
			public TariffDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				TariffDTO tariffDTO = new TariffDTO();
				tariffDTO.setSlabStart(rs.getInt("slb_st"));
				tariffDTO.setSlabEnd(rs.getInt("slb_end"));
				tariffDTO.setSlabCount(rs.getInt("slb_cnt"));
				tariffDTO.setSlabRate(rs.getBigDecimal("slb_rt"));
				tariffDTO.setTariffId(rs.getString("slb_tarfId"));
				tariffDTO.setTariffType(rs.getString("trf_typ"));
				tariffDTO.setTariffStartDate(rs.getString("trf_strt_dt"));
				return tariffDTO;

			}
		});

		if (!tariffSlabRecords.isEmpty()) {
			// then build the hashmap
			LOGGER.info("slab list found");
			tariffSlabDetailsMap = new HashMap<>();
			for (TariffDTO eachRow : tariffSlabRecords) {
				key = createTariffMapKey(eachRow.getTariffId(), eachRow.getTariffType(), eachRow.getTariffStartDate());

				if (!tariffSlabDetailsMap.containsKey(key)) {
					tariffRowObjectList = new ArrayList<>();
					tariffRowObjectList.add(eachRow);
					tariffSlabDetailsMap.put(key, tariffRowObjectList);

				} else {
					// then add to the list
					// take the list
					List<TariffDTO> tempTariffDTOList = tariffSlabDetailsMap.get(key);
					// add the current object
					tempTariffDTOList.add(eachRow);
					// place the updated list inside map
					tariffSlabDetailsMap.put(key, tempTariffDTOList);
				}

			}

		} else {
			throw new NullPointerException("Tariff records are null");
		}

		LOGGER.info("Slab list returned to Service: ");
		return tariffSlabDetailsMap;
	}

	protected String createTariffMapKey(final String tariffId, final String tariffType, final String tariffStartDt) {

		CreateMapKey generateKey = () -> {
			return tariffId.concat("|").concat(tariffType).concat("|").concat(tariffStartDt);
		};

		return generateKey.get();
	}

	@Override
	public ConstantCharges getConstantChargeByTariffType(String tariffType) throws BillProcessException {
		// TODO Auto-generated method stub
		ConstantCharges constantCharge = null;
		final String fetchLtstConstntChrgQuery = "SELECT CHARGE_TYPE , FIXED_RATE , DUTY , MVCA_RATE , METER_RENT , START_DATE FROM CONST_CHARGES  WHERE CHARGE_TYPE=? "
				+ "AND START_DATE = (SELECT MAX(START_DATE) FROM CONST_CHARGES WHERE CHARGE_TYPE=?)";
		try {
			constantCharge = this.jdbcTemplate.queryForObject(fetchLtstConstntChrgQuery,
					new RowMapper<ConstantCharges>() {

						@Override
						public ConstantCharges mapRow(ResultSet rs, int rowNum) throws SQLException {
							// TODO Auto-generated method stub
							ConstantCharges constantCharges = new ConstantCharges(rs.getString("CHARGE_TYPE"),
									rs.getBigDecimal("FIXED_RATE"), rs.getBigDecimal("DUTY"),
									rs.getBigDecimal("MVCA_RATE"), rs.getBigDecimal("METER_RENT"),
									rs.getString("START_DATE"));

							return constantCharges;
						}
					}, tariffType, tariffType);
			if (constantCharge == null)
				throw new NullPointerException("No constant charges available for this tariffType: " + tariffType);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		LOGGER.info("Constant charges returned");
		return constantCharge;
	}
}
