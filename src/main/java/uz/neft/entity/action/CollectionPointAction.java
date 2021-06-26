package uz.neft.entity.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.User;
import uz.neft.entity.template.AbsEntityLong;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CollectionPointAction extends AbsEntityLong {

    // Bosim
    private double pressure;

    // Tempratura
    private int temperature;

    // Rasxod
    private double cost;

    @ManyToOne
    private User user;

    @ManyToOne
    private CollectionPoint collectionPoint;

}
