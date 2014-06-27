package com.epam.training.jp.jdbc.excercises.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.epam.training.jp.jdbc.excercises.dao.AddressDao;
import com.epam.training.jp.jdbc.excercises.domain.Address;

public class JdbcAddressDao extends GenericJdbcDao implements AddressDao {

	public JdbcAddressDao(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void save(Address address) {
		String sql = "INSERT INTO address (CITY, COUNTRY, STREET, ZIPCODE) VALUES (?, ?, ?, ?)";
		try (Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, address.getCity());
			ps.setString(2, address.getCountry());
			ps.setString(3, address.getStreet());
			ps.setString(4, address.getZipCode());
			
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				address.setId(rs.getInt(1));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
