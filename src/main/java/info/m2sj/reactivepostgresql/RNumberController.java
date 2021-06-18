package info.m2sj.reactivepostgresql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;
@Slf4j
@RestController
@RequestMapping(value = "/api/numbers")
public class RNumberController {

    @Autowired
    private RNumberRepository numberRepository;

    @Autowired
    private RNumber2Repository number2Repository;

    @Autowired
    private RService rService;

    public RNumberController(RNumberRepository numberRepository) {
        this.numberRepository = numberRepository;
    }

    @GetMapping(value = "/1", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<RNumber> getHome() {
        return numberRepository.findAll();
    }

    @GetMapping(value = "/1/insert", produces = TEXT_EVENT_STREAM_VALUE)
    public Mono<RNumber> getHome1Insert() {
        RNumber rNumber=new RNumber();
        rNumber.setName("2-01");
        rNumber.setRandomNum(0.1F);
        return numberRepository.save(rNumber);
    }

    @GetMapping(value = "/1/insert2", produces = TEXT_EVENT_STREAM_VALUE)
    public Mono<RNumber> getHome10Insert() {

        return rService.saveR12();
    }

    @GetMapping(value = "/1/insert2All", produces = TEXT_EVENT_STREAM_VALUE)
    public Mono<RNumber> getHome10InsertAll() {
        return rService.saveR122();
    }

    @GetMapping(value = "/1/insert2/select", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<RNumber> getHome10InsertSelect() {
        log.info("step 1");
        try {
//            rService.getBlockingData().forEach(x -> {
//                log.info(x);
//            });
            rService.getNonBlockingData()
//                    .parallel(10)
//                    .runOn(Schedulers.newParallel("jay",10))
                    .subscribe(x -> {
                log.info(x);
            });


        }catch (Exception e) {
            e.printStackTrace();
        }
        log.info("step 2");
        return rService.getAllR12();
    }

    @GetMapping(value = "/2", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<RNumber2> getHome2() {
        return number2Repository.findAll();
    }

    @GetMapping(value = "/2/insert", produces = TEXT_EVENT_STREAM_VALUE)
    public Mono<RNumber2> getHome2Insert() {
        RNumber2 rNumber2=new RNumber2();
//        rNumber2.setId(1L);
        rNumber2.setName("2-01");
        rNumber2.setRandomNum(0.1F);
        return number2Repository.save(rNumber2);
    }

}
