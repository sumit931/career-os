package io.careeros.careeros.controller;

import io.careeros.careeros.model.Todo;
import io.careeros.careeros.model.UpdateTodo;
import io.careeros.careeros.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    TodoService todoService = new TodoService();
    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo){
        return todoService.createTodo(todo);
    }

    @DeleteMapping("/{id}")
    public Todo deleteTodo(@PathVariable Long id){
        return todoService.deleteTodo(id);
    }

    @GetMapping
    public List<Todo> todos(){
        return todoService.getAllTodos();
    }

    @PatchMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id,@RequestBody UpdateTodo updateTodo){
        return todoService.updateTodo(id, updateTodo);
    }

    // this was interesting building todo list as checking how fluent i am with my tech skills and i feel more confident about them now
    // will keep building things for fun as coding is fun need to build many thing to proove my self as an potential engineer.
    // let see these thing like optional and many more.

}
