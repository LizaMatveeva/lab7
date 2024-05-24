package ifmo.lab.server.builders;

import ifmo.lab.server.models.Person;

public class PersonBuilder {

    public static Person build(){
        Person person = new Person();
        person.setName(StringFieldBuilder.build("Введите имя владельца: "));
        person.setWeight(FloatBuilder.build("Введите вес владельца:"));
        person.setHairColor(ColorBuilder.build());
        person.setNationality(CountryBuilder.build());
        person.setLocation(LocationBuilder.build());
        return person;
    }
}
