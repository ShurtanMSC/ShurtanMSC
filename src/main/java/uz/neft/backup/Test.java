package uz.neft.backup;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.neft.backup.my.options.PropertiesOptions;
import uz.neft.backup.my.services.PostgresqlExportService;
import uz.neft.utils.Converter;

import java.io.File;
import java.util.Properties;

@Service
public class Test {
    @Autowired
    Logger logger;
    @Value("${backup.file.path}")
    private String backupPath ;
    @Autowired
    private Converter converter;
    public HttpEntity<?> backup() {

        try{
            Properties properties = new Properties();
            properties.setProperty(PropertiesOptions.DB_NAME, "neftgaz");
            properties.setProperty(PropertiesOptions.DB_USERNAME, "postgres");
            properties.setProperty(PropertiesOptions.DB_PASSWORD, "postgres");

//properties relating to email config
            properties.setProperty(PropertiesOptions.EMAIL_HOST, "smtp.gmail.com");
            properties.setProperty(PropertiesOptions.EMAIL_PORT, "587");
            properties.setProperty(PropertiesOptions.EMAIL_USERNAME, "pdpemailtest@gmail.com");
            properties.setProperty(PropertiesOptions.EMAIL_PASSWORD, "12020202pdp");
            properties.setProperty(PropertiesOptions.EMAIL_FROM, "pdpemailtest@gmail.com");
            properties.setProperty(PropertiesOptions.EMAIL_TO, "mahmud.salomov.salomov@gmail.com");

//set the outputs temp dir
//            properties.setProperty(PropertiesOptions.TEMP_DIR, new File("external").getPath());
            properties.setProperty(PropertiesOptions.TEMP_DIR, new File(backupPath).getPath());

            PostgresqlExportService postgresqlExportService = new PostgresqlExportService(properties);
            postgresqlExportService.export();
            logger.info("Backup completed");
            return converter.apiSuccess200();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Backup failed");
            logger.error(e.toString());
            return converter.apiError409("Error");
        }
        //required properties for exporting of db


    }

}
