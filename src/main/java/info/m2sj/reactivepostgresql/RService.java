package info.m2sj.reactivepostgresql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
    public Flux<RNumber> getAllR12() {

        return numberRepository.findAll().flatMap(master -> {
            log.info("pid:" + master.getId());
            return number2Repository.retrieveByPId(master.getId())
                    .collectList()
                    .flatMap(list -> {
                        master.setList(list);
                        return Mono.justOrEmpty(master);
                    }).subscribeOn(Schedulers.elastic()) ;
        });
    }

    public List<String> getBlockingData() throws Exception {
        log.info("Blocking method was executed");
//        Thread.sleep(10000);
        List<String> retList = new ArrayList<>();
        retList.add("abc");
        log.info("step 3");
        return retList;
    }

    public Flux<String> getNonBlockingData() throws Exception {
        log.info("Blocking method was executed");
//        Thread.sleep(10000);
        List<String> retList = new ArrayList<>();
        for(int i=0; i<10; i++) {
            retList.add("abc"+i);
        }
        log.info("step 3");
        return Flux.fromIterable(retList).subscribeOn(Schedulers.elastic());
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

                    List<RNumber2> r2List= new ArrayList<>();
                    for(int i=0; i<100; i++) {
                        RNumber2 rNumber2 = new RNumber2();
                        rNumber2.setName("22-01"+i);
                        rNumber2.setRandomNum(0.01F*i);
                        rNumber2.setPId(x.getId());
                        r2List.add(rNumber2);
                        x.addList(rNumber2);
                    }
//                    x.setList(r2List);

//                    RNumber2 rNumber2_1 = new RNumber2();
//                    rNumber2_1.setName("22-02");
//                    rNumber2_1.setRandomNum(0.22F);
//                    rNumber2_1.setPId(x.getId());

                     number2Repository.saveAll(r2List)
                             .subscribeOn(Schedulers.elastic())
                            .subscribe(r2-> {
                                x.addList(r2);
                            }) ;


                    return Mono.justOrEmpty(x);
                })
                ;
    }

}
