package com.epam.training.jp.jdbc.excercises.dao;

import java.util.List;

import com.epam.training.jp.jdbc.excercises.domain.Food;

public interface FoodDao {

	Food findFoodByName(String name);
	
	void updateFoodPriceByName(String name, int newPrice);	

	void save(List<Food> foods);
	
	List<Food> getFoods();
}
