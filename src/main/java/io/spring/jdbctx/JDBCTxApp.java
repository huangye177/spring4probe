package io.spring.jdbctx;

import org.junit.Assert;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JDBCTxApp
{
	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		
        ctx.register(JDBCTxConfig.class);
        ctx.refresh();

        BookingService bookingService = ctx.getBean(BookingService.class);
        bookingService.book("Alice", "Bob", "Carol");
        Assert.assertEquals("First booking should work with no problem",
                3, bookingService.findAllBookings().size());

        try {
            bookingService.book("Chris", "Samuel");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        Assert.assertEquals("'Samuel' should have triggered a rollback",
                3, bookingService.findAllBookings().size());

        try {
            bookingService.book("Buddy", null);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        Assert.assertEquals("'null' should have triggered a rollback",
                3, bookingService.findAllBookings().size());

    }
}
