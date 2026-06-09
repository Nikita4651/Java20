import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AviaSoulsTest {

    @Test
    void test1() {
        Ticket ticket1 = new Ticket("Moscow", "SPb", 1000, 1000, 1200);
        Ticket ticket2 = new Ticket("Moscow", "SPb", 2000, 1100, 1300);

        assertEquals(-1, ticket1.compareTo(ticket2));
        assertEquals(1, ticket2.compareTo(ticket1));
        assertEquals(0, ticket1.compareTo(ticket1));
    }

    @Test
    void test2() {
        AviaSouls manager = new AviaSouls();
        Ticket cheap = new Ticket("Moscow", "SPb", 1000, 1000, 1200);
        Ticket expensive = new Ticket("Moscow", "SPb", 2000, 1100, 1300);
        manager.add(expensive);
        manager.add(cheap);

        Ticket[] found = manager.search("Moscow", "SPb");

        Ticket[] expected = new Ticket[]{
                new Ticket("Moscow", "SPb", 1000, 1000, 1200),
                new Ticket("Moscow", "SPb", 2000, 1100, 1300)
        };
        assertArrayEquals(expected, found);
    }

    @Test
    void test3() {
        Ticket fast = new Ticket("Moscow", "SPb", 1500, 1000, 1100); // длительность 100
        Ticket slow = new Ticket("Moscow", "SPb", 1500, 1000, 1300); // длительность 300
        TicketTimeComparator comparator = new TicketTimeComparator();

        assertEquals(-1, comparator.compare(fast, slow));
        assertEquals(1, comparator.compare(slow, fast));
        assertEquals(0, comparator.compare(fast, fast));
    }

    @Test
    void test4() {
        AviaSouls manager = new AviaSouls();
        Ticket fast = new Ticket("Moscow", "SPb", 1500, 1000, 1100); // длительность 100
        Ticket slow = new Ticket("Moscow", "SPb", 1500, 1000, 1300); // длительность 300
        manager.add(slow);
        manager.add(fast);

        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket[] found = manager.searchAndSortBy("Moscow", "SPb", comparator);

        Ticket[] expected = new Ticket[]{
                new Ticket("Moscow", "SPb", 1500, 1000, 1100),
                new Ticket("Moscow", "SPb", 1500, 1000, 1300)
        };
        assertArrayEquals(expected, found);
    }

    @Test
    void test5() {
        AviaSouls manager = new AviaSouls();
        Ticket ticket = new Ticket("Moscow", "Kazan", 1000, 1000, 1200);
        manager.add(ticket);

        Ticket[] found = manager.search("Moscow", "SPb");
        Ticket[] expectedEmpty = new Ticket[0];
        assertArrayEquals(expectedEmpty, found);

        found = manager.searchAndSortBy("Moscow", "SPb", new TicketTimeComparator());
        assertArrayEquals(expectedEmpty, found);
    }

    @Test
    void test6() {
        AviaSouls manager = new AviaSouls();
        Ticket t1 = new Ticket("Moscow", "SPb", 1000, 1000, 1200);
        Ticket t2 = new Ticket("Moscow", "Kazan", 800, 900, 1100);
        Ticket t3 = new Ticket("Moscow", "SPb", 1200, 1100, 1400);
        manager.add(t1);
        manager.add(t2);
        manager.add(t3);

        Ticket[] spbTickets = manager.search("Moscow", "SPb");

        Ticket[] expected = new Ticket[]{
                new Ticket("Moscow", "SPb", 1000, 1000, 1200),
                new Ticket("Moscow", "SPb", 1200, 1100, 1400)
        };
        assertArrayEquals(expected, spbTickets);
    }

}
