REQUIREMENTS:
-Java7 installed

HOW TO RUN:
-Run the class SimulatorViewer

DESCRIPTION
This simulation is based on a savannah landscape and the food chain and ecosystem that results from
it. It includes two carnivore predators (lions and tigers) that both compete for the same kinds of prey
(zebras and gazelles); the prey are herbivores that only eat plants, both competing for the same food
source. The different species are represented by different colours on the simulator: Tigers: Orange,
Lions: Yellow, Gazelles: Blue, Zebras: Black, Plants: Green (empty space in the savannah is
displayed as white squares).
Each of our five species have and display different characteristics and behaviours, namely they each
have different maximum living ages, breeding ages, maximum litter sizes as well as breeding
tendencies. 
All animals breed,only predators hunt other prays if they are hungry and are close to them, whilst only prey eat vegetation.
When the simulation is run, the savannah is populated using differing creation probabilities to initially
create sensible numbers of the different species and all of which act according their unique
behaviours. Predators will look to hunt for prey animals as a food source; if they are unable to find
food for a sustained amount of time, their hunger level is incremented to the point where they can
eventually starve and die. Once they reach the required age of breeding, they will also look for
potential mates to breed with, thus creating new live young that grow and display similar behaviours
to their parents. In order to breed, an animal must be of the required breeding age, as well as being of
a different gender to its mate and meeting in adjacent positions in the savannah (for predators and
prey alike). Prey will also look for food sources, however this only includes vegetation as opposed to
other live animals. If prey animals are eaten by predators then they are killed and removed from the
simulation (any dead animal is removed from the simulation, whether eaten, killed by disease or dead
from old age).
Prey only eat vegetation, therefore will eat plants, simulating the eating of plant life in the real world;
once the plant has been eaten completely i.e. its growth level is down to zero, it is dead and removed
from the simulation similar to when the animals die.
Other than solely looking for food or mates, the animals also freely move around the savannah to
wherever there is space. This is particularly evident when the animals are temporarily full, i.e. they
have eaten enough food for the time being, and simply move around the field until they feel hungry
enough to begin looking for food again. This can be seen as some prey animals may be able to move
around some predators without being eaten, provided the predators are currently full. This simulates
that the interactions between predator and prey are not limited hunting and being hunted, but prey can
also flee from predators whilst the predators are not hunting them.
This simulation also oscillates between day and night, which allows for more characteristics of the
species to be demonstrated. A lion for example will sleep during the night and do all of its acting in
the day. Whilst a gazelle will do the opposite, sleeping during the day and moving throughout the
night; obviously while asleep, an animal is unable to move, find food or breed.

Plant Pollination.
As in real life plants do not breed in the same manner that animals do, but rather by pollination. As many savannah plants pollinate via wind pollination, a fairly random process, we created a similar mechanism, whereby a child plant can be born in a random
empty space in the savannah, and then further grow in its new position. This involves scanning the
entire field to find a free location and creating the new child plant in that position, thus simulating the
pollination of the plants, procreating new plants in the process. Of course, plants can only pollinate so
much at one go, which is however not constant, thus we chose a random number up to a maximum
amount that will pollinate at various steps of the simulation.

Weather.
I created different weather conditions in the simulation including temperature, rain, fog as well as
the time of day. Depending on the weather conditions certain species may exhibit different
behaviours. For example, plants will only grow while it rains and in extreme heat (40 degrees or
above) predators will sleep and be unable to move, hunt or breed due to the high temperatures. The
weather conditions are randomly selected at each step as the steps oscillate between day and night,
creating many days and nights with differing conditions leading to species acting a reacting
differently to the different weather conditions. These are also used to further authentically replicate
real life behavioural characteristics of animals; where predators refrain from hunting in high
temperatures to prevent overheating. To implement this in the simulation I created another separate
Weather class to house all the details of the weather, which is then called at each step in the simulator.

Disease.
It has been created various types of disease in our simulation too, one of these being a food-based infection that is started with diseased vegetation. Once a prey animal eats the diseased plant, the animal will also become infected with the disease, likewise if a predator eats a diseased prey animal, they will
also become infected. With this particular disease, the infected animal will have a 5-day incubation
period where they will not be able to move, eat or breed, after which they will recover and return to
full health. A second, rarer disease is more critical than the first, however, like the first it begins with infected plants and does spread through eating infected food, however, the more critical disease can
also be transferred through breeding, whereby both mating animals will become infected as well as all
of their offspring. This disease results in a 10-day incubation period before the animal dies; much
more destructive than the first one as it directly causes death much more quickly and spreads and
infects much more violently also. At its inception a small, random number of plants may be created already infected with the disease by using a low probability, however, as plants pollinate, new plants may also be infected. The critical disease also occurs randomly in infected plants, however at a much lower probability.

BUGS
Even males can give birth to new animals.
When dieseas is passed to a predator cause he ate a prey, the predator is gonna get the same amount of "daysOfIllness".
In the very rare case where there are no species left there might appear a fullStackException caused by an infinite recursive call.





