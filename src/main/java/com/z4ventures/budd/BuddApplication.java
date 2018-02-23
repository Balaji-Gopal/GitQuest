package com.z4ventures.budd;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.z4ventures.budd.models.Account;
import com.z4ventures.budd.models.Role;
import com.z4ventures.budd.services.AccountService;

/*import com.z4ventures.budd.models.Account;
import com.z4ventures.budd.models.Role;
import com.z4ventures.budd.services.AccountService;*/

import javax.sql.DataSource;
import java.util.Arrays;

@SpringBootApplication
@EnableAsync
public class BuddApplication extends SpringBootServletInitializer {

	/**
     * Used when run as JAR
     */
	public static void main(String[] args) {
		SpringApplication.run(BuddApplication.class, args);
	}
	
	/**
     * Used when run as WAR
     */
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BuddApplication.class);
    }
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	/*@Bean
	CommandLineRunner init(
			AccountService accountService
	) {
		return (evt) -> Arrays.asList(
				"user,admin,john,robert,ana".split(",")).forEach(
				username -> {
					Account acct = new Account();
					acct.setUsername(username);
					acct.setPassword("password");
					acct.setFirstName(username);
					acct.setLastName("LastName");
					if (username.equals("admin"))
						acct.grantAuthority(Role.ROLE_ADMIN);
					else
						acct.grantAuthority(Role.ROLE_USER);
					accountService.registerUser(acct);
				}
		);
	}*/
}
