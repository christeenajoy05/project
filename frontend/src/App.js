import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './components/header/Header';
import About from './components/page/About';
import Todos from './components/todo/ViewTodo';
import AddTodo from './components/todo/AddTodo.js';
import UpdateTodo from './components/todo/UpdateTodo';
import Signin from './components/auth/Signin';
import Signup from './components/auth/Signup';
import Signout from './components/auth/Signout';
import Landing from './components/page/Landing';
import NotFound from './components/page/NotFound';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    if (sessionStorage.getItem('token') !== null) {
      setIsAuthenticated(true);
    }
  }, []);

  return (
    <Router>
      <div className="App">
        <Header isAuthenticated={isAuthenticated} setIsAuthenticated={setIsAuthenticated} />
        <div>
          <Routes>
            <Route
              path="/"
              element={<Landing isAuthenticated={isAuthenticated} setIsAuthenticated={setIsAuthenticated} />}
            />
            <Route
              path="/signin"
              element={<Signin isAuthenticated={isAuthenticated} setIsAuthenticated={setIsAuthenticated} />}
            />
            <Route
              path="/signup"
              element={<Signup isAuthenticated={isAuthenticated} setIsAuthenticated={setIsAuthenticated} />}
            />
            <Route
              path="/signout"
              element={<Signout isAuthenticated={isAuthenticated} setIsAuthenticated={setIsAuthenticated} />}
            />
            <Route
              path="/todo"
              element={<Todos isAuthenticated={isAuthenticated} setIsAuthenticated={setIsAuthenticated} />}
            />
            <Route
              path="/add"
              element={<AddTodo isAuthenticated={isAuthenticated} setIsAuthenticated={setIsAuthenticated} />}
            />
            <Route
              path="/update/:id"
              element={<UpdateTodo isAuthenticated={isAuthenticated} setIsAuthenticated={setIsAuthenticated} />}
            />
            <Route path="/about" element={<About />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
