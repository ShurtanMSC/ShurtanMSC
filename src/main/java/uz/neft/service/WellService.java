package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.neft.dto.WellDto;
import uz.neft.entity.CollectionPoint;
import uz.neft.entity.Well;
import uz.neft.payload.ApiResponse;
import uz.neft.repository.CollectionPointRepository;
import uz.neft.repository.WellRepository;
import uz.neft.utils.Converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WellService {

    private final WellRepository wellRepository;
    private final Converter converter;
    private final CollectionPointRepository collectionPointRepository;

    @Autowired
    public WellService(WellRepository wellRepository, Converter converter, CollectionPointRepository collectionPointRepository) {
        this.wellRepository = wellRepository;
        this.converter = converter;
        this.collectionPointRepository = collectionPointRepository;
    }

    public ApiResponse save(WellDto dto) {
        try {
            if (dto.getId() == null) {
                Well well = new Well();
                Optional<CollectionPoint> byIdCollectionPoint = collectionPointRepository.findById(dto.getCollectionPointId());
                if (byIdCollectionPoint.isPresent()) {
                    well.setNumber(dto.getNumber());
                    well.setCollectionPoint(byIdCollectionPoint.get());
                    Well save = wellRepository.save(well);
                    WellDto wellDto = converter.wellToWellDto(save);
                    return converter.apiSuccess("Well saved", wellDto);
                }
                return converter.apiError("Collection point not found");
            }
            return converter.apiError("id shouldn't be sent");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error creating well");
        }
    }

    public ApiResponse edit(WellDto dto) {
        try {
            if (dto.getId() == null) return converter.apiError("Id null");

            Well editWell;
            Optional<Well> byId = wellRepository.findById(dto.getId());
            if (byId.isPresent()) {
                Optional<CollectionPoint> byIdCollectionPoint = collectionPointRepository.findById(dto.getCollectionPointId());
                if (byIdCollectionPoint.isPresent()) {
                    editWell = byId.get();
                    editWell.setNumber(dto.getNumber());
                    editWell.setCollectionPoint(byIdCollectionPoint.get());
                    Well editedWell = wellRepository.save(editWell);
                    WellDto wellDto = converter.wellToWellDto(editedWell);
                    return converter.apiSuccess("Well Edited", wellDto);
                }
                return converter.apiError("Collection point not found");
            }
            return converter.apiError("Well not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error editing well");
        }
    }

    public ApiResponse delete(Integer id) {
        try {
            if (id != null) {
                Optional<Well> byId = wellRepository.findById(id);
                if (byId.isPresent()) {
                    wellRepository.deleteById(id);
                    return converter.apiSuccess("Well deleted");
                }
                return converter.apiError("Well not found");
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in deleting well", e);
        }

    }

    public ApiResponse findAll() {
        try {
            List<Well> all = wellRepository.findAll();
            List<WellDto> collect = all.stream().map(converter::wellToWellDto).collect(Collectors.toList());
            return converter.apiSuccess(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in fetching all wells", e);
        }
    }

    public ApiResponse findById(Integer id) {
        try {
            if (id != null) {
                Optional<Well> byId = wellRepository.findById(id);
                if (byId.isPresent()) {
                    WellDto wellDto = converter.wellToWellDto(byId.get());
                    return converter.apiSuccess(wellDto);
                }
                return converter.apiError("Well not found");
            }
            return converter.apiError("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError("Error in finding well", e);
        }

    }
}
