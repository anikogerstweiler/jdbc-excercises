package com.epam.training.jp.jdbc.excercises.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.epam.training.jp.jdbc.excercises.dao.RestaurantDao;
import com.epam.training.jp.jdbc.excercises.domain.Address;
import com.epam.training.jp.jdbc.excercises.domain.Food;
import com.epam.training.jp.jdbc.excercises.domain.Restaurant;
import com.epam.training.jp.jdbc.excercises.dto.RestaurantWithAddress;

public class JdbcRestaurantDao extends GenericJdbcDao implements RestaurantDao {

	public JdbcRestaurantDao(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public List<Food> getFoodsAvailable(Date date, String restaurantName) {
		String sql = "SELECT f.ID, f.CALORIES, f.ISVEGAN, f.NAME, f.PRICE FROM sql_excercise.menu m "
		        +"JOIN restaurant r ON m.Restaurant_ID = r.ID "
		        +"JOIN menu_food mf ON mf.Menu_ID = m.ID "
		        +"JOIN food f ON mf.food_ID = f.ID WHERE r.NAME = ? AND ? BETWEEN FROMDATE AND TODATE; " ;
		
		java.sql.Date d = new java.sql.Date(date.getTime());
		
		List<Food> foods = new ArrayList<>();
		try (Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, restaurantName);
			ps.setDate(2, d);
			
			ResultSet rs = ps.executeQuery();
			
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

	@Override
	public List<RestaurantWithAddress> getAllRestaurantsWithAddress() {
		String sql = "select * from restaurant r join address a on r.address_id = a.id";
		
		List<RestaurantWithAddress> restaurants = new ArrayList<>();
		try (Connection conn = dataSource.getConnection();
			Statement statement = conn.createStatement()) {
				ResultSet rs = statement.executeQuery(sql);
				
				while(rs.next()) {
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
					
					RestaurantWithAddress rwa = new RestaurantWithAddress(r, a);
					
					restaurants.add(rwa);
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return restaurants;
	}
}
