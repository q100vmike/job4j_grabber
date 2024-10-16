package ru.job4j.grabber;

import ru.job4j.model.Post;

import java.util.List;

public interface Store {
    void save(Post post);

    List<Post> getAll();

    Post findById(int id);

    void close() throws Exception;
}