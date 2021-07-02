package uz.neft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "constant")
public class Constant extends AbsEntityInteger {

    private String name;
    private String description;


}
