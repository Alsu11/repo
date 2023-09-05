package ru.itis.services;

import ru.itis.dto.LeaveDto;
import ru.itis.dto.CarEntryDto;

public interface CarEntryService {
    CarEntryDto canCarGoOut(LeaveDto leave);
}
