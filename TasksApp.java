package com.company;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TasksApp<T> {

    public static void main(String[] args) {
        //Task with comparator
        Comparator comparator = (o1, o2) -> (((Integer) o1 < (Integer) o2) ? -1 : ((o1 == o2) ? 0 : 1));
        Stream stream = Stream.empty();
        BiConsumer minMaxConsumer = (s1, s2) -> System.out.println(s1 + " " + s2);
        findMinMax(stream, comparator, minMaxConsumer);
        //Task with Serializable
        byte[] data = {0, 1, 2, 3, 4, 56, 7, 8, 8, 8, 5};
        deserializeAnimalArray(data);
    }

    public static <T> void findMinMax(
            Stream<? extends T> stream,
            Comparator<? super T> order,
            BiConsumer<? super T, ? super T> minMaxConsumer) {
        List<T> list = stream.sorted(order).collect(Collectors.toList());
        if (list.isEmpty()) {
            minMaxConsumer.accept(null, null);
        } else {
            minMaxConsumer.accept(list.get(0), list.get(list.size() - 1));
        }
    }

    public static Animal[] deserializeAnimalArray(byte[] data) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(data))) {
            int size = ois.readInt();
            Animal[] animals = new Animal[size];
            for (Animal animal : animals) {
                animal = (Animal) ois.readObject();
            }
            return animals;
        } catch (IOException | NullPointerException | ClassNotFoundException | ClassCastException ex) {
            throw new IllegalArgumentException("It's exception.");
        }
    }
}
