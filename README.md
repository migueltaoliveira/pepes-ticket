# pepes-ticket
What the hack?

Pepes Ticket is a queue optimisation solution to be used in supermarket queues.

The formulated solution tries to give an adequate response to the challenge:

<b>"When I'm at a supermarket trying to be in 2 queues at the same time but once they call me in a booth, I'm also called for the other."</b>
 
To do that, we envisioned the situation in which a frequent customer can rely both on paper as well as on virtual tickets, provided by an Android application. The user, contrarily to what tends to happen nowadays, can select multiple services’ tickets, so that there’s no need to wait an indefinite amount of time in each of them.
 
To prevent the problem of being called simultaneously by two different services and having to chose between them, this software:

* Offers a guided multi-phased approach - in which the user is advised to follow the best path to complete the desired requests. 
 * The best path tries to give the customer a fair amount of time to reach the next stop. 
* Whenever a customer has several stops in different services (wants to buy different products, such as meat, fish, bread, etc.), once a new spot is reached, the other ones are blocked from calling the corresponding ticket.
* Time saving to an user to make the requests and get the intended products. After the request is complete in a specific location, the best path is calculated based on the number of tickets in queue in each service.
* Shows centralised information so that in both the application and the supermarkets’ monitors, the tickets’ actual counting is accurate.
 
For demonstration purposes, the solution offers not only the Android application which relies on a custom server, but also an hardware-based integration that simulates the interaction of the customer with a tickets’ machine and also emulates the screens distributed throughout the supermarket.

## architecture

There are 4 components in total:
* Android front-end app (<b>android_app</b> folder)
* Hardware-based front-end (<b>hardware_ticket</b> folder)
* Basic web dashboard for each service
* Java backend (<b>backend</b> folder)

![architecture diagram](https://preview.ibb.co/d516oF/pepe_diagram.jpg?style=centerme)

Each have its own folder in this repo.
