package io.sumitnegi.careeros.service;

import io.sumitnegi.careeros.model.Todo;
import io.sumitnegi.careeros.model.UpdateTodo;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TodoService {

    private Long idCounter = 1L;
    private Map<Long,Todo> todos = new HashMap<>();

    public Todo createTodo(Todo todo){
        todo.setId(idCounter++);
        todos.put(todo.getId(),todo);
        return todo;
    }

    public Todo deleteTodo(Long id){
        Todo removeTodo = todos.get(id);
        todos.remove(id);
        return removeTodo;
    }

    public List<Todo> getAllTodos(){
        return new ArrayList<>(todos.values());
    }

    public Todo updateTodo(Long id,UpdateTodo updateTodo){
        Todo updatedTodo = new Todo(id,updateTodo.getTitle(),updateTodo.isStatus());
        todos.put(id,updatedTodo);
        return updatedTodo;
    }





}
