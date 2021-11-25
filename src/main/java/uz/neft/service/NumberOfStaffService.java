package uz.neft.service;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import uz.neft.dto.StaffDto;
import uz.neft.dto.action.ObjectWithActionsDto;
import uz.neft.entity.MiningSystem;
import uz.neft.entity.NumberOfStaff;
import uz.neft.repository.MiningSystemRepository;
import uz.neft.repository.NumberOfStaffRepository;
import uz.neft.utils.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NumberOfStaffService {

    private final NumberOfStaffRepository numberOfStaffRepository;
    private final MiningSystemRepository miningSystemRepository;
    private final Converter converter;

    public NumberOfStaffService(NumberOfStaffRepository numberOfStaffRepository, MiningSystemRepository miningSystemRepository, Converter converter) {
        this.numberOfStaffRepository = numberOfStaffRepository;
        this.miningSystemRepository = miningSystemRepository;
        this.converter = converter;
    }

    public HttpEntity<?> add(StaffDto dto) {
        try {
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(dto.getMiningSystemId());
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            NumberOfStaff numberOfStaff = dto.toEntity(miningSystem.get());
            numberOfStaff=numberOfStaffRepository.save(numberOfStaff);
            return converter.apiSuccess201(numberOfStaff.toDto());
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public HttpEntity<?> one(int id) {
        try {
            Optional<MiningSystem> miningSystem = miningSystemRepository.findById(id);
            if (!miningSystem.isPresent()) return converter.apiError404("Mining system not found");
            Optional<NumberOfStaff> first = numberOfStaffRepository.findFirstByMiningSystemOrderByCreatedAtDesc(miningSystem.get());
            if (!first.isPresent()) return converter.apiSuccess200(new ObjectWithActionsDto(miningSystem.get().toDto(),null));
            return converter.apiSuccess200(first.get().toWithActionsDto());
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }
    }

    public HttpEntity<?> all() {
        try {
            List<ObjectWithActionsDto> dtoList = getAll();
            return converter.apiSuccess200(dtoList);
        }catch (Exception e){
            e.printStackTrace();
            return converter.apiError409();
        }

    }


    public List<ObjectWithActionsDto> getAll(){
        List<MiningSystem> miningSystemList = miningSystemRepository.findAll();
        List<ObjectWithActionsDto> dtoList=new ArrayList<>();
        miningSystemList.forEach(m->{
            Optional<NumberOfStaff> first = numberOfStaffRepository.findFirstByMiningSystemOrderByCreatedAtDesc(m);
            if (first.isPresent()){
                dtoList.add(first.get().toWithActionsDto());
            }else {
                dtoList.add(new ObjectWithActionsDto(m.toDto(),null));
            }
        });
        return dtoList;
    }
}
