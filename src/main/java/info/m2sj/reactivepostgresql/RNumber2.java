package info.m2sj.reactivepostgresql;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("RNUM2")
public class RNumber2 {
    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

//    @Column
    private Float randomNum;

//    @Column
    private String name;

//    @Column
//    private String name2;

//    @Column(name="p_id")
    @Column("p_id")
    private Long pId;
//    private RNumber rNumber;

}
