package com.epam.training.jp.jdbc.excercises.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.epam.training.jp.jdbc.excercises.dao.FoodDao;
import com.epam.training.jp.jdbc.excercises.domain.Food;

public class JdbcFoodDao extends GenericJdbcDao implements FoodDao {

	public JdbcFoodDao(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public Food findFoodByName(String name) {
		String sql = "SELECT * FROM food where name = ?";
		Food food = null;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				food = new Food();
				food.setId(rs.getInt(1));
				food.setCalories(rs.getInt(2));
				food.setVegan(rs.getBoolean(3));
				food.setName(rs.getString(4));
				food.setPrice(rs.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return food;
	}

	@Override
	public void updateFoodPriceByName(String name, int newPrice) {
		String sql = "UPDATE FOOD set PRICE = ? where name = ?";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, newPrice);
			ps.setString(2, name);

			int updatedRows = ps.executeUpdate();

			if (updatedRows == 0) {
				throw new IllegalArgumentException(
						"No rows to found where name is " + name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void save(List<Food> foods) {
		String sql = "INSERT INTO food (calories, isvegan, name, price) VALUES (?, ?, ?, ?)";
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			conn.setAutoCommit(false);

			for (Food food : foods) {
				ps.setInt(1, food.getCalories());
				ps.setBoolean(2, food.isVegan());
				ps.setString(3, food.getName());
				ps.setInt(4, food.getId());

				ps.addBatch();
			}

			ps.executeBatch();

			ps.clearBatch();

			conn.commit();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Food> getFoods() {
		String sql = "Select * from FOOD";
		List<Food> foods = new ArrayList<>();
		try (Connection conn = dataSource.getConnection();
			Statement statement = conn.createStatement()) {
			
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				Food food = new Food();
				food.setId(rs.getInt(1));
				food.setCalories(rs.getInt(1));
				food.setVegan(rs.getBoolean(3));
				food.setName(rs.getString(4));
				food.setPrice(rs.getInt(5));
				
				foods.add(food);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return foods;
	}
}
