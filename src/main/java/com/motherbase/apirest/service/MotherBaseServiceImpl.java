package com.motherbase.apirest.service;

import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.repository.MotherBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MotherBaseServiceImpl implements MotherBaseService {
    @Autowired
    private MotherBaseRepository motherBaseRepository;

    public MotherBaseServiceImpl() {
    }

    @Override
    @Transactional
    public MotherBase create(MotherBase motherBase) {
        return motherBaseRepository.save(motherBase);
    }
}
