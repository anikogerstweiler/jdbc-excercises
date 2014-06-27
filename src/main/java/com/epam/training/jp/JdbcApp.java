package com.epam.training.jp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.epam.training.jp.jdbc.excercises.domain.Address;
import com.epam.training.jp.jdbc.excercises.domain.Food;
import com.epam.training.jp.jdbc.excercises.helper.DatabaseCreator;
import com.epam.training.jp.jdbc.excercises.service.RestaurantService;
import com.epam.training.jp.jdbc.excercises.spring.SpringConfigurationDataSource;
import com.epam.training.jp.jdbc.excercises.spring.SpringConfigurationJdbcDao;
import com.epam.training.jp.jdbc.excercises.spring.SpringConfigurationService;

public class JdbcApp {

	public static void main(String[] args) {
		JdbcApp app = new JdbcApp();
		app.run();
	}
	
	protected void run() {
		AbstractApplicationContext context = createSpringContext();
		
		RestaurantService restaurantService = context.getBean(RestaurantService.class);
		DatabaseCreator databaseCreator = context.getBean(DatabaseCreator.class);
		
		databaseCreator.createAndPopulateDatabase();
				
		Address address = new Address();
		address.setCity("Budapest");
		address.setCountry("HU");
		address.setStreet("Futo utca");
		address.setZipCode("1085");
				
		restaurantService.save(address);
		
		System.out.println("Retreived id of saved address: " + address.getId());
		
		System.out.println("Number of foods before delete menu #1: " + restaurantService.getFoodsAvailable(new Date(), "10 MINUTES").size());
		restaurantService.removeMenu(1);
		System.out.println("Number of foods after delete menu #1: " + restaurantService.getFoodsAvailable(new Date(), "10 MINUTES").size());
		
		System.out.println("Price before update food: " + restaurantService.findFoodByName("Tender lion").getPrice());
		restaurantService.updateFoodPriceByName("Tender lion", 7000);
		System.out.println("Price after update food: " + restaurantService.findFoodByName("Tender lion").getPrice());
		
		List<Food> foods = createFoods();
		restaurantService.save(foods);
		
		System.out.println("Foods: " + restaurantService.getFoods());

		System.out.println("Restaurants: " + restaurantService.getAllRestaurantsWithAddress());
		
		restaurantService.removeMenu(1);		
		
		context.close();
		
	}

	private List<Food> createFoods() {
		List<Food> foods = new ArrayList<>();
		Food f1 = new Food();
		f1.setCalories(1000);
		f1.setName("gulyas");
		f1.setPrice(890);
		f1.setVegan(false);
		foods.add(f1);
		
		Food f2 = new Food();
		f2.setCalories(10);
		f2.setName("szilvasgomboc");
		f2.setPrice(1000);
		f2.setVegan(true);
		foods.add(f2);
		return foods;
	}
	
	protected AbstractApplicationContext createSpringContext() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(
				SpringConfigurationDataSource.class,
				SpringConfigurationJdbcDao.class,
				SpringConfigurationService.class);
		return context;
	}
}
