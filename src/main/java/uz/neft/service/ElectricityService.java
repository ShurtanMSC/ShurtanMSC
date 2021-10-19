package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.ElectricityDto;
import uz.neft.dto.action.ObjectWithActionsDto;
import uz.neft.entity.Electricity;
import uz.neft.entity.MiningSystem;
import uz.neft.repository.ElectricityRepository;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.utils.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ElectricityService {
    @Autowired
    private ElectricityRepository electricityRepository;
    @Autowired
    private Converter converter;
    @Autowired
    private MiningSystemRepository miningSystemRepository;

    public ResponseEntity<?> save(ElectricityDto dto){
        try {
            if (dto.getId()!=null) return converter.apiError400("id shouldn't be sent");
            if (dto.getMiningSystemId()==null) return converter.apiError400("Mining system id is null!");
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(dto.getMiningSystemId());
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found!");
            Electricity electricity=Electricity
                    .builder()
                    .hourly(dto.getHourly())
                    .miningSystem(miningSystem.get())
                    .build();
            return converter.apiSuccess201("Saved",electricity);
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public ResponseEntity<?> saveAll(List<ElectricityDto> dtoList){
        try {
            List<MiningSystem> miningSystemList=new ArrayList<>();
            for (ElectricityDto electricityDto : dtoList) {
                Optional<MiningSystem> miningSystem = miningSystemRepository.findById(electricityDto.getMiningSystemId());
                if (!miningSystem.isPresent()) return converter.apiError404("Mining system not foound");
                miningSystemList.add(miningSystem.get());
            }
            for (int i = 0; i <miningSystemList.size() ; i++) {
                Electricity electricity= Electricity
                        .builder()
                        .hourly(dtoList.get(i).getHourly())
                        .hourPerDay(24)
                        .dayPerWeek(7)
                        .dayPerMonth(30)
                        .dayPerYear(365)
                        .miningSystem(miningSystemList.get(i))
                        .build();
                electricityRepository.save(electricity);
            }
            return converter.apiSuccess201("Successfully created");
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public ResponseEntity<?> edit(ElectricityDto dto){
        try {
            if (dto.getId()==null) return converter.apiError400("Id is null!");
            Optional<Electricity> electricity = electricityRepository.findById(dto.getId());
            if (!electricity.isPresent()) return converter.apiError404("Electricity not found!");
            electricity.get().setHourly(dto.getHourly());
            Electricity edit = electricityRepository.save(electricity.get());
            return converter.apiSuccess200("Edited",edit);
//            if (dto.getMiningSystemId()==null) return converter.apiError400("Mining system id is null!");
//            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(dto.getMiningSystemId());

        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public ResponseEntity<?> getOne(Integer id){
        try {
            Optional<Electricity> electricity = electricityRepository.findById(id);
            if (!electricity.isPresent()) return converter.apiError404("Electricity not found");
            return converter.apiSuccess200(electricity.get());
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public ResponseEntity<?> getOneByMiningSystem(Integer id){
        try {
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found!");
            Optional<Electricity> electricity = electricityRepository.findFirstByMiningSystemOrderByCreatedAtDesc(miningSystem.get());
            if (!electricity.isPresent()) return converter.apiError404("Electricity not found");
            return converter.apiSuccess200(electricity.get());
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public ResponseEntity<?> allElectricity(){
        try {
            return converter.apiSuccess200("all",electricityRepository.findAll());
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public ResponseEntity<?> allFirstBy(){
        try {
            List<MiningSystem> miningSystemList = miningSystemRepository.findAll();
            List<Electricity> electricityList=new ArrayList<>();
            for (MiningSystem miningSystem : miningSystemList) {
                Optional<Electricity> electricity = electricityRepository.findFirstByMiningSystemOrderByCreatedAtDesc(miningSystem);
                if (electricity.isPresent()) electricityList.add(electricity.get());
                else electricityList.add(null);
            }
            List<ElectricityDto> dtoList=new ArrayList<>();
            for (Electricity electricity : electricityList) {
                if (electricity == null) {
                    dtoList.add(null);
                } else {
                    ElectricityDto dto = ElectricityDto
                            .builder()
                            .id(electricity.getId())
                            .miningSystemId(electricity.getMiningSystem().getId())
                            .hourly(electricity.getHourly())
                            .daily(electricity.getHourly()*electricity.getHourPerDay())
                            .monthly(electricity.getHourly()*electricity.getHourPerDay()*electricity.getDayPerMonth())
                            .yearly(electricity.getHourly()*electricity.getHourPerDay()*electricity.getDayPerYear())
                            .build();
                    dtoList.add(dto);
                }
            }
            return converter.apiSuccess200("all",dtoList);
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public ResponseEntity<?> delete(Integer id) {
        try {
            if (id==null) return converter.apiError400("Id is null");
            if (!electricityRepository.existsById(id)) return converter.apiError404("Electricity not found!");
            electricityRepository.deleteById(id);
            return converter.apiSuccess200("Deleted");
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }
}
