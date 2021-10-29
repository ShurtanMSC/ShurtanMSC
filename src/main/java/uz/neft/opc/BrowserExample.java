package uz.neft.opc;

import javafish.clients.opc.browser.JOpcBrowser;
import javafish.clients.opc.exception.*;

import java.util.Arrays;

public class BrowserExample {

  /**
   * @param args
   */
  public static void main(String[] args) {
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
    catch (HostException e1) {
      e1.printStackTrace();
    }
    catch (NotFoundServersException e1) {
      e1.printStackTrace();
    }
    
//    JOpcBrowser jbrowser = new JOpcBrowser("localhost", "Matrikon.OPC.Simulation", "JOPCBrowser1");
    JOpcBrowser jbrowser = new JOpcBrowser("localhost", "EH_Wetzer.OPC_DA_Server.4", "a");

    try {
      jbrowser.connect();
      String[] branches = jbrowser.getOpcBranch("");
      System.out.println(Arrays.asList(branches));
    }
    catch (ConnectivityException e) {
      e.printStackTrace();
    }
    catch (UnableBrowseBranchException e) {
      e.printStackTrace();
    }
    catch (UnableIBrowseException e) {
      e.printStackTrace();
    }
    
    try {
//      String[] items = jbrowser.getOpcItems("Simulation Items.Random", true);
      String[] items = jbrowser.getOpcItems("Server Information", true);
      if (items != null) {



        System.out.println(items.length);
        for (int i = 0; i < items.length; i++) {
          System.out.println(items[i]);
        }
      }
      // disconnect server
      JOpcBrowser.coUninitialize();
    }
    catch (UnableBrowseLeafException e) {
      e.printStackTrace();
    }
    catch (UnableIBrowseException e) {
      e.printStackTrace();
    }
    catch (UnableAddGroupException e) {
      e.printStackTrace();
    }
    catch (UnableAddItemException e) {
      e.printStackTrace();
    }
    catch (CoUninitializeException e) {
      e.printStackTrace();
    }
  }

}
