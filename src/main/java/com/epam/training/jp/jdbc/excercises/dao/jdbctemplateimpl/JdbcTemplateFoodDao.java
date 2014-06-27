package com.epam.training.jp.jdbc.excercises.dao.jdbctemplateimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.epam.training.jp.jdbc.excercises.dao.FoodDao;
import com.epam.training.jp.jdbc.excercises.domain.Food;

public class JdbcTemplateFoodDao extends JdbcDaoSupport implements FoodDao {

	public JdbcTemplateFoodDao(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public Food findFoodByName(String name) {
		Food food = this.getJdbcTemplate().queryForObject(
		          "SELECT ID, CALORIES, ISVEGAN, NAME, PRICE from FOOD WHERE NAME = ?",
		          new String[]{name},
		          new RowMapper<Food>() {
		              public Food mapRow(ResultSet rs, int rowNum) throws SQLException {
		                  Food food = new Food();
		                  food.setId(rs.getInt(1));
					      food.setCalories(rs.getInt(2));
					      food.setVegan(rs.getBoolean(3));
					      food.setName(rs.getString(4));
					      food.setPrice(rs.getInt(5));
					      
		                  return food;
		              }
		          });
		  return food;
	}

	@Override
	public void updateFoodPriceByName(String name, int newPrice) {
		this.getJdbcTemplate().update("update food set price = ? where name = ?", new Object[]{newPrice, name});
	}

	@Override
	public void save(final List<Food> foods) {
		List<Object[]> batch = new ArrayList<>();
		for (Food food : foods) {
			Object[] values = new Object[] {
					food.getCalories(),
					food.isVegan(),
					food.getName(),
					food.getPrice()};
			batch.add(values);
		}
		
		int[] count = this.getJdbcTemplate().
				batchUpdate("INSERT INTO food (calories, isvegan, name, price) VALUES (?, ?, ?, ?)", batch);
		
	}
}
