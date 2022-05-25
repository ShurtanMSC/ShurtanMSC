package uz.neft.backup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class BackupMiningSystem {

//    @Scheduled(cron = "*/2 * * * * ?")
    public void dump() throws Exception {
        log.info("backup database");
        String backName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        dataBaseDump("localhost", "5432", "postgres", "postgres", "neftgaz", backName);
    }

    //mysqldump -hlocalhost -P3306 -uroot -p123456 db > E:/back.sql
    //Backup
    public static void dataBaseDump(String host, String port, String username, String password, String databasename, String sqlname) throws Exception {
//        File file = new File("a");
//        if (!file.exists()) {
//            file.mkdir();
//        }
        File datafile = new File(sqlname + ".sql");

        if (datafile.exists()) {
            System.out.println(sqlname + "File name already exists, please change");
            return;
        }
        datafile.createNewFile();
        //Stitch cmd command
//        String executeCmd = "C:/Program Files (x86)/pgAdmin 4/v4/runtime/pg_dump.exe -u " + username +"-h"+ url+"-p"+port+"-t"+table+"--databases " + dbase+"-w"+password+" -r " + savePath;
//        c:\Program files\postgresql\9.3\bin> pg_dump -h localhost -p 5432 -U postgres test > D:\backup.sql
//        pg_dump -F d tecmintdb -f tecmintdumpdir
//        Process exec = Runtime.getRuntime().exec("pg_dump -h" + host + " -P" + port + "-U" + username + " -w" + password + "--databases" + databasename + " > " + datafile);

//        String[] args = new String[] {"pg_dump -h " + host + " -P " + port + " -U " + username + " -w " + password + " --databases " + databasename + " > " + datafile};
//        String[] args = new String[] {"pg_dump --host "+host+" --port "+port+" --username "+username+" --password "+password+" --dbname "+databasename+" > ~/Documents/NeftGaz/Uzlitineftgaz/ShurtanMSC/"+datafile};
        String[] args = new String[] {"/bin/bash", "-c", "cd :~/Documents/NeftGaz/Uzlitineftgaz/ShurtanMSC","pg_dump --host "+host+" --port "+port+" --username "+username+" --password "+password+" --dbname "+databasename+" > "+datafile};
        Process p = new ProcessBuilder(args).start();
        if (p.waitFor() == 0) {
            System.out.println("Database backup is successful, the backup path is:" + datafile);
        }
    }
//
}
