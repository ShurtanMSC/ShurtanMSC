package uz.neft.dto.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.neft.dto.Dto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObjectWithActionsDto implements Dto{

    private Object objectDto;
    private Object objectActionDto;

}
