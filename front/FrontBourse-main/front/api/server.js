const express = require("express");
const cors = require("cors");

const app = express();
const port = 8080; // API will run on http://localhost:8080

// Middleware
app.use(cors());
app.use(express.json());

// Mock Data
const bourseActions = [
    { id: "1", actionNom: "Action A", prix: 100.0, date: "2024-01-01 10:00:00" },
    { id: "2", actionNom: "Action B", prix: 200.0, date: "2024-01-02 11:00:00" },
];

const averageActions = [
    { id: "1", averagePrix: 150.0 },
    { id: "2", averagePrix: 175.0 },
];


 

  
 

// Routes
app.get("/api/bourse-actions", (req, res) => {
    res.json(bourseActions);
});

app.get("/api/average-actions", (req, res) => {
    res.json(averageActions);
});

// Start the server
app.listen(port, () => {
    console.log(`Server is running on http://localhost:${port}`);
});
