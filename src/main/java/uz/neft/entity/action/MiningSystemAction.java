package uz.neft.entity.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.template.AbsEntityLong;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MiningSystemAction extends AbsEntityLong {

    private double cost;

    @ManyToOne
    private MiningSystem miningSystem;
}
