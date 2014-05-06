package data.yummynoodlebar.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import data.yummynoodlebar.persistence.domain.MenuItem;

/*
 * CrudRepository is part of the Spring Data repository hierarchy. 
 * It acts as both a marker interface and it adds several key methods to provide us with a living, 
 * breathing implementation of a repository, with almost zero code
 * 
 * The AnalyseIngredients interface indicates to Spring Data that,
 * it should look for an implementation of that interface for extension
 */
public interface MenuItemRepository extends CrudRepository<MenuItem, String>, AnalyseIngredients
{
    /*
     * The implementation of search via "ingredients -> name", Spring Data does
     * it for you!
     * 
     * Spring Data has generated an implementation of this method, doing what
     * you wanted. Spring Data relies on a rich vocabulary that you can express
     * using method names in order to trigger auto-generation of these search
     * methods on your repositories
     */
    public List<MenuItem> findByIngredientsNameIn(String... name);

}
