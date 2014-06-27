package com.epam.training.jp.jdbc.excercises.dao.jdbctemplateimpl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.epam.training.jp.jdbc.excercises.dao.AddressDao;
import com.epam.training.jp.jdbc.excercises.domain.Address;

public class JdbcTemplateAddressDao extends JdbcDaoSupport implements AddressDao {

	private SimpleJdbcInsert insertAddress;
	
	public JdbcTemplateAddressDao(DataSource dataSource) {		
		setDataSource(dataSource);
		insertAddress = new SimpleJdbcInsert(dataSource).withTableName("address").usingGeneratedKeyColumns("id");
	}

	@Override
	public void save(Address address) {
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(address);
		Number generatedId = insertAddress.executeAndReturnKey(parameters);
		
		address.setId(generatedId.intValue());
	}

}
