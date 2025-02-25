package com.triana.salesianos.dam.lagrada.query;

import com.triana.salesianos.dam.lagrada.model.User;
import com.triana.salesianos.dam.lagrada.util.SearchCriteria;
import org.springframework.stereotype.Component;

import java.util.List;

public class UserSpecificationBuilder
        extends GenericSpecificationBuilder<User>{
    public UserSpecificationBuilder(List<SearchCriteria> params) {
        super(params);
    }
}