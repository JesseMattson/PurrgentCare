// src/PersonService.js
import axios from 'axios';

const API_PERSON_BASE_URL = 'http://localhost:8080/persons'; // Adjust this based on your API endpoint

export const getAllPersonsData = async () => {
    try {
        const response = await axios.get(`${API_PERSON_BASE_URL}/all`);
        return response.data;
    } catch (error) {
        console.error('Error fetching people data', error);
        throw error;
    }
};
