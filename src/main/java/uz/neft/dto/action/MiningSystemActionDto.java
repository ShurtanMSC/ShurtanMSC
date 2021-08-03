package uz.neft.dto.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.neft.entity.enums.WellStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MiningSystemActionDto {

    // Rasxod
    private double cost;

    private Integer miningSystemId;

}
