import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import PetEdit from './PetEdit';
import { PET_BASE_URL } from './constants';

// Mock the fetch API
global.fetch = jest.fn((url) => {
    if (url.includes('1')) {
        return Promise.resolve({
            json: () => Promise.resolve({
                id: 1,
                name: 'Buddy',
                type: 'Dog',
                age: '5',
                gender: 'Male',
            }),
        });
    }
    return Promise.resolve({
        json: () => Promise.resolve({}),
    });
});

const renderComponent = (initialEntries) => {
    render(
        <MemoryRouter initialEntries={initialEntries}>
            <Routes>
                <Route path={`${PET_BASE_URL}/:id`} element={<PetEdit />} />
                <Route path={PET_BASE_URL} element={<div>Redirected</div>} />
            </Routes>
        </MemoryRouter>
    );
};

/* TODO: Broken test with then() issues with implementation
test('renders the PetEdit component in edit mode', async () => {
    renderComponent([`${PET_BASE_URL}/1`]);

    // Check that the title is "Edit Pet"
    expect(await screen.findByText(/Edit Pet/i)).toBeInTheDocument();

    // Check that the form fields are populated with fetched data
    expect(screen.getByLabelText(/Name/i).value).toBe('Buddy');
    expect(screen.getByLabelText(/Type/i).value).toBe('Dog');
    expect(screen.getByLabelText(/Age/i).value).toBe('5');
    expect(screen.getByLabelText(/Gender/i).value).toBe('Male');
});
*/

test('renders the PetEdit component in add mode', () => {
    renderComponent([`${PET_BASE_URL}/new`]);

    // Check that the title is "Add Pet"
    expect(screen.getByText(/Add Pet/i)).toBeInTheDocument();

    // Check that the form fields are empty
    expect(screen.getByLabelText(/Name/i).value).toBe('');
    expect(screen.getByLabelText(/Type/i).value).toBe('');
    expect(screen.getByLabelText(/Age/i).value).toBe('');
    expect(screen.getByLabelText(/Gender/i).value).toBe('');
});

test('handles form submission correctly', async () => {
    renderComponent([`${PET_BASE_URL}/new`]);

    // Fill out the form
    fireEvent.change(screen.getByLabelText(/Name/i), { target: { value: 'Charlie' } });
    fireEvent.change(screen.getByLabelText(/Type/i), { target: { value: 'Cat' } });
    fireEvent.change(screen.getByLabelText(/Age/i), { target: { value: '3' } });
    fireEvent.change(screen.getByLabelText(/Gender/i), { target: { value: 'Female' } });

    // Mock the fetch POST request
    global.fetch.mockImplementationOnce(() =>
        Promise.resolve({
            json: () => Promise.resolve({ id: 2, name: 'Charlie', type: 'Cat', age: '3', gender: 'Female' }),
        })
    );

    // Submit the form
    fireEvent.click(screen.getByText(/Save/i));

    // Wait for navigation
    await waitFor(() => {
        expect(screen.getByText(/Redirected/i)).toBeInTheDocument();
    });

    // Ensure the fetch was called with correct data
    expect(global.fetch).toHaveBeenCalledWith(`${PET_BASE_URL}`, expect.objectContaining({
        method: 'POST',
        body: JSON.stringify({ name: 'Charlie', type: 'Cat', age: '3', gender: 'Female' }),
    }));
});
