package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.UppgDto;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.Uppg;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.UppgRepository;
import uz.neft.utils.Converter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UppgService {




    private final UppgRepository uppgRepository;
    private final Converter converter;
    private final MiningSystemRepository miningSystemRepository;

    @Autowired
    public UppgService(UppgRepository uppgRepository, Converter converter, MiningSystemRepository miningSystemRepository) {
        this.uppgRepository = uppgRepository;
        this.converter = converter;
        this.miningSystemRepository = miningSystemRepository;
    }

    public static void main(String[] args) {
        test();
    }




    public static void test(){
        String temp="";
        Connection connection=null;
        Statement statement=null;
        Statement statement2=null;
        Statement statement3=null; ResultSet resultSet=null;
        ResultSet resultSet2=null;
        String url="jdbc:sqlserver://192.168.10.20:1433;database=UPPG";
        String userDB="UPPGReader", pass="97F88FA06BB691C96D3B46CC3252452369F4ACEB5E076CFACF4B7BF4B5370A5A43B57702334D3C31";
        Date date=new Date(); SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection= DriverManager.getConnection(url,userDB,pass);
            statement=connection.createStatement();
            resultSet=statement.executeQuery("SELECT [ID]" +
                    "     ,[Name]" +
                    "     ,[FullName]" +
                    "     ,[Description]" +
                    "     ,[Val]" +
                    "     ,[Quality]" +
                    "     ,[Time_Stamp]" +
                    "     ,[ScaleMin]" +
                    "     ,[ScaleMax]" +
                    "FROM dbo.Data");

            if (resultSet.next()){
                System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
                System.out.println(resultSet.getString(4));
                System.out.println(resultSet.getString(5));
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public ResponseEntity<?> save(UppgDto dto) {
        try {
            if (dto.getId() != null) return converter.apiError400("id shouldn't be sent");
            Uppg uppg = new Uppg();
            Optional<MiningSystem> byIdMining = miningSystemRepository.findById(dto.getMiningSystemId());
            if (byIdMining.isPresent()) {
                uppg.setName(dto.getName());
                uppg.setMiningSystem(byIdMining.get());
                Uppg save = uppgRepository.save(uppg);
                UppgDto uppgDto = converter.uppgToUppgDto(save);
                return converter.apiSuccess201("Uppg saved", uppgDto);
            }
            return converter.apiError404("Mining system not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error creating uppg");
        }
    }

    public ResponseEntity<?> edit(UppgDto dto) {
        try {
            if (dto.getId() == null) return converter.apiError400("Id null");

            Uppg editUppg;
            Optional<Uppg> byId = uppgRepository.findById(dto.getId());
            if (byId.isPresent()) {
                Optional<MiningSystem> byIdMining = miningSystemRepository.findById(dto.getMiningSystemId());
                if (byIdMining.isPresent()) {
                    editUppg = byId.get();
                    editUppg.setName(dto.getName());
                    editUppg.setMiningSystem(byIdMining.get());
                    Uppg editedUppg = uppgRepository.save(editUppg);
                    return converter.apiSuccess200("Edited Mining System", editedUppg);
                }
                return converter.apiError404("Mining system not found");
            }
            return converter.apiError404("Uppg not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error edit uppg");
        }
    }

    public ResponseEntity<?> delete(Integer id) {
        try {
            if (id != null) {
                Optional<Uppg> byId = uppgRepository.findById(id);
                if (byId.isPresent()) {
                    uppgRepository.deleteById(id);
                    return converter.apiSuccess200("Uppg deleted");
                } else {
                    return converter.apiError404("Mining system not found");
                }
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in deleting uppg", e);
        }
    }



    public ResponseEntity<?> findAll() {
        try {
            List<Uppg> all = uppgRepository.findAll();
            List<UppgDto> collect = all.stream().map(converter::uppgToUppgDto).collect(Collectors.toList());

            return converter.apiSuccess201(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching all uppgs", e);
        }

    }

    public ResponseEntity<?> findById(Integer id) {
        try {
            if (id != null) {
                Optional<Uppg> byId = uppgRepository.findById(id);
                if (byId.isPresent()) {
                    UppgDto uppgDto = converter.uppgToUppgDto(byId.get());
                    return converter.apiSuccess200(uppgDto);
                } else {
                    return converter.apiError404("Uppg not found");
                }
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in finding uppg", e);
        }

    }
}
