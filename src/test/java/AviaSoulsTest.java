import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AviaSoulsTest {

    @Test
    void shouldCompareTicketsByPrice() {
        Ticket ticket1 = new Ticket("Moscow", "SPb", 1000, 1000, 1200);
        Ticket ticket2 = new Ticket("Moscow", "SPb", 2000, 1100, 1300);

        assertEquals(-1, ticket1.compareTo(ticket2));
        assertEquals(1, ticket2.compareTo(ticket1));
        assertEquals(0, ticket1.compareTo(ticket1));
    }

    @Test
    void shouldSearchAndSortByPrice() {
        AviaSouls manager = new AviaSouls();
        Ticket cheap = new Ticket("Moscow", "SPb", 1000, 1000, 1200);
        Ticket expensive = new Ticket("Moscow", "SPb", 2000, 1100, 1300);
        manager.add(expensive);
        manager.add(cheap);

        Ticket[] found = manager.search("Moscow", "SPb");

        assertEquals(2, found.length);
        assertEquals(1000, found[0].getPrice());
        assertEquals(2000, found[1].getPrice());
    }

    @Test
    void shouldCompareByFlightTime() {
        Ticket fast = new Ticket("Moscow", "SPb", 1500, 1000, 1100); // 1 час
        Ticket slow = new Ticket("Moscow", "SPb", 1500, 1000, 1300); // 3 часа
        TicketTimeComparator comparator = new TicketTimeComparator();

        assertEquals(-1, comparator.compare(fast, slow));
        assertEquals(1, comparator.compare(slow, fast));
        assertEquals(0, comparator.compare(fast, fast));
    }

    @Test
    void shouldSearchAndSortByFlightTime() {
        AviaSouls manager = new AviaSouls();
        Ticket fast = new Ticket("Moscow", "SPb", 1500, 1000, 1100); // 1 час
        Ticket slow = new Ticket("Moscow", "SPb", 1500, 1000, 1300); // 3 часа
        manager.add(slow);
        manager.add(fast);

        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket[] found = manager.searchAndSortBy("Moscow", "SPb", comparator);

        assertEquals(2, found.length);
        assertEquals(100, found[0].getTimeTo() - found[0].getTimeFrom());
        assertEquals(300, found[1].getTimeTo() - found[1].getTimeFrom());
    }

    @Test
    void shouldReturnEmptyArrayWhenNoTicketsFound() {
        AviaSouls manager = new AviaSouls();
        Ticket ticket = new Ticket("Moscow", "Kazan", 1000, 1000, 1200);
        manager.add(ticket);

        Ticket[] found = manager.search("Moscow", "SPb");
        assertEquals(0, found.length);

        found = manager.searchAndSortBy("Moscow", "SPb", new TicketTimeComparator());
        assertEquals(0, found.length);
    }

    @Test
    void shouldHandleMultipleDestinationsCorrectly() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("Moscow", "SPb", 1000, 1000, 1200));
        manager.add(new Ticket("Moscow", "Kazan", 800, 900, 1100));
        manager.add(new Ticket("Moscow", "SPb", 1200, 1100, 1400));

        Ticket[] spbTickets = manager.search("Moscow", "SPb");
        assertEquals(2, spbTickets.length);
        assertEquals(1000, spbTickets[0].getPrice());
        assertEquals(1200, spbTickets[1].getPrice());
    }
}
