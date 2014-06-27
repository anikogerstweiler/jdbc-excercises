package com.epam.training.jp.jdbc.excercises.service;

import java.util.Date;
import java.util.List;

import com.epam.training.jp.jdbc.excercises.dao.AddressDao;
import com.epam.training.jp.jdbc.excercises.dao.FoodDao;
import com.epam.training.jp.jdbc.excercises.dao.MenuDao;
import com.epam.training.jp.jdbc.excercises.dao.MenuFoodDao;
import com.epam.training.jp.jdbc.excercises.dao.RestaurantDao;
import com.epam.training.jp.jdbc.excercises.domain.Address;
import com.epam.training.jp.jdbc.excercises.domain.Food;
import com.epam.training.jp.jdbc.excercises.dto.RestaurantWithAddress;

public class RestaurantService {

	private final AddressDao addressDao;
	private final RestaurantDao restaurantDao;
	private final FoodDao foodDao;
	private final MenuDao menuDao;
	private final MenuFoodDao menuFoodDao;
	
	public RestaurantService(AddressDao addressDao, RestaurantDao restaurantDao, FoodDao foodDao, MenuDao menuDao, MenuFoodDao menuFoodDao) {
		super();
		this.addressDao = addressDao;
		this.restaurantDao = restaurantDao;
		this.foodDao = foodDao;
		this.menuDao = menuDao;
		this.menuFoodDao = menuFoodDao;
	}

	public void save(Address address) {
		addressDao.save(address);
	}

	public List<Food> getFoodsAvailable(Date date, String restaurantName) {
		return restaurantDao.getFoodsAvailable(date, restaurantName);
	}

	public void updateFoodPriceByName(String name, int newPrice) {
		foodDao.updateFoodPriceByName(name, newPrice);
	}

	public Food findFoodByName(String name) {
		return foodDao.findFoodByName(name);
	}

	public void removeMenu(int id) {
		menuFoodDao.removeMenuFoods(id);
		menuDao.removeMenu(id);
	}

	public List<RestaurantWithAddress> getAllRestaurantsWithAddress() {
		return restaurantDao.getAllRestaurantsWithAddress();
	}

	public void save(List<Food> foods) {
		foodDao.save(foods);
	}

	public List<Food> getFoods() {
		return foodDao.getFoods();
	}
}
