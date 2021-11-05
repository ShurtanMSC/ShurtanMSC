package uz.neft.controller.opc;

import javafish.clients.opc.browser.JOpcBrowser;
import javafish.clients.opc.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.entity.action.CollectionPointAction;
import uz.neft.entity.enums.OpcServerType;

import java.security.SecureRandom;
import java.util.Arrays;

@Service
public class OpcService {


    public double getValue(CollectionPointAction collectionPointAction,String unit){
        try {
            if (collectionPointAction.getCollectionPoint().getOpcServer().getType().equals(OpcServerType.REAL)){
                try {
                    JOpcBrowser.coInitialize();
                }
                catch (CoInitializeException e1) {
                    e1.printStackTrace();
                }

                // find opc-servers (OpcEnum interface)
                try {
                    String[] opcServers = JOpcBrowser.getOpcServers("localhost");
                    System.out.println(Arrays.asList(opcServers));
                }
                catch (HostException | NotFoundServersException e1) {
                    e1.printStackTrace();
                }

//    JOpcBrowser jbrowser = new JOpcBrowser("localhost", "Matrikon.OPC.Simulation", "JOPCBrowser1");
                JOpcBrowser jbrowser = new JOpcBrowser("localhost", collectionPointAction.getCollectionPoint().getOpcServer().getAddress(),String.valueOf( collectionPointAction.getCollectionPoint().getId()));

                try {
                    jbrowser.connect();
                    String[] branches = jbrowser.getOpcBranch("");
                    System.out.println(Arrays.asList(branches));
                }
                catch (ConnectivityException | UnableBrowseBranchException | UnableIBrowseException e) {
                    e.printStackTrace();
                }

                try {
                    String[] parts = unit.split("\\.");
                    System.out.println(Arrays.toString(parts));
//      String[] items = jbrowser.getOpcItems("Simulation Items.Random", true);

                    String[] items = jbrowser.getOpcItems(parts[0]+"."+parts[1], true);
                    if (items != null) {
                        System.out.println(items.length);
//                    System.out.println(items);
                        for (String item : items) {
                            if (item.contains(parts[0])&&item.contains(parts[2])){
                                String[] itemParts =item.split(";");
                                System.out.println(Arrays.toString(itemParts));
                                if (itemParts.length==4) return Double.parseDouble(itemParts[3].replace(",","."));
                            }
//                        System.out.println(item);
                        }
                    }
                    // disconnect server
                    JOpcBrowser.coUninitialize();
                }
                catch (UnableBrowseLeafException | UnableIBrowseException | UnableAddItemException | CoUninitializeException | UnableAddGroupException e) {
                    e.printStackTrace();
                }
                return 0.0;
            }else {
                return new SecureRandom().nextFloat()*(16.0-13.0)+13.0;
            }

        }catch (Exception e){
            e.printStackTrace();
            return 0.0;
        }
    }


}
