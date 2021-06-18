package info.m2sj.reactivepostgresql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import reactor.blockhound.BlockHound;

@SpringBootApplication
@EnableR2dbcRepositories
public class ReactivepostgresqlApplication {
    static {
        BlockHound.install();
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactivepostgresqlApplication.class, args);
    }
}



