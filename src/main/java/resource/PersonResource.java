package resource;
import  entity.Person;
import io.vertx.ext.bridge.PermittedOptions;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @GET
    @Path("/list")
    public List<Person> listAll() {
        return Person.listAll();
    }

    @GET
    @Path("/search/{id}")
    public Person search(@PathParam("id") Long id) {
        return Person.findById(id);
    }

    @POST
    @Path("/add")
    @Transactional
    public  Response add(Person person) {
        if (person.id == null || person.id == 0) {
            person.persist();
           return Response.ok(person).build();
        } else {
            String errorMessage = String.format("Fehler bei add Person");
            return Response.status(Response.Status.CREATED)
                    .entity(errorMessage)
                    .build();
        }
    }

    @Path("/delete/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Person delete(@PathParam("id") Long id) {
        Person delPerson = this.search(id);
        System.out.println("DER ID : " + id + " PERSON  " + " Vorname :" + delPerson.getName() + "Name :" + delPerson.getFname() );
        boolean b = Person.deleteById(id);
        if (!b) {
            System.out.println("Die Person wurde NICHT storniert: " + b);
        } else {
            System.out.println("Die Person wurde storniert: " + b);
        }
        return delPerson;
    }


    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional // Asegura que haya una transacción activa
    public Response update(@PathParam("id") Long id, Person personUpdate) {
        System.out.println("Received update for ID: " + id);
        System.out.println("Person update data: " + personUpdate);

        Person person = Person.findById(id);

        if (person != null) {
            System.out.println("Existing Person: " + person.fname);
            System.out.println(person.id + " " + person.name + " " + person.fname + " " + person.alt);
            System.out.println("NEUE PERSON: " + personUpdate.id + " " + personUpdate.name + " " + personUpdate.fname + " " + personUpdate.alt);


            person.setName(personUpdate.getName());
            person.setFname(personUpdate.getFname());
            person.setAlt(personUpdate.getAlt());


            //Person.getEntityManager().flush();
              person.persist();
            return Response.ok(person).build();
        } else {
            String errorMessage = String.format("Person mit ID %d nicht gefunden", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessage)
                    .build();
        }
    }


}
