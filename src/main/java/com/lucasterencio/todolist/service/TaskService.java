package com.lucasterencio.todolist.service;

import com.lucasterencio.todolist.model.Mensagem;
import com.lucasterencio.todolist.model.Task;
import com.lucasterencio.todolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private Mensagem mensagem;

    public ResponseEntity<?> listTask(){
        return new ResponseEntity<>(taskRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> createTask(Task task){
        if(task.getDescription().equals("")){
            mensagem.setMensagem("Descrição vazia!");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(this.taskRepository.save(task), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> selectTaskById(Long id){
        if(taskRepository.countById(id) == 0){
            mensagem.setMensagem("Id não encontrado");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(taskRepository.findById(id), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> editTask(Task task){
        if(taskRepository.countById(task.getId()) == 0){
            mensagem.setMensagem("O id informado não existe");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        } else if(task.getDescription().equals("")){
            mensagem.setMensagem("Descrição vazia!");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else{
            return new ResponseEntity<>(taskRepository.save(task), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> deleteTask(Task task){
        if(taskRepository.countById(task.getId()) == 0){
            mensagem.setMensagem("Id não existe");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        } else{

            taskRepository.deleteById(task.getId());

            mensagem.setMensagem("Deletado com sucesso!");
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }
    }
}
