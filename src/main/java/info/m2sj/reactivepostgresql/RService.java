package info.m2sj.reactivepostgresql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;

@Service
@Slf4j
public class RService {


    @Autowired
    private RNumberRepository numberRepository;
    @Autowired

    private RNumber2Repository number2Repository;

    @Transactional
    public Mono<RNumber> saveR12() {
        RNumber rNumber=new RNumber();
        rNumber.setName("11-01");
        rNumber.setRandomNum(0.1F);

       return numberRepository.save(rNumber)
               .flatMap(x-> {
                    System.out.println("pid:" + x.getId());
                    System.out.println("step 1:"+ x);
                    RNumber2 rNumber2 = new RNumber2();
                    rNumber2.setName("22-01");
                    rNumber2.setRandomNum(0.2F);
                    rNumber2.setPId(x.getId());

                  return number2Repository.save(rNumber2).flatMap (y-> {
                             System.out.println("cid:" + y.getId());
                            x.setList(Arrays.asList(y));
                            System.out.println("step 2:"+ x);
                        return Mono.justOrEmpty(x);
                    });
               });
    }

    @Transactional(readOnly = true)
    public Flux<RNumber> getAllR12(){
        Flux<RNumber> rNumberFlux= numberRepository.findAll();
        return rNumberFlux.flatMap(x -> {
            log.info("pid:"+x.getId());
            List<RNumber2> listDetail=new ArrayList<>();
            return number2Repository.retrieveByPId(x.getId())
            .collectList().flatMap( list -> {
                listDetail.addAll(list);
                x.setList(listDetail);
                return Mono.justOrEmpty(x);
            });
        });
    }



    @Transactional
    public Mono<RNumber> saveR122() {
        RNumber rNumber=new RNumber();
        rNumber.setName("11-01");
        rNumber.setRandomNum(0.1F);

        return numberRepository.save(rNumber)
                .flatMap(x-> {
                    System.out.println("pid:" + x.getId());
                    System.out.println("step 1:"+ x);
                    RNumber2 rNumber2 = new RNumber2();
                    rNumber2.setName("22-01");
                    rNumber2.setRandomNum(0.2F);
                    rNumber2.setPId(x.getId());

                    RNumber2 rNumber2_1 = new RNumber2();
                    rNumber2_1.setName("22-02");
                    rNumber2_1.setRandomNum(0.22F);
                    rNumber2_1.setPId(x.getId());

                    number2Repository.saveAll(Arrays.asList(rNumber2,rNumber2_1)).subscribe();

                    return Mono.justOrEmpty(x);
                });
    }

}
