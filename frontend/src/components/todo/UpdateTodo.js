import React, { useEffect, useState } from 'react';
import axios from 'axios';
import moment from 'moment';
import { useNavigate } from 'react-router-dom';

function UpdateTodo({ isAuthenticated, match }) {
  const [title, setTitle] = useState('');
  const [targetDate, setTargetDate] = useState('');
  const [message, setMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/');
    }
  }, [isAuthenticated, navigate]);

  function timeout(delay) {
    return new Promise((res) => setTimeout(res, delay));
  }

  const onSubmit = async (e) => {
    e.preventDefault();

    try {
      const todoId = match?.params?.id; // Check if match and params are defined
      if (todoId) {
        await axios.put(
          `http://localhost:8080/api/todo/${todoId}`,
          { title, targetDate }
        );
      }
    } catch (error) {
      setMessage('');
      if (error.response) {
        setErrorMessage(error.response.data.message);
      } else {
        setErrorMessage('Error: something happened');
      }
      return;
    }

    setErrorMessage('');
    setMessage('Todo successfully updated');
    await timeout(1000);
    navigate('/todo');
  };

  useEffect(() => {
    const loadData = async () => {
      try {
        const todoId = match?.params?.id; // Check if match and params are defined
        if (todoId) {
          const response = await axios.get(`http://localhost:8080/api/todo/${todoId}`);
          setTitle(response.data.title);
          setTargetDate(moment(response.data.targetDate).format('YYYY-MM-DD'));
        }
      } catch (error) {
        setMessage('');
        if (error.response) {
          setErrorMessage(error.response.data.message);
        } else {
          setErrorMessage('Error: something happened');
        }
      }
    };

    loadData();
  }, [match?.params?.id]); // Include match?.params?.id in the dependency array

  useEffect(() => {
    setMessage('');
  }, [title, targetDate]);

  const showMessage = () => {
    if (message === '') {
      return <div></div>;
    }
    return (
      <div className="alert alert-success" role="alert">
        {message}
      </div>
    );
  };

  const showErrorMessage = () => {
    if (errorMessage === '') {
      return <div></div>;
    }

    return (
      <div className="alert alert-danger" role="alert">
        {errorMessage}
      </div>
    );
  };

  return (
    <div className="container">
      <form onSubmit={onSubmit}>
        <h1>Update Todo</h1>
        <div className="form-group">
          <label>Title</label>
          <input
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="form-control"
          />
        </div>
        <div className="form-group">
          <label>Target Date</label>
          <input
            value={targetDate}
            type="date"
            onChange={(e) => setTargetDate(e.target.value)}
            className="form-control"
          />
        </div>
        <button className="btn btn-primary">Update Todo</button>
      </form>
      {showMessage()}
      {showErrorMessage()}
    </div>
  );
}

export default UpdateTodo;
