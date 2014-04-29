package com.yummynoodlebar.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.yummynoodlebar.persistence.domain.MenuItem;

/*
 * CrudRepository is part of the Spring Data repository hierarchy. 
 * It acts as both a marker interface and it adds several key methods to provide us with a living, 
 * breathing implementation of a repository, with almost zero code
 */
public interface MenuItemRepository extends CrudRepository<MenuItem, String>, AnalyseIngredients
{
    public List<MenuItem> findByIngredientsNameIn(String... name);

}