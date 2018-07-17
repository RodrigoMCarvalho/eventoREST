package br.com.eventosRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@SpringBootApplication
public class EventosRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventosRestApplication.class, args);
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter(){
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true); //criação de tabelas automaticamente pelo Hibernate
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
		adapter.setPrepareConnection(true);
		
		return adapter;
}
}
