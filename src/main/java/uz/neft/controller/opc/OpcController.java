package uz.neft.controller.opc;

import javafish.clients.opc.browser.JOpcBrowser;
import javafish.clients.opc.exception.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@CrossOrigin
@RequestMapping("api/opc")
public class OpcController {


    @GetMapping("/get")
    public HttpEntity<?> test(@RequestBody OpcRequest request){
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
        JOpcBrowser jbrowser = new JOpcBrowser(request.getHost(), request.getServerProgId(), request.getServerClientHandle());

        try {
            jbrowser.connect();
            String[] branches = jbrowser.getOpcBranch("");
            System.out.println(Arrays.asList(branches));
        }
        catch (ConnectivityException | UnableBrowseBranchException | UnableIBrowseException e) {
            e.printStackTrace();
        }

        try {
//      String[] items = jbrowser.getOpcItems("Simulation Items.Random", true);
            String[] items = jbrowser.getOpcItems(request.getUnit(), true);
            if (items != null) {
                System.out.println(items.length);
                for (String item : items) {
                    System.out.println(item);
                }
                return ResponseEntity.ok(items);
            }
            // disconnect server
            JOpcBrowser.coUninitialize();
        }
        catch (UnableBrowseLeafException | UnableIBrowseException | UnableAddItemException | CoUninitializeException | UnableAddGroupException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("");
    }

}
