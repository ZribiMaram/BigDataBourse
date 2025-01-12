import React, { useEffect, useState } from "react";
import axios from "axios";

const DataTable = () => {
    const [bourseActions, setBourseActions] = useState([]);
    const [averageActions, setAverageActions] = useState([]);

    // Fetch data from backend
    useEffect(() => {
        const fetchBourseActions = async () => {
            try {
                const response = await axios.get("http://localhost:8045/api/bourse/actions"); // Update with your backend API endpoint
                setBourseActions(response.data);
            } catch (error) {
                console.error("Error fetching Bourse Actions:", error);
            }
        };

      

        fetchBourseActions();
         
    }, []);

    return (
        <div style={{ padding: "20px" }}>
            {/* Bourse Actions Table */}
            <h2>Bourse Actions</h2>
            <table border="1" style={{ width: "100%", textAlign: "left", marginBottom: "20px" }}>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Action Name</th>
                        <th>Price</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    {bourseActions.map((action) => (
                        <tr key={action.id}>
                            <td>{action.id}</td>
                            <td>{action.actionNom}</td>
                            <td>{action.prix}</td>
                            <td>{new Date(action.timestamp).toLocaleString()}</td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {/* Average Actions Table */}
     
        </div>
    );
};

export default DataTable;
