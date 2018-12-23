package com.motherbase.apirest.controller.responsecustom;

import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.resource.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class MotherBaseRessourcesResponse extends CustomerResponse {
    private Long id;
    private Map<Resource, Integer> resources;

    public MotherBaseRessourcesResponse(MotherBase motherBase) {
        this.id = motherBase.getId();
        this.resources = new HashMap<>();


    }

}
