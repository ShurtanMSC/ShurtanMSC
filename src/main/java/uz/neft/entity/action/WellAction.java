package uz.neft.entity.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import uz.neft.entity.User;
import uz.neft.entity.Well;
import uz.neft.entity.enums.WellStatus;
import uz.neft.entity.template.AbsEntityLong;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WellAction extends AbsEntityLong {

    // Bosim
    private double pressure;

    // Tempratura
    private int temperature;

    // Rasxod
    private double cost;

    // Rpl
    private double rpl;

    @Enumerated(EnumType.STRING)
    private WellStatus status;

    @ManyToOne
    private User user;

    @ManyToOne
    private Well well;


    @LastModifiedBy
    @Column(nullable = false)
    private String modifiedBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modified;

}
