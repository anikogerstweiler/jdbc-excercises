package com.epam.training.jp.jdbc.excercises.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.epam.training.jp.jdbc.excercises.dao.MenuDao;

public class JdbcMenuDao extends GenericJdbcDao implements MenuDao {

	public JdbcMenuDao(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void removeMenu(int id) {
		String sql = "DELETE FROM MENU where id = ?";
		try (Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			ps.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
