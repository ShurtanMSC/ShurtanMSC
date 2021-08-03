package uz.neft.dto.action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uz.neft.dto.CollectionPointDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ObjectWithActionsDto {

    private Object objectDto;
    private Object objectActionDto;

}
