package com.epam.training.jp;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.epam.training.jp.jdbc.excercises.spring.SpringConfigurationDataSource;
import com.epam.training.jp.jdbc.excercises.spring.SpringConfigurationJdbcTemplateDao;
import com.epam.training.jp.jdbc.excercises.spring.SpringConfigurationService;

public class JdbcTemplateApp extends JdbcApp {

	public static void main(String[] args) {
		JdbcTemplateApp app = new JdbcTemplateApp();
		app.run();
		
	}

	@Override
	protected AbstractApplicationContext createSpringContext() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(
				SpringConfigurationDataSource.class,
				SpringConfigurationJdbcTemplateDao.class,
				SpringConfigurationService.class);
		return context;
	}
	
}
