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
Para esta entrega se pedia implementar un controlador para el juego, además de una entidad Tactician que representaría a cada jugador dentro del juego. A continuación veremos lo principal a implementar en cada uno, y la solución que se dio a estas.

## Tactician

- Debe ser capaz de realizar todas las acciones de la Entrega 1 y acciones del codigo base
- Debe conocer las unidades que posee, y el mapa
- Puede mover a todas sus unidades, pero solo una vez a cada una dentro del turno
- Capacidad de ver los datos de sus unidades

## Controller

- Debe mantener los tacticians, mapa del juego y player actual
- Cualquier cambio en el estado del juego debe ser notificado al controlador para que decida que hacer
- Terminar turno y pasar al siguiente jugador
- Si un heroe es derrotado en el turno de otro jugador que no sea el dueño, pierde la partida y se retira del juego junto a sus unidades
- Si el heroe muere en el turno de su dueño, termina el turno y se retira al jugador
- Se puede tener más de un heroe, pero se pierde si alguno es derrotado
- Turnos aleatorios en cada ronda, el último no puede ser el primero en el siguiente turno
- Al crearse debe crear los jugadores, un mapa aleatorio e iniciar y asignar unidades
- Manera sencilla de crear unidades e items, y que se puedan asociar
- No puede haber dos unidades en la misma casilla, si alguna es derrotada se debe retirar

## Maneras de ganar

- El resto de los jugadores se han retirado
- Se alcanza la máxima cantidad de rondas. En este caso gana el jugador con mayor cantidad de unidades, si son iguales, empatan
