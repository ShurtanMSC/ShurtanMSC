package uz.neft.dto.fake;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class FakeService {

    public static void main(String[] args) {
        String url="jdbc:sqlserver://192.168.10.20:1433;database=UPPG";
        String userDB="UPPGReader", pass="97F88FA06BB691C96D3B46CC3252452369F4ACEB5E076CFACF4B7BF4B5370A5A43B57702334D3C31";
        all(url,userDB,pass);
    }

    public List<FakeUppg> all(){
        String url="jdbc:sqlserver://192.168.10.20:1433;database=UPPG";
        String userDB="UPPGReader", pass="97F88FA06BB691C96D3B46CC3252452369F4ACEB5E076CFACF4B7BF4B5370A5A43B57702334D3C31";
        return all(url,userDB,pass);
    }

    public static List<FakeUppg> all(String url,String username,String password){
        List<FakeUppg> list=new ArrayList<>();

        try {
            Connection connection=null;
            Statement statement=null;
            ResultSet resultSet=null;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection= DriverManager.getConnection(url,username,password);
            statement=connection.createStatement();
            resultSet=statement.executeQuery("SELECT TOP 144" +
                    "     [ID]" +
                    "     ,[Name]" +
                    "     ,[FullName]" +
                    "     ,[Description]" +
                    "     ,[Val]" +
                    "     ,[Quality]" +
                    "     ,[Time_Stamp]" +
                    "     ,[ScaleMin]" +
                    "     ,[ScaleMax]" +
                    "FROM dbo.Data");

            List<FakeFlowMeter> flowMeters1=new ArrayList<>();
            List<FakeFlowMeter> flowMeters2=new ArrayList<>();
            int counter=0;
            int f1=1;
            int f2=1;
            List<FakeFlowMeterElement> flowMeterElements1=new ArrayList<>();
            List<FakeFlowMeterElement> flowMeterElements2=new ArrayList<>();
            while (resultSet.next()){

                counter++;
                if (counter<=54){

                    FakeFlowMeterElement fakeFlowMeterElement = FakeFlowMeterElement
                            .builder()
                            .id(resultSet.getInt(1))
                            .name(resultSet.getString(2))
                            .fullName(resultSet.getString(3))
                            .description(resultSet.getString(4))
                            .v(resultSet.getDouble(5))
                            .quality(resultSet.getInt(6))
                            .timestamp(resultSet.getString(7))
                            .scaleMin(resultSet.getInt(8))
                            .scaleMax(resultSet.getInt(9))
                            .build();
                    flowMeterElements1.add(fakeFlowMeterElement);

                    if (counter%9==0){
                        flowMeters1.add(FakeFlowMeter
                                .builder()
                                .fakeFlowMeterElements(flowMeterElements1)
                                .build());
                        flowMeterElements1=new ArrayList<>();
//                        f1++;
                    }
//                System.out.println(fakeFlowMeter.getId());
                }
                else {


                    FakeFlowMeterElement fakeFlowMeterElement = FakeFlowMeterElement
                            .builder()
                            .id(resultSet.getInt(1))
                            .name(resultSet.getString(2))
                            .fullName(resultSet.getString(3))
                            .description(resultSet.getString(4))
                            .v(resultSet.getDouble(5))
                            .quality(resultSet.getInt(6))
                            .timestamp(resultSet.getString(7))
                            .scaleMin(resultSet.getInt(8))
                            .scaleMax(resultSet.getInt(9))
                            .build();
                    flowMeterElements2.add(fakeFlowMeterElement);

                    if (counter==64||counter==74||counter==84||counter==94||counter==104||counter==114||counter==124||counter==134||counter==144){
                        flowMeters2.add(FakeFlowMeter
                                .builder()
                                .fakeFlowMeterElements(flowMeterElements2)
                                .build());
                        flowMeterElements2=new ArrayList<>();
//                        f1++;
                    }

                }



//                if (counter==144) break;
            }
//            System.out.println(flowMeters1.size());
//            flowMeters1.forEach(f-> System.out.println(f.toString()));
//            System.out.println(flowMeters1.size());
//            System.out.println(flowMeters2.size());
//            for (FakeFlowMeter fakeFlowMeter : flowMeters1) {
//                for (int j = 0; j < fakeFlowMeter.getFakeFlowMeterElements().size(); j++) {
//                    System.out.println(fakeFlowMeter.getFakeFlowMeterElements().get(j).toString());
//                }
//            }
            FakeUppg fakeUppg1= FakeUppg
                    .builder()
                    .flowMeters(flowMeters1)
                    .build();
            fakeUppg1.sum(true);
            FakeUppg fakeUppg2= FakeUppg
                    .builder()
                    .flowMeters(flowMeters2)
                    .build();
            fakeUppg2.sum(false);
            connection.close();
            list.add(fakeUppg1);
            list.add(fakeUppg2);
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return list;
        }
    }

}
