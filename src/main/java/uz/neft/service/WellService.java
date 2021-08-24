package uz.neft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> save(WellDto dto) {
        try {
            if (dto.getId() == null) {

                Optional<CollectionPoint> byIdCollectionPoint = collectionPointRepository.findById(dto.getCollectionPointId());
                if (byIdCollectionPoint.isPresent()) {
                    Well well = Well
                            .builder()
                            .collectionPoint(byIdCollectionPoint.get())
                            .altitude(dto.getAltitude())
                            .commissioningDate(dto.getCommissioningDate())
                            .depth(dto.getDepth())
                            .drillingStartDate(dto.getDrillingStartDate())
                            .horizon(dto.getHorizon())
                            .number(dto.getNumber())
                            .x(dto.getX())
                            .y(dto.getY())
                            .build();
                    Well save = wellRepository.save(well);
                    WellDto wellDto = converter.wellToWellDto(save);
                    return converter.apiSuccess201("Well saved", wellDto);
                }
                return converter.apiError404("Collection point not found");
            }
            return converter.apiError400("id shouldn't be sent");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error creating well");
        }
    }

    public ResponseEntity<?> edit(WellDto dto) {
        try {
            if (dto.getId() == null) return converter.apiError400("Id null");

            Well editWell;
            Optional<Well> byId = wellRepository.findById(dto.getId());
            if (byId.isPresent()) {
                Optional<CollectionPoint> byIdCollectionPoint = collectionPointRepository.findById(dto.getCollectionPointId());
                if (byIdCollectionPoint.isPresent()) {
                    editWell = byId.get();
                    editWell.setNumber(dto.getNumber());
                    editWell.setCollectionPoint(byIdCollectionPoint.get());
                    editWell.setAltitude(dto.getAltitude());
                    editWell.setCommissioningDate(dto.getCommissioningDate());
                    editWell.setDepth(dto.getDepth());
                    editWell.setDrillingStartDate(dto.getDrillingStartDate());
                    editWell.setHorizon(dto.getHorizon());
                    editWell.setX(dto.getX());
                    editWell.setY(dto.getY());
                    Well editedWell = wellRepository.save(editWell);
                    WellDto wellDto = converter.wellToWellDto(editedWell);
                    return converter.apiSuccess200("Well Edited", wellDto);
                }
                return converter.apiError404("Collection point not found");
            }
            return converter.apiError404("Well not found");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error editing well");
        }
    }

    public ResponseEntity<?> delete(Integer id) {
        try {
            if (id != null) {
                Optional<Well> byId = wellRepository.findById(id);
                if (byId.isPresent()) {
                    wellRepository.deleteById(id);
                    return converter.apiSuccess200("Well deleted");
                }
                return converter.apiError404("Well not found");
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in deleting well", e);
        }

    }

    public ResponseEntity<?> findAll() {
        try {
            List<Well> all = wellRepository.findAll();
            List<WellDto> collect = all.stream().map(converter::wellToWellDto).collect(Collectors.toList());
            return converter.apiSuccess200(collect);
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in fetching all wells", e);
        }
    }

    public ResponseEntity<?> findById(Integer id) {
        try {
            if (id != null) {
                Optional<Well> byId = wellRepository.findById(id);
                if (byId.isPresent()) {
                    WellDto wellDto = converter.wellToWellDto(byId.get());
                    return converter.apiSuccess200(wellDto);
                }
                return converter.apiError404("Well not found");
            }
            return converter.apiError400("Id null");
        } catch (Exception e) {
            e.printStackTrace();
            return converter.apiError409("Error in finding well", e);
        }

    }
}
