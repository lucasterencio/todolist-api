package com.lucasterencio.todolist.controller;

import com.lucasterencio.todolist.model.Task;
import com.lucasterencio.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/criar")
    public ResponseEntity<?> create(@RequestBody Task task){
        return taskService.createTask(task);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar(){
        return taskService.listTask();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarById(@PathVariable Long id){
        return taskService.listarId(id);
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editar(@RequestBody Task task){
        return taskService.editTask(task);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        return taskService.deleteTaskById(id);
    }

}
