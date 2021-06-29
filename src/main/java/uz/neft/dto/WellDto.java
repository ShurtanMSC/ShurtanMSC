package uz.neft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WellDto {

    private Integer id;
    private Integer number;
    private CollectionPointDto collectionPointDto;

}
