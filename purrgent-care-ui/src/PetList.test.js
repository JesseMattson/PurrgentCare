import React from 'react';
import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import PetList from './PetList';
import { PET_BASE_URL } from './constants';

// Mock the fetch API
global.fetch = jest.fn();

const petsData = [
    { id: 1, name: 'Buddy', type: 'Dog', age: '5', gender: 'Male' },
    { id: 2, name: 'Mittens', type: 'Cat', age: '3', gender: 'Female' },
];

beforeEach(() => {
    fetch.mockImplementation((url, options) => {
        if (!options) {
            // GET request for fetching pets
            return Promise.resolve({
                json: () => Promise.resolve(petsData),
            });
        } else if (options.method === 'DELETE') {
            // DELETE request for removing a pet
            return Promise.resolve();
        }
    });
});

afterEach(() => {
    fetch.mockClear();
});

/* TODO: Fix broken test
test('renders PetList and displays pets', async () => {
    render(
        <MemoryRouter>
            <PetList />
        </MemoryRouter>
    );

    // Check that the loading indicator is shown initially
    expect(screen.getByText(/Loading.../i)).toBeInTheDocument();

    // Wait for the pets to be loaded and displayed
    await waitFor(() => {
        expect(screen.getByText(/Buddy/i)).toBeInTheDocument();
        expect(screen.getByText(/Mittens/i)).toBeInTheDocument();
    });

    // Check that the pet details are displayed correctly
    expect(screen.getByText(/Dog/i)).toBeInTheDocument();
    expect(screen.getByText(/Cat/i)).toBeInTheDocument();
    expect(screen.getByText(/5/i)).toBeInTheDocument();
    expect(screen.getByText(/3/i)).toBeInTheDocument();
    expect(screen.getByText(/Male/i)).toBeInTheDocument();
    expect(screen.getByText(/Female/i)).toBeInTheDocument();
});
*/

test('handles deletion of a pet correctly', async () => {
    render(
        <MemoryRouter>
            <PetList />
        </MemoryRouter>
    );

    // Wait for the pets to be loaded and displayed
    await waitFor(() => {
        expect(screen.getByText(/Buddy/i)).toBeInTheDocument();
        expect(screen.getByText(/Mittens/i)).toBeInTheDocument();
    });

    // Delete the first pet
    fireEvent.click(screen.getAllByText(/Delete/i)[0]);

    // Wait for the pet to be removed from the list
    await waitFor(() => {
        expect(screen.queryByText(/Buddy/i)).not.toBeInTheDocument();
        expect(screen.getByText(/Mittens/i)).toBeInTheDocument();
    });

    // Ensure that the DELETE fetch call was made with the correct URL
    expect(fetch).toHaveBeenCalledWith(`${PET_BASE_URL}/1`, expect.objectContaining({
        method: 'DELETE',
    }));
});

test('renders empty state when no pets are available', async () => {
    // Mock fetch to return an empty array
    fetch.mockImplementationOnce(() =>
        Promise.resolve({
            json: () => Promise.resolve([]),
        })
    );

    render(
        <MemoryRouter>
            <PetList />
        </MemoryRouter>
    );

    // Wait for the component to finish loading
    await waitFor(() => {
        expect(screen.queryByText(/Loading.../i)).not.toBeInTheDocument();
    });

    // Check that the table body is empty
    expect(screen.queryByText(/Buddy/i)).not.toBeInTheDocument();
    expect(screen.queryByText(/Mittens/i)).not.toBeInTheDocument();
    expect(screen.getByText(/Pets/i)).toBeInTheDocument();
});

test('renders loading state correctly', () => {
    render(
        <MemoryRouter>
            <PetList />
        </MemoryRouter>
    );

    // Check that the loading indicator is shown initially
    expect(screen.getByText(/Loading.../i)).toBeInTheDocument();
});
