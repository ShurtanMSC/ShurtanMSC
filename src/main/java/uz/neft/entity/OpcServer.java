package uz.neft.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.neft.entity.template.AbsEntityInteger;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class OpcServer extends AbsEntityInteger {
    private String name;
    private String description;
    private String address;
    private String url;
}
