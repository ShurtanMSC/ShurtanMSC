package uz.neft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.Uppg;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionPointDto {

    private Integer id;
    private String name;
    private UppgDto uppgDto;
}
