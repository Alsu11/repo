package ru.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dto.LeaveDto;
import ru.itis.dao.CarEntryRepository;
import ru.itis.dto.CarEntryDto;
import ru.itis.exceptions.ErrorEntity;
import ru.itis.exceptions.NotFoundException;
import ru.itis.mappers.CarEntryMapper;
import ru.itis.models.CarEntry;
import ru.itis.services.CarEntryService;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class CarEntryServiceImpl implements CarEntryService {

    private final CarEntryRepository carEntryRepository;

    private final CarEntryMapper carEntryMapper;

    @Override
    public CarEntryDto canCarGoOut(LeaveDto leave) {
        CarEntry carEntry = carEntryRepository.findByCarNumber(leave.getCarNumber())
                .orElseThrow(() -> new NotFoundException(ErrorEntity.CAR_ARE_NOT_IN_THIS_PARKING));

        if(carEntry.getEndTime().compareTo(Instant.now()) <= 0) {
            return carEntryMapper.toResponse(carEntry);
        }

        return null;
    }

}
