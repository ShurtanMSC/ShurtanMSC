package uz.neft.entity.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.neft.entity.User;
import uz.neft.entity.Well;
import uz.neft.entity.template.AbsEntityLong;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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

    private boolean status;

    @ManyToOne
    private User user;

    @ManyToOne
    private Well well;

}
