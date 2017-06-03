import manager.MarketManager;

/**
 * Created by migueloliveira on 03/06/17.
 */
public class NextService
{
    public static void main(String[] args)
    {
        GenerateRandomTickets.main(null);

        MarketManager marketManager = MarketManager.getInstance();
        marketManager.getNextTicket("TALHO");
    }
}
