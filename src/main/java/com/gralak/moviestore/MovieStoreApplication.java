package com.gralak.moviestore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gralak.moviestore.appuser.AppUser;
import com.gralak.moviestore.appuser.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;

import static com.gralak.moviestore.appuser.AppUserRole.*;

@SpringBootApplication
@ComponentScan({"com.gralak.moviestore"})
public class MovieStoreApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(MovieStoreApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(AppUserService appUserService)
    {
        return args ->
        {
            appUserService.saveUser(new AppUser(
                    "user",
                    "user",
                    Collections.singletonList(ROLE_USER)));

            appUserService.saveUser(new AppUser(
                    "admin",
                    "admin",
                    Arrays.asList(ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_USER)));

            appUserService.saveUser(new AppUser(
                    "employee",
                    "employee",
                    Arrays.asList(ROLE_EMPLOYEE, ROLE_USER)));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper objectMapper()
    {
        return new ObjectMapper();
    }

	/*@Bean
	CommandLineRunner runner(MovieRepo movieRepo,
							 WarehouseRepo warehouseRepo,
							 MovieWarehouseRepo movieWarehouseRepo)
	{
		return args -> {

			Movie movie = new Movie("The Lord Of The Rings I", 2001, "Peter Jackson",
					Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
			Movie movie2 = new Movie("The Lord Of The Rings II", 2002, "Peter Jackson",
					Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);
			Movie movie3 = new Movie("The Lord Of The Rings III", 2003, "Peter Jackson",
					Arrays.asList("Fantasy", "Adventure"), Arrays.asList("USA", "New Zealand"), 39.99);

			Warehouse warehouse = new Warehouse("Kreta 19", "Warszawa", "00-897");
			Warehouse warehouse2 = new Warehouse("Wielkopolska 19", "Poznań", "00-210");
			Warehouse warehouse3 = new Warehouse("Małopolska 19", "Kraków", "00-654");

			MovieWarehouse movieWarehouse = new MovieWarehouse(movie, warehouse, 10);
			MovieWarehouse movieWarehouse2 = new MovieWarehouse(movie, warehouse2, 20);
			MovieWarehouse movieWarehouse3 = new MovieWarehouse(movie, warehouse3, 30);
			MovieWarehouse movieWarehouse4 = new MovieWarehouse(movie2, warehouse, 10);
			MovieWarehouse movieWarehouse5 = new MovieWarehouse(movie3, warehouse, 10);

			movieRepo.saveAll(Arrays.asList(movie, movie2, movie3));

			warehouseRepo.saveAll(Arrays.asList(warehouse, warehouse2, warehouse3));

			movieWarehouseRepo.saveAll(Arrays.asList(movieWarehouse, movieWarehouse2, movieWarehouse3, movieWarehouse4, movieWarehouse5));
		};
	}*/
}