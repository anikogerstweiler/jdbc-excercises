package com.epam.training.jp.jdbc.excercises.dao.jdbctemplateimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.epam.training.jp.jdbc.excercises.dao.RestaurantDao;
import com.epam.training.jp.jdbc.excercises.domain.Address;
import com.epam.training.jp.jdbc.excercises.domain.Food;
import com.epam.training.jp.jdbc.excercises.domain.Restaurant;
import com.epam.training.jp.jdbc.excercises.dto.RestaurantWithAddress;

public class JdbcTemplateRestaurantDao extends JdbcDaoSupport implements RestaurantDao {

	public JdbcTemplateRestaurantDao(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public List<Food> getFoodsAvailable(Date date, String restaurantName) {
		String sql = "SELECT f.ID, f.CALORIES, f.ISVEGAN, f.NAME, f.PRICE FROM sql_excercise.menu m "
		        +"JOIN restaurant r ON m.Restaurant_ID = r.ID "
		        +"JOIN menu_food mf ON mf.Menu_ID = m.ID "
		        +"JOIN food f ON mf.food_ID = f.ID WHERE r.NAME = ? AND ? BETWEEN FROMDATE AND TODATE; " ;
		
		return this.getJdbcTemplate().query(sql, new Object[]{restaurantName, date}, new RowMapper<Food>() {
					
			@Override
			public Food mapRow(ResultSet rs, int rowNum) throws SQLException {
				Food food = new Food();
				food.setId(rs.getInt(1));
				food.setCalories(rs.getInt(1));
				food.setVegan(rs.getBoolean(3));
				food.setName(rs.getString(4));
				food.setPrice(rs.getInt(5));
				
				return food;
			}
		});
	}

	@Override
	public List<RestaurantWithAddress> getAllRestaurantsWithAddress() {
		String sql = "select * from restaurant r join address a on r.address_id = a.id";
		
		return this.getJdbcTemplate().query(sql, new RowMapper<RestaurantWithAddress>() {
					
			@Override
			public RestaurantWithAddress mapRow(ResultSet rs, int rowNum)throws SQLException {
				Restaurant r = new Restaurant();
				r.setId(rs.getInt(1));
				r.setName(rs.getString(2));
				r.setAddressId(rs.getInt(3));
				
				Address a = new Address();
				a.setId(rs.getInt(4));
				a.setCity(rs.getString(5));
				a.setCountry(rs.getString(6));
				a.setStreet(rs.getString(7));
				a.setZipCode(rs.getString(8));
				
				return new RestaurantWithAddress(r, a);
			}
		});	
		
	}
}
