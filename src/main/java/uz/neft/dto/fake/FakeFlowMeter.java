package uz.neft.dto.fake;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FakeFlowMeter {
    List<FakeFlowMeterElement> fakeFlowMeterElements;
}
