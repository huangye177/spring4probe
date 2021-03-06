package io.spring.velocity;

import java.io.StringWriter;
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

        /*  create a context and add data */
        VelocityContext context = new VelocityContext();
        context.put("name", "World");

        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge( context, writer );

        /* show the World */
        System.out.println( writer.toString() );
    }
}
