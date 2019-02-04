
// todo: APUNTES PRACTICA:
    -> 1r Cargar el fichero con Rooms Workers. (Coleccion)
    -> 2n Especificaciones
        -> Estado Habitaciones.
            -> CLEAN: Puede ser asignado a un cliente.
            -> UNCLEAN: No puede ser asignada hasta que se limpie.
            -> BROKEN: Debe ser reparada antes de poderla asignar.
            -> RESEERVED: Esta reservada hasta que los clientes salgan.
        -> Servicio en las habitaciones:
            1. TV.
            2. Balcon.
            3. Camadoble.
            4. Jacuzzi.
            5. Minibar.
            6. Telefono.
            7. Satelite.
            8. Suite.
        -> Habilidades de los Trabajores:
            1. Mantenimiento.
            2. Limpieza.
            3. Piscina.
            4. Piscina.
            5. Spa.
            6. Bar
            7. Comida.
            8. Lavanderia.
        -> Money:
            precio estandar 1000$
                debera gestionares para generar mas ingresos...

// todo: COMANDOS:
        -> Respuesta se muestra azul.
        -> Informacion se muestra magenta.
        -> Mensajes error se muestra rojo.
    -> ROOM:
        - añadir habitacion al hotel.
        3 digitos. separado por espacios.
            numero habitacion.
            personas en habitacion: MAX el que quieras
            servicios incluidos: Separado por comas.
         // PERO GUARDAMOS TAMBIENE ESTADO DE ROOM.
    -> WORKER:
        -> añadir trabajadores al hotel.
        3 digitos separado por espacios.
            -> identificados por DNI -> (Coleccion Map)
            -> Nombre
            -> Habilidades del trabajador: Separado por comas.
            // PERO GUARDAMOS EL ROOM DONDE TRABAJA Y SI NO ESTA TRABAJANDO ES NULL!!!
    -> RESERVATION:
        -> añadir una nueva reserva al hotel.
        se añadira un Customer nuevo que es el cliente.
        3 digitos separado por espacios.
            -> DNI.
            -> Numero de personas que son.
            -> Requisitos / servicios de la habitacion.
        Cuando se hace la reserva se tendra que buscar una habitacion que cumpla todos los requisitos.
        y se debera asginar la habitacion que mas se acerque a los requisitos del cliente
            -> Nª personas y nª de servicios.
    -> HOTEL:
        Mostar los estados de la habitacion.
            -> DNI del cliente si esta ocupada.
            -> Lista trabajadores y que si estan en un cuarto lo muestre.
    -> PROBLEM:
        informa de un problema en alguna de las rooms.
            -> Se busca el cuarto y se pone en estado BROKEN.
            -> Si esta en BROKEN se debera mover a los clientes a otra ROOM con las mismas carac si puede ser.
            O quitarlos del HOTEL si no hay rooms disponible para ellos.
    -> REQUEST:
        peticion de ROOM para un servicio. Se buscara un WORKER disponible y se les asignara a la room.
        Un WORKER por peticion. Si WORKER no puede hacer el servicio se guardara.
        Sirve para si hay algun BROKEN asginar un WORKER para arreglar o limpiar.
    -> FINISH:
        -> Cuando ya no quedan peticiones de una ROOM.
        Quedaran liberados los WORKERS de la ROOM y la ROOM estara en CLEAN.
    -> LEAVE:
        Informa cuando unos CUSTOMER se van de la ROOM
        La habitacion quedara UNCLEAN para futuras reservas antes se tiene que limpiar.
        Tambien se marca con los WORKERS.
        Tambien marca si los CUSTOMERS pagan siempre que tengas las peticiones en FINISH de su ROOM.
        De lo contrario si los REQUEST no estan todos cumplidos el HOTEL perdera la mitad de este valor.
            -> EL HOTEL PAGA LA MITAD DE LO QUE TIENE QUE RECIBIR Y NO RECIBE NADA!!.
    -> MONEY:
        informa dinero HOTEL.
        EN caso de HOTEL sin MONEY da mensaje corresponiende y finalizar la APP.
     -> EXIT:
          SALIR DE LA APP.


// todo: COLLECTIONS:
    -> Almacenar que CUSTOMER esta en cada ROOM.
    -> Almacenar que WORKER esta en cada ROOM.
    -> Almacenar todas las ROOMS.
    -> Almacenar todos los WORKERS.
    -> Almacenar todos los CUSTOMERS.
    -> Almacenar de cada CUSTOMER/ROOM/WORKER sus colecciones de atributos.