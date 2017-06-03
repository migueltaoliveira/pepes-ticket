import manager.MarketManager;

import java.util.Arrays;

/**
 * Created by migueloliveira on 03/06/17.
 */
public class GenerateRandomTickets
{
    public static void main(String [] args)
    {
        MarketManager marketManager = MarketManager.getInstance();

        marketManager.generateGroupTicket("pepe", Arrays.asList("CHARCUTARIA","TALHO"));
        marketManager.generateGroupTicket("JPP", Arrays.asList("TALHO","PADARIA"));
        marketManager.generateGroupTicket("JPP", Arrays.asList("TALHO","CHARCUTARIA"));
    }
}
