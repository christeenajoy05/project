import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Signin.css';

function Signin({ isAuthenticated, setIsAuthenticated }) {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  function timeout(delay) {
    return new Promise((res) => setTimeout(res, delay));
  }

  const onSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/api/auth/signin', { username, password });
      sessionStorage.setItem('token', response.data.token);
      sessionStorage.setItem('name', response.data.username);
      setIsAuthenticated(true);
    } catch (error) {
      setMessage('');
      if (error.response) {
        setErrorMessage(error.response.data.message);
      } else {
        setErrorMessage('Error: something happened');
      }
      setIsAuthenticated(false);
      return;
    }

    setUsername('');
    setPassword('');
    setErrorMessage('');
    setMessage('Sign in successful');
    await timeout(1000);
    navigate('/');
  };

  useEffect(() => {
    setMessage('');
  }, [username, password]);

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
      <section className="vh-100">
        <div className="container-fluid">
          <div className="row">
            <div className="col-sm-6 text-black">
              <div className="px-5 ms-xl-4">
                <i className="fas fa-crow fa-2x me-3 pt-5 mt-xl-4" style={{ color: '#709085' }}></i>
                <span className="h1 fw-bold mb-0">ToDoApplication</span>
                <img src="images/clockicon.png" alt="Icon description" className="icon"/>
              </div>
  
              <div className="d-flex align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-5 pt-xl-0 mt-xl-n5">
                <form onSubmit={onSubmit} style={{ width: '23rem' }}>
                  <h3 className="fw-normal mb-3 pb-3" style={{ letterSpacing: '1px' }}>Log in</h3>
  
                  <div className="form-outline mb-4">
                    <input
                      type="text"
                      id="form2Example18"
                      className="form-control form-control-lg"
                      value={username}
                      onChange={(e) => setUsername(e.target.value)}
                    />
                    <label className="form-label" htmlFor="form2Example18">Username</label>
                  </div>
  
                  <div className="form-outline mb-4">
                    <input
                      type="password"
                      id="form2Example28"
                      className="form-control form-control-lg"
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                    />
                    <label className="form-label" htmlFor="form2Example28">Password</label>
                  </div>
  
                  <div className="pt-1 mb-4">
                    <button className="btn btn-info btn-lg btn-block" type="submit">Login</button>
                  </div>
                  {showMessage()}
                  {showErrorMessage()}
  
                  <p></p>
                  
                </form>
              </div>
            </div>
  
            <div className="col-sm-6 px-0 d-none d-sm-block">
              <img
                src="/images/clock.jpg"
                alt=""
                className="w-100 vh-100"
                style={{ objectFit: 'cover', objectPosition: 'left' }}
              />
            </div>
          </div>
        </div>
      </section>
  );
}

export default Signin;
