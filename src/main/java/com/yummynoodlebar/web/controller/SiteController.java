package com.yummynoodlebar.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yummynoodlebar.core.services.MenuService;
import com.yummynoodlebar.events.menu.AllMenuItemsEvent;
import com.yummynoodlebar.events.menu.MenuItemDetails;
import com.yummynoodlebar.events.menu.RequestAllMenuItemsEvent;
import com.yummynoodlebar.web.domain.Basket;
import com.yummynoodlebar.web.domain.MenuItem;

@Controller
@RequestMapping("/")
public class SiteController
{

    private static final Logger LOG = LoggerFactory.getLogger(SiteController.class);

    @Autowired
    private MenuService menuService;

    /*
     * only a single SiteController exists in the system, however multiple
     * Basket instances exist and can all be accessed via this auto injected
     * proxy
     */
    @Autowired
    private Basket basket;

    /*
     * a Model instance is obtained from Spring MVC and populated with all the
     * current MenuItems
     * 
     * the returned string "/home" is a reference to a view
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getCurrentMenu(Model model)
    {
        LOG.debug("Yummy MenuItemDetails to home view");
        model.addAttribute("menuItems", getMenuItems(menuService.requestAllMenuItems(new RequestAllMenuItemsEvent())));
        return "/home";
    }

    private List<MenuItem> getMenuItems(AllMenuItemsEvent requestAllMenuItems)
    {
        List<MenuItem> menuDetails = new ArrayList<MenuItem>();

        for (MenuItemDetails menuItemDetails : requestAllMenuItems.getMenuItemDetails())
        {
            menuDetails.add(MenuItem.fromMenuDetails(menuItemDetails));
        }

        return menuDetails;
    }

    /*
     * need to put the Basket into the model for the view to be able to read
     * from. This method takes the auto injected Basket and annotates it so that
     * it is automatically merged into the Model
     */
    @ModelAttribute("basket")
    private Basket getBasket()
    {
        return basket;
    }
}
