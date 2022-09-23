package uz.neft.utils;

import com.google.gson.Gson;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.OpcServer;
import uz.neft.entity.action.CollectionPointAction;

import java.lang.reflect.Type;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
//        Map<String,Object> request=new HashMap<>();
//        request.put("server","Matrikon.OPC.Simulation.1");
//        request.put("unit","Random.Int2");
//        HttpResponse<String> response = Unirest
//                .post("http://127.0.0.1:8000/opc/")
//                .fields(request)
//                .asString();

        String[] array = new String[3];
        OpcServer server=new OpcServer("sim","sim","Matrikon.OPC.Simulation.1","http://127.0.0.1:8000/opc/");
        CollectionPoint collectionPoint=new CollectionPoint();
        collectionPoint.setOpcServer(server);
        collectionPoint.setPressureUnit("Random.Real");
        collectionPoint.setTemperatureUnit("Random.Real");
        CollectionPointAction action= CollectionPointAction
                .builder()
                .collectionPoint(collectionPoint)
                .build();
//        //System.out.println(action.getPressureOpc());
        Gson gson=new Gson();
        //System.out.println(collectionPoint.jsonRequestBodyPressure());
//        HttpResponse<JsonNode> response = Unirest.post(action.getAddress())
//                .header("Content-Type", "application/json")
//                .body(collectionPoint.jsonRequestBodyPressure())
//                .asJson();

        HttpResponse<JsonNode> response = Unirest.post("http://127.0.0.1:8000/opc/")
                .header("Content-Type", "application/json")
                .body("{\r\n    \"server\":\""+collectionPoint.getOpcServer().getAddress()+"\",\r\n    \"unit\":\""+collectionPoint.getPressureUnit()+"\"\r\n}")
                .asJson();
        //System.out.println(response);
        //System.out.println(response.getStatus());
        if (response.getBody()!=null){
            //System.out.println(response.getBody());
            //System.out.println(response.getBody().isArray());
            //System.out.println(response.getBody().getArray());
            String[] a= gson.fromJson(String.valueOf(response.getBody()), (Type) String[].class);
            //System.out.println(Arrays.toString(a));
            //System.out.println(a[0]);
        }

    }


//    public String test(Object o){
//        String s="salom";
//        return switch (o) {
//            case null -> //System.out.println("Null");
//            case "salom" -> //System.out.println("String: " + s);
//            default -> //System.out.println("Other");
//        };
//    }
}
