package uz.neft.service;

import de.re.easymodbus.modbusclient.ModbusClient;

import java.util.Arrays;

public class ModbusTest {
    public static void main(String[] args) {
        ModbusClient modbusClient = new ModbusClient("10.10.24.90", 8002);
        try
        {
//            modbusClient.setConnectionTimeout(10000);
            modbusClient.Connect();
            //Read Int value from register 0 (Barrier Command)
            System.out.println(Arrays.toString(modbusClient.ReadHoldingRegisters(200, 1)));
            System.out.println(Arrays.toString(modbusClient.receiveData));
            //Read Float Value from Register 1 and 2 (Barrier Status)
//            System.out.println(ModbusClient.ConvertRegistersToFloat(modbusClient.ReadHoldingRegisters(1, 2)));

        }
        catch (Exception e)
        {
//            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
