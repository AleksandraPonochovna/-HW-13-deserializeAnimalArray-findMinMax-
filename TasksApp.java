package com.company;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TasksApp {

    public static void main(String[] args) {
        //Task with comparator
        Comparator comparator = (o1, o2) -> (((Integer) o1 < (Integer) o2) ? -1 : ((o1 == o2) ? 0 : 1));
        Stream stream = Stream.empty();
        BiConsumer minMaxConsumer = (s1, s2) -> System.out.println(s1 + " " + s2);
        findMinMax(stream, comparator, minMaxConsumer);
        //Task with Serializable for TESTS
        Animal[] animals = {new Animal("Name"), new Animal("Name"), new Animal("Name"),
                new Animal("Name"), new Animal("Name"), new Animal("Name")};
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeInt(animals.length);
            for (Animal value : animals) {
                objectOutputStream.writeObject(value);
            }
            for (Animal animal : deserializeAnimalArray(byteArrayOutputStream.toByteArray())) {
                System.out.println(animal.toString());
            }
        } catch (IOException | NullPointerException | ClassCastException ex) {
            throw new IllegalArgumentException("Some exception in main.");
        }
    }

    public static <T> void findMinMax(
            Stream<? extends T> stream,
            Comparator<? super T> order,
            BiConsumer<? super T, ? super T> minMaxConsumer) {
        List<T> list = stream.collect(Collectors.toList());
        if (list.isEmpty()) {
            minMaxConsumer.accept(null, null);
        } else {
            minMaxConsumer.accept(Collections.min(list, order), Collections.max(list, order));
        }
    }

    public static Animal[] deserializeAnimalArray(byte[] data) {
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
             ObjectInputStream objInputStream = new ObjectInputStream(byteStream)) {
            int size = objInputStream.readInt();
            Animal[] animals = new Animal[size];
            for (int i = 0; i < animals.length; i++) {
                animals[i] = (Animal) objInputStream.readObject();
            }
            return animals;
        } catch (IOException | NullPointerException | ClassNotFoundException | ClassCastException ex) {
            throw new IllegalArgumentException("It's exception in deserializeAnimalArray ().");
        }
    }
}

