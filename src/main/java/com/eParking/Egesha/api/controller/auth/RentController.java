package com.eParking.Egesha.api.controller.auth;

import com.eParking.Egesha.api.dto.RentBody;
import com.eParking.Egesha.model.Rent;
import com.eParking.Egesha.model.dao.RentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("RENT")
public class RentController {

    @Autowired
    RentsRepository rentsRepository;

    @PostMapping("/rent")
    public Rent rentDetails (@RequestBody RentBody rentsBody) {
        Rent rentDetail = new Rent();
        rentDetail.setLocation(rentsBody.getLocation());
        rentDetail.setDates(rentsBody.getDates());
        rentDetail.setOperator(rentsBody.getOperator());
        rentDetail = rentsRepository.save(rentDetail);
        return rentsRepository.save (rentDetail);
    }
}
