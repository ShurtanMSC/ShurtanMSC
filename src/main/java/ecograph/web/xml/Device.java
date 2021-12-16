package ecograph.web.xml;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    public Date v1;
    public String u1;
    public String vtime;
    public int vstslvl;
    public int vstslvl1;
    public Object hlsts1;
    public Param param;
    public List<String> tag;
    public String man;
    public String id;
    public String type;
    public String text;
}
