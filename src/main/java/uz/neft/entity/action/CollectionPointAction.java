package uz.neft.entity.action;

import lombok.*;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.User;
import uz.neft.entity.template.AbsEntityLong;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CollectionPointAction extends AbsEntityLong {

    // Bosim
    private double pressure;

    // Tempratura
    private int temperature;

    // Rasxod
    private double expend;

    @ManyToOne
    private User user;

    @ManyToOne
    private CollectionPoint collectionPoint;

//    @LastModifiedBy
//    @Column(nullable = false)
//    private String modifiedBy;
//
//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime modified;

}
