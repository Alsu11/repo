package ru.itis.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.dao.CarEntryRepository;
import ru.itis.dao.ParkingsRepository;
import ru.itis.dto.CarEntryDto;
import ru.itis.dto.EntryForm;
import ru.itis.exceptions.*;
import ru.itis.mappers.CarEntryMapper;
import ru.itis.models.CarEntry;
import ru.itis.models.Parking;
import ru.itis.services.ParkingsService;

import javax.transaction.Transactional;
import java.rmi.AlreadyBoundException;
import java.time.Instant;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ParkingsServiceImpl implements ParkingsService {

    private final ParkingsRepository parkingsRepository;

    private final CarEntryRepository carEntryRepository;

    private final CarEntryMapper carEntryMapper;

    @Override
    public Integer getSlotsCount(String address) {
        Parking parking = findByAddress(address);
        return parking.getSlotsCount();
    }

    @Transactional
    @Override
    public CarEntryDto parkCar(EntryForm entryForm) {
        Optional<CarEntry> carEntryOptional = carEntryRepository.findByCarNumber(entryForm.getCarNumber());
        if (carEntryOptional.isPresent()) {
            throw new AlreadyExistsException(ErrorEntity.CAR_ALREADY_HERE);
        }

        Parking parking = findByAddress(entryForm.getAddress());

        if(parking.getSlotsCount() <= 0) {
            throw new NoPlaceException(ErrorEntity.NO_PLACE);
        } else {

            CarEntry carEntry = carEntryMapper.toRequest(entryForm);

            return updateCarEntry(carEntry, entryForm.getAmountOfHours(), updateParking(parking));
        }
    }

    private Parking updateParking(Parking parking) {
        Integer slots = parking.getSlotsCount() - 1;
        parking.setSlotsCount(slots);
        parking.setUpdatedAt(Instant.now());
        return parkingsRepository.save(parking);
    }

    private Parking findByAddress(String address) {
       return parkingsRepository.findByAddress(address).
                orElseThrow(() -> new IncorrectInput(ErrorEntity.ADDRESS_WRONG));
    }

    private CarEntryDto updateCarEntry(CarEntry carEntry, Integer amountOfHours, Parking parking) {
        carEntry.setCreatedAt(Instant.now());
        carEntry.setStartTime(Instant.now());
        carEntry.setEndTime(Instant.now().plusSeconds(amountOfHours * 60));
        carEntry.setParking(parking);
        return carEntryMapper
                .toResponse(carEntryRepository
                        .save(carEntry));
    }
}
