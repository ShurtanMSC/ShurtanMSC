package uz.neft.controller.opc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpcRequest {
    private String host;
    private String serverProgId;
    private String serverClientHandle;
    private String unit;
}
