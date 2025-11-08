package sun.vn.employee_management.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilityService {

    public Long generateEmployeeId(){
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
