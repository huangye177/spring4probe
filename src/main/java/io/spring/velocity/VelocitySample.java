package io.spring.velocity;

import java.io.StringWriter;
import java.util.*;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * Created by yehuang on 6/9/14.
 */
public class VelocitySample {

    public static void main(String[] args) throws Exception
    {
        System.out.println( System.getProperty("user.dir"));

        /*  first, get and initialize an engine  */
        VelocityEngine ve = new VelocityEngine();
        ve.init();

        /*  next, get the Template  */
        Template t = ve.getTemplate( "src/main/resources/helloworld.vm" );

        /* create our list of maps  */
        ArrayList list = new ArrayList();

        Map map = new HashMap();
        map.put("name", "horse");
        map.put("price", "00.00");
        list.add( map );

        map = new HashMap();
        map.put("name", "dog");
        map.put("price", "9.99");
        list.add( map );

        map = new HashMap();
        map.put("name", "bear");
        map.put("price", ".99");
        list.add( map );

        /*  create a context and add data */
        VelocityContext context = new VelocityContext();

        context.put("name", "World");
        context.put("petList", list);

        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge( context, writer );

        /* show the World */
        System.out.println( writer.toString() );
    }
}
