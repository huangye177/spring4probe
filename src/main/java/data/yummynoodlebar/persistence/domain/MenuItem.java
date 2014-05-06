package data.yummynoodlebar.persistence.domain;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import data.yummynoodlebar.events.menu.MenuItemDetails;

@Document(collection = "menu")
public class MenuItem
{
    @Id
    private String id;

    @Field("itemName")
    @Indexed
    private String name;

    private String description;

    private Set<Ingredient> ingredients;

    private BigDecimal cost;

    private int minutesToPrepare;

    public String getDescription()
    {
        return description;
    }

    public Set<Ingredient> getIngredients()
    {
        return ingredients;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setIngredients(Set<Ingredient> ingredients)
    {
        this.ingredients = ingredients;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public BigDecimal getCost()
    {
        return cost;
    }

    public void setCost(BigDecimal cost)
    {
        this.cost = cost;
    }

    public int getMinutesToPrepare()
    {
        return minutesToPrepare;
    }

    public void setMinutesToPrepare(int minutesToPrepare)
    {
        this.minutesToPrepare = minutesToPrepare;
    }

    public MenuItemDetails toStatusDetails()
    {
        return new MenuItemDetails(id, name, cost, minutesToPrepare);
    }

    public static MenuItem fromStatusDetails(MenuItemDetails orderStatusDetails)
    {
        MenuItem item = new MenuItem();

        item.cost = orderStatusDetails.getCost();
        item.id = orderStatusDetails.getId();
        item.minutesToPrepare = orderStatusDetails.getMinutesToPrepare();
        item.name = orderStatusDetails.getName();

        return item;
    }
}