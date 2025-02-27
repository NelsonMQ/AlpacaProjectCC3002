<h1 style="background-color: black;">AlpacaProjectCC3002</h1>

# Primera Entrega

<p>Para la realización de lo pedido, primero se revisó si el código implementaba una buena metodología de diseño. Al revisarlo se encontró que el metodo equipItem usaba "instance of" para equipar los objetos. Es por esto que este método tuvo que ser modificado, para lo cual se implementó a través de Double Dispatch, debido a que cada unidad podía equiparse un tipo diferente de objeto. Además se agregó una restricción al equipar un item, ahora la unidad debe tener el item en su lista para ser añadido.</p>

<p>En cuanto a los requerimientos pedidos por nuestro cliente, se pedía implementar un sistema de Combate, una forma de intercambiar items entre unidades y además, añadir un nuevo tipo de unidad con tres nuevos items.</p>

Para el sistema de Ataques, las armas fueron divididas en 2 tipos: items de ataque y items de no ataque. Esto se tradujo en dos nuevas clases abstractas y dos nuevas interfaces. Se decidió hacer clases abstractas, ya que las armas tenían algunos métodos en común que nombraremos a continuación. Estos métodos fueron agregados para quitarle vida a alguna unidad, tenemos tres opciones de daño: daño normal, que corresponde al daño del arma; daño fuerte, que corresponde a 1.5 veces el daño del arma; y daño débil, que corresponde al daño del arma menos 20. Cabe destacar que el daño débil solo aplica cuando sea un valor mayor a 0, ya que no es posible "curar" a una unidad enemiga. Además se construyó un metodó abstracto para atacar, esto debido a que cada item llamaba a un método diferente para hacer daño a la unidad dependiendo de que item tiene equipado. También se añadió un método para curar con el Staff.

Para las debilidades y fortalezas de los items se usó Double Dispatch creando varios métodos de rebicir daño dependiendo del arma con que se es atacado. Esto dado que cada unidad recibe un daño diferente dependiendo de que arma lo está atacando y que arma tiene equipada. Dependiendo de las fortalezas, este método usa los daños normal, fuertes o débiles. Con lo anterior ya tenemos la parte de ataque en los items.

Para la parte de las unidades, se creó un método para saber si una unidad puede atacar a otra. Para esto el método contempla la distancia entre ambas unidades, el rango del arma equipada y la vida de las unidades. También fue añadido un método de atacar, en donde si la unidad puede atacar a su enemigo, entonces el item ataca al enemigo. Es ahí en donde entra los métodos de item definidos anteriormente. Ya al tener lo anterior, para el combate solamente se ataca a una unidad enemiga, y luego si la unidad enemiga puede atacar, entonces lo hace. Cabe destacar que para el Combate se modificó la variable de VidaActual de las unidades, ya que esta estaba como una constante. Además se añadio un método para cambiar la vida actual.

En cuanto al requisito del intercambio de items, se añadieron métodos para añadir y eliminar items de la lista de la unidad. Con esto, se creó un método donde si una unidad poseía el item para intercambiar y la unidad a recibir tenía espacio para llevarlo, además de tener distancia 1 entre ellas, entonces se añadia el item a la nueva unidad y se eliminaba de la anterior.

Para el último requisito, se añadieron nuevas clases al programa, una para la nueva unidad, y tres para los diferentes items nuevos. La nueva clase de la unidad extendío la clase Abstracta de unidades, y las clases de los nuevos items, extendieron la clase abstracta de items de ataque. Como estos items posían debilidades y fortalezas, entonces se tuvieron que añadir nuevos métodos a los demás items, para que así recibieran el daño correspondiente. También se añadió un método para equipar los items a las unidades correspondientes. Lo anterior no fue difícil, dado que estaba implementado con Double Dispatch.

Para la ejecución del programa basta correr los test ubicados en la carpeta "test".

# Segunda Entrega
Para esta entrega se pedia implementar un controlador para el juego, además de una entidad Tactician que representaría a cada jugador dentro del juego. A continuación veremos lo principal a implementar en cada uno, y la solución que se dio a estas. En cuanto a los errores de la Tarea 1, no hubieron, pero de todas formas se agregó un método usarItem, el cual llama a atacar o curar dependiendo el caso, esto para facilitar la implementación en la Tarea 2 y no tener que "saber" cual usar.

## Tactician

- Debe ser capaz de realizar todas las acciones de la Entrega 1 y acciones del codigo base: para lograrlo, se implimentaron métodos en Tactician que permiten ejecutar las acciones correspondientes (mover unidad, usar item, dar item). Tactician, a través de estos métodos, accede al modelo del juego.

- Debe conocer las unidades que posee, y el mapa: En Tactician, se agregaron variables de instancia, una de tipo Field que guarda el mapa, y una lista de unidades, en donde se encuentran las unidades que posee el jugador.

- Puede mover a todas sus unidades, pero solo una vez a cada una dentro del turno: Para lograrlo, se agrego una variable de instancia a Tactician, que corresponde a una lista de Unidades, en donde cada vez que se mueve a una unidad, primero se revisa que este en la lista, y luego al moverla, es eliminada de esta. Cabe destacar que esta lista se rellena al principio de cada turno.

- Capacidad de ver los datos de sus unidades: para esto se agregaron métodos "getters" que devuelven cada uno de los datos de la unidad seleccionada. Por ejemplo: getSelectedUnitCurrentHP, getSelectedUnitMaxHP, etc.

- Además posee métodos para asignar unidades e items de una manera sencilla, esto se logró usando el Patrón de Diseño Factory, en donde tenemos una interfaz IUnitFactory que es implementada por cada tipo de unidades, es decir, por cada TipoDeUnidadFactory. Por ejemplo, AlpacaFactory implementa la interfaz IUnitFactory. Lo mismo se hizo para crear los items, una interfaz IEquipableItemFactory que es implementada por TipoDeItemfactory. La interfaz pide que en cada una de las clases de implemente un método create(), el cual retorna el tipo de unidad/item que se requiere con parametros ya definidos.

- También, posee una variable de instancia con el número de Heroes al comenzar la partida, y también, una lista con los Heroes, esto para saber cuando un heroe muere y poder avisarlo al controlador.

## Controller

- Debe mantener los tacticians, mapa del juego y player actual: se agregaron variables de instancias para lograrlo. Se agregó una Lista de Tacticians que corresponden a los jugadores, un Field que corresponde al mapa del juego, un Tactician que corresponde al jugador actual.

- Cualquier cambio en el estado del juego debe ser notificado al controlador para que decida que hacer: para esta funcionalidad, se usó el Observer Pattern, el cual, avisaría al controller si el Tactician provocaba algún cambio en el juego. Se uso este patrón de diseño, debido a que es trabajo del controlador mantener este estado, y además, el Tactician no puede hacer llamadas al Controller. Se implementaron 4 Observers, uno para cada acción. Estas acciones corresponden a: Remover Unidad, Remover Tactician, Actualizar el Mapa y Chequear los heroes de los jugadores. Para esto, se crearon cuatro clases diferentes,ya que así no hay necesidad de preguntar que acción sucedió. Estas implementan PropertyChangeListener, en donde se posee una instancia del controlador. Además, cada una de las clases, implementa el método PropertyChange, que realiza el llamado a métodos del controller. Cabe destacar que cada uno de estas clases llama a diferentes métodos en controller. Para continuar, estos Observers son agregados a los Tactician (el cual posee cuatro instancias de PropertyChangeSupport), y avisan al controller cuando alguna de las acciones ,anteriormente nombradas, son requeridas. Por ejemplo, si una unidad muere en combate, el Tactician le avisa al controller para que la elimine del juego. O que también chequee si los heroes siguen vivos despues de algún combate.

- Terminar turno y pasar al siguiente jugador: En este método, el controller revisa la ronda que se está jugando, si la ronda es la última, y el jugador es el último en jugar de aquella ronda, entonces se llama a un método que termina el juego, y genera los ganadores dadas las condiciones de victoria en el juego. Otro caso, es si el jugador en el último de su ronda, pero la ronda no es la última, en este caso se crea una nueva lista de Orden para la siguiente ronda, siguiendo la restricción de que el último jugador de una ronda, no puede ser el primero de la siguiente. El último caso es cuando es una ronda cualquiera, en donde se elimina el jugador actual de la lista de Ronda, se cambia el jugador actual por el siguiente en la lista y se reinician las unidades para mover al jugador. Cabe destacar que en todas las rondas se hace lo que mencionamos anteriormente.  

- Si un heroe es derrotado en el turno de otro jugador que no sea el dueño, pierde la partida y se retira del juego junto a sus unidades: para esto se creó un método checkHeroes(), el cual chequea si a algún jugador se le murió un Hero. Si es el hero de otro jugador que no es el player actual, entonces elimina al tactician junto con sus unidades. 

- Si el heroe muere en el turno de su dueño, termina el turno y se retira al jugador: el mismo método mencionado en el punto anterior es usado, pero si el Hero derrotado es del jugador actual, entonces termina el turno a través de EndTurn y elimina al Tactician usando el método removeTactician().

- Se puede tener más de un heroe, pero se pierde si alguno es derrotado: en este caso el método checkHeroes(), revisa la cantidad total de Heroes que el player poseía y lo compara con el tamaño de la Lista de heroes, si no son iguales, entonces significa que murio alguno, y se procede a realizar los casos que se hablaron en los puntos anteriores.

- Turnos aleatorios en cada ronda, el último no puede ser el primero en el siguiente turno: Para esto, se crearon dos instancias en el controller, que corresponden a dos Listas de Tacticians, en una de ellas guardamos los jugadores de la ronda actual, y en la otra la de la ronda anterior. Así, al volver a cambiar el orden de los tacticians, se verifica que el primero sea diferente al de la Lista de la ronda anterior.

- Al crearse debe crear los jugadores, un mapa aleatorio e iniciar y asignar unidades: Al crearse el controlador, crea los jugadores a través de un método, el cual los crea a través de un for y los añade a la Lista de Tacticians del controlador. Para la asignación de unidades, no es necesario que lo haga todavía, basta con métodos sencillos, que se hablarán en el siguiente punto. Para la creación del mapa, se uso Factory Pattern, en donde los Locations del mapa son asignados al azar, es decir, un mapa podría tener el Location(0,0), pero otro podría no tenerlo. Además las conecciones entre los nodos del mapa, también son aleatorios. Para lograr la aleatoriedad de los Location, se crea una matriz cuadrada con 0's y 1's de manera al azar, para luego convertir los 1's en la posición (x,y) en un Location(x,y). Para finalizar se crea el mapa con addCells pasandole todas las locations creadas. Cabe destacar que si el mapa no era conexo, se procederá a construir una matriz nueva. 

- Manera sencilla de crear unidades e items, y que se puedan asociar: Para esto se crearon métodos para añadir unidades al jugador actual (uno para tipo de Unidad), en donde se llama a un método del Tactician, que es quien los crea. Aquel método en el tactician, lo que hace es crear un Factory para aquel tipo de unidad ,y retorna la unidad que crea el Factory. Además, este método recibe una posición para poder poner la unidad en el mapa. Para los items es muy parecido, existen métodos para cada item en el Tactician que son llamados por el Controller, y que le añaden un item a la unidad seleccionada. Al igual que las unidades, los items se crean a a través de un Factory.  

- No puede haber dos unidades en la misma casilla, si alguna es derrotada se debe retirar: para lograr que haya solo una unidad en la misma casilla, se procedio a verificar que la casilla estuviera vacia antes de ejecutar algún método, en caso de que no estaba vacia, el método no hace nada. Para retirar una unidad, se creó un método removeUnit(), el cual elimina la unidad del Tactician, además de eliminar sus referencias en el mapa (eliminandola de la casilla en que se encontraba).

## Maneras de ganar

- El resto de los jugadores se han retirado: Para esto en el método removeTactician(), si al eliminar un Tactician luego queda solo uno, entonces llamaba al método EndGame, el cual cambia los ganadores en el controller dependiendo del caso en que se encuentre.

- Se alcanza la máxima cantidad de rondas. En este caso gana el jugador con mayor cantidad de unidades, si son iguales, empatan: En el método EndTurn, si es el último turno del último round, entones se llama al método EndGame(), el cual contara las unidades de cada jugador y cambiara la lista de los ganadores. 

## Ejecución del programa
Para ejecutar el programa, basta correr los test ubicados en la carpeta "test".
