import React from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import './App.css';
import {UserService} from "../../services/UserService";

function App() {
    const userService: UserService = new UserService();
    userService.getAllUser();
  return (
    <div className="App">
        Some text
    </div>
  );
}

export default App;
