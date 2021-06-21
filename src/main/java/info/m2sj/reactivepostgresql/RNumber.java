package info.m2sj.reactivepostgresql;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("RNUM")
public class RNumber {
    @Id
    private Long id;
//    @Column
    private Float randomNum;
//    @Column
    private String name;
//    @Column
//    private String name2;

    @Transient
//    @OneToMany(mappedBy = "rNumber",fetch = FetchType.LAZY, targetEntity = RNumber2.class)
//    @ForeignTable(joinkey = "myLink")
    private List<RNumber2> list=new ArrayList<>();

    public void addList(RNumber2 r2) {
        if (this.list ==null) this.list=new ArrayList<>();
        this.list.add(r2);
    }

    public RNumber(RNumber rNumber) {
        this.setId(rNumber.id);
        this.setName(rNumber.name);

    }
}
