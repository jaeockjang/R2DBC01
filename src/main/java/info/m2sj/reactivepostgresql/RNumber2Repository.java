package info.m2sj.reactivepostgresql;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RNumber2Repository extends ReactiveCrudRepository<RNumber2, Long> {
    @Query("select * from test.rnum2 where p_id= :pId")
    Flux<RNumber2> retrieveByPId(long pId);
}