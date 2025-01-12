import React from "react";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import DataTable from "./components/DataTable"; // Update with the correct path to your DataTable component

const App = () => {
  return (
    <Router>
      <div style={{ padding: "20px" }}>
        {/* Navigation Links */}
        <nav>
          <Link to="/" style={{ marginRight: "10px" }}>Home</Link>
          <Link to="/datatable">DataTable</Link>
        </nav>

        {/* Routes */}
        <Routes>
          <Route path="/" element={<h1>Welcome to the App</h1>} />
          <Route path="/datatable" element={<DataTable />} />
        </Routes>
      </div>
    </Router>
  );
};

export default App;
