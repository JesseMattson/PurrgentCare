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

const renderComponent = () => {
    render(
        <MemoryRouter>
            <PetList />
        </MemoryRouter>
    );
};

test('renders PetList and displays pets', async () => {
    renderComponent()

    // Check that the loading indicator is shown initially
    expect(screen.getByText('Loading...')).toBeInTheDocument();

    // Wait for the pets to be loaded and displayed
    await waitFor(() => {
        expect(screen.getByText('Buddy')).toBeInTheDocument();
        expect(screen.getByText('Mittens')).toBeInTheDocument();
    });

    // Check that the pet details are displayed correctly
    expect(screen.getByText('Dog')).toBeInTheDocument();
    expect(screen.getByText('Cat')).toBeInTheDocument();
    expect(screen.getByText('5')).toBeInTheDocument();
    expect(screen.getByText('3')).toBeInTheDocument();
    expect(screen.getByText('Male')).toBeInTheDocument();
    expect(screen.getByText('Female')).toBeInTheDocument();
});

test('handles deletion of a pet correctly', async () => {
    renderComponent()

    // Wait for the pets to be loaded and displayed
    await waitFor(() => {
        expect(screen.getByText('Buddy')).toBeInTheDocument();
        expect(screen.getByText('Mittens')).toBeInTheDocument();
    });

    // Delete the first pet
    fireEvent.click(screen.getAllByText('Delete')[0]);

    // Wait for the pet to be removed from the list
    await waitFor(() => {
        expect(screen.queryByText('Buddy')).not.toBeInTheDocument();
        expect(screen.getByText('Mittens')).toBeInTheDocument();
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

    renderComponent()

    // Wait for the component to finish loading
    await waitFor(() => {
        expect(screen.queryByText('Loading...')).not.toBeInTheDocument();
    });

    // Check that the table body is empty
    expect(screen.queryByText('Buddy')).not.toBeInTheDocument();
    expect(screen.queryByText('Mittens')).not.toBeInTheDocument();
    expect(screen.getByText('Pets')).toBeInTheDocument();
});

test('renders loading state correctly', () => {
    renderComponent()

    // Check that the loading indicator is shown initially
    expect(screen.getByText('Loading...')).toBeInTheDocument();
});
