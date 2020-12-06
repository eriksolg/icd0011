package storage;

import common.Person;
import common.Post;

public class Main {

    public static void main(String[] args) {

        var storage = new Storage();

        storage.store("person", new Person("Alice", "Smith"));

        storage.store("post", new Post("Some title", "Some text ..."));

        // read and store value from storage
    }

}
