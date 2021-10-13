package uz.neft.entity.action;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import uz.neft.entity.User;
import uz.neft.entity.Well;
import uz.neft.entity.enums.WellStatus;
import uz.neft.entity.template.AbsEntityLong;
import uz.neft.entity.variables.*;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.MiningSystemGasCompositionRepository;
import uz.neft.repository.UserRepository;
import uz.neft.repository.WellRepository;
import uz.neft.repository.action.WellActionRepository;
import uz.neft.repository.constants.ConstantRepository;
import uz.neft.repository.constants.MiningSystemConstantRepository;
import uz.neft.service.Calculator;
import uz.neft.utils.Converter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WellAction extends AbsEntityLong {
    // Bosim
    private double pressure;

    // Tempratura
    private double temperature;

    //Sredniy rasxod D_skv
    private double average_expend;

    // Rasxod Q
    private double expend;

    // Rpl
    private double rpl;

    @Enumerated(EnumType.STRING)
    private WellStatus status;

    @ManyToOne
    private User user;

    @ManyToOne
    private Well well;

    private int perforation_min;
    private int perforation_max;




//    @LastModifiedBy
//    @Column(nullable = false)
//    private String modifiedBy;
//
//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime modified;

}
