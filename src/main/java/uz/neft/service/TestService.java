//package uz.neft.service;
//
//import de.re.easymodbus.modbusclient.ModbusClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.stereotype.Service;
//import uz.neft.utils.Converter;
//
//import java.util.Arrays;
//
//@Service
//public class TestService {
//    @Autowired
//    private Converter converter;
//    public HttpEntity<?> test(){
//        ModbusClient modbusClient = new ModbusClient("127.0.0.1", 1536);
//        try
//        {
//            modbusClient.Connect();
//            //Read Int value from register 0 (Barrier Command)
//            System.out.println(Arrays.toString(modbusClient.ReadHoldingRegisters(0, 1)));
//            //Read Float Value from Register 1 and 2 (Barrier Status)
//            System.out.println(ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(1, 2)));
//            return converter.apiSuccess200();
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            return converter.apiError409();
//        }
//    }
//}
