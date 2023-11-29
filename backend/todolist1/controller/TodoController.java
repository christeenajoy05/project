package com.backend.todolist1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.todolist1.errorhandler.CustomException;
import com.backend.todolist1.model.Todo;
import com.backend.todolist1.service.TodoService;
import com.backend.todolist1.controller.CountResponse;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@ApiResponses(value = {
        @ApiResponse(code=400, message = "Bad Request", response = CustomException.class),
        @ApiResponse(code=401, message = "Unauthorized", response = CustomException.class),
        @ApiResponse(code=403, message = "Forbidden", response = CustomException.class),
        @ApiResponse(code=404, message = "Not Found", response = CustomException.class)
})
public class TodoController {
    @Autowired
    private TodoService todoService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @RequestMapping(value = "/api/todo", method = RequestMethod.POST)
    public ResponseEntity<Todo> create(HttpSession session,@Valid @RequestBody TodoCreateRequest todoCreateRequest) {
    	String username = (String) session.getAttribute("username");
    	// No need to check for Principal, as we are not using authentication
        return new ResponseEntity<>(todoService.create(todoCreateRequest, username), HttpStatus.CREATED);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/api/todo", method = RequestMethod.GET)
    public ResponseEntity<List<Todo>> readAll(HttpSession session,@RequestParam(required = false) String isCompleted) {
    	String username = (String) session.getAttribute("username");

        if (isCompleted != null) {
            return new ResponseEntity<>(todoService.readAllByIsCompleted(username,isCompleted), HttpStatus.OK);
        }
        return new ResponseEntity<>(todoService.readAll(username), HttpStatus.OK);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/api/todo/count", method = RequestMethod.GET)
    public ResponseEntity<CountResponse> countAll(HttpSession session,@RequestParam(required = false) String isCompleted) {
    	String username = (String) session.getAttribute("username");
        if (isCompleted != null) {
            return new ResponseEntity<>(todoService.countAllByIsCompleted(username,isCompleted), HttpStatus.OK);
        }
        return new ResponseEntity<>(todoService.countAll(username), HttpStatus.OK);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/api/todo/{pageNumber}/{pageSize}", method = RequestMethod.GET)
    public ResponseEntity<List<Todo>> readAllPageable(
            @PathVariable String pageNumber,
            @PathVariable String pageSize,
            @RequestParam(required = false) String isCompleted,
            HttpSession session) {
    	String username = (String) session.getAttribute("username");
        if (isCompleted != null) {
            return new ResponseEntity<>(todoService.readAllByIsCompletedPageable(username,isCompleted, pageNumber, pageSize), HttpStatus.OK);
        }
        return new ResponseEntity<>(todoService.readAllPageable(username,pageNumber, pageSize), HttpStatus.OK);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/api/todo/{id}", method = RequestMethod.GET)
    public ResponseEntity<Todo> read(HttpSession session, @PathVariable long id) {
        String username = (String) session.getAttribute("username");
        return new ResponseEntity<>(todoService.readById(id, username), HttpStatus.OK);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/api/todo/{id}/markcomplete", method = RequestMethod.PUT)
    public ResponseEntity<Todo> markComplete(HttpSession session, @PathVariable long id) {
        String username = (String) session.getAttribute("username");
        return new ResponseEntity<>(todoService.markCompleteById(id, username), HttpStatus.OK);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(value = "/api/todo/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Todo> update(HttpSession session, @PathVariable long id, @Valid @RequestBody TodoUpdateRequest todoUpdateRequest) {
        String username = (String) session.getAttribute("username");
        return new ResponseEntity<>(todoService.updateById(id, todoUpdateRequest, username), HttpStatus.OK);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/api/todo/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(HttpSession session, @PathVariable long id) {
        String username = (String) session.getAttribute("username");
        todoService.deleteById(id, username);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
