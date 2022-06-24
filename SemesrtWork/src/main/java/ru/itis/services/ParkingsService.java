package ru.itis.services;

import ru.itis.dto.CarEntryDto;
import ru.itis.dto.EntryForm;

public interface ParkingsService {
    Integer getSlotsCount(String address);
    CarEntryDto parkCar(EntryForm entryForm);
}
