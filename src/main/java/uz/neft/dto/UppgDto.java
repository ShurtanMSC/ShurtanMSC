package uz.neft.dto;

import lombok.*;
import uz.neft.entity.MiningSystem;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UppgDto {

    private Integer id;
    private String name;
    private MiningSystemDto miningSystem;

}
