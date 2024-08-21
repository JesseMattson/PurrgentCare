import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import PersonList from './PersonList';
import { PERSON_BASE_URL } from './constants';

// Mock the fetch API globally
beforeEach(() => {
    global.fetch = jest.fn();
});

afterEach(() => {
    jest.resetAllMocks();
});

const renderComponent = () => {
    render(
        <MemoryRouter initialEntries={[`${PERSON_BASE_URL}`]}>
            <Routes>
                <Route path={`${PERSON_BASE_URL}`} element={<PersonList />} />
                <Route path={`${PERSON_BASE_URL}/new`} element={<div>Add Person Page</div>} />
                <Route path={`${PERSON_BASE_URL}/:id`} element={<div>Edit Person Page</div>} />
            </Routes>
        </MemoryRouter>
    );
};

test('renders the PersonList component and fetches data', async () => {
    global.fetch.mockImplementationOnce(() =>
        Promise.resolve({
            json: () => Promise.resolve([
                { id: 1, name: 'John Doe' },
                { id: 2, name: 'Jane Smith' },
            ]),
        })
    );

    renderComponent();

    // Check that loading indicator is shown
    expect(screen.getByText('Loading...')).toBeInTheDocument();

    // Wait for the data to be fetched and check that it's displayed
    expect(await screen.findByText('John Doe')).toBeInTheDocument();
    expect(screen.getByText('Jane Smith')).toBeInTheDocument();
});

test('handles delete action correctly', async () => {
    global.fetch.mockImplementationOnce(() =>
        Promise.resolve({
            json: () => Promise.resolve([
                { id: 1, name: 'John Doe' },
                { id: 2, name: 'Jane Smith' },
            ]),
        })
    );

    global.fetch.mockImplementationOnce(() =>
        Promise.resolve({
            status: 204,
        })
    );

    renderComponent();

    // Wait for data to load
    await screen.findByText('John Doe');

    // Click the delete button for John Doe
    fireEvent.click(screen.getAllByText('Delete')[0]);

    // Wait for the data to be updated
    await waitFor(() => {
        expect(screen.queryByText('John Doe')).not.toBeInTheDocument();
    });

    // Ensure the fetch was called with correct data
    expect(global.fetch).toHaveBeenCalledWith(`${PERSON_BASE_URL}/1`, expect.objectContaining({
        method: 'DELETE',
    }));
});

/* TODO: Fix broken test
test('handles navigation to add person page', () => {
    renderComponent();

    // Click the "Add person" button
    fireEvent.click(screen.getByText('Add person'));

    // Check that the navigation occurred
    expect(screen.getByText('Add Person Page')).toBeInTheDocument();
});
*/

test('handles navigation to edit person page', async () => {
    global.fetch.mockImplementationOnce(() =>
        Promise.resolve({
            json: () => Promise.resolve([
                { id: 1, name: 'John Doe' },
                { id: 2, name: 'Jane Smith' },
            ]),
        })
    );

    renderComponent();

    // Wait for data to load
    await screen.findByText('John Doe');

    // Click the "Edit" button for John Doe
    fireEvent.click(screen.getAllByText('Edit')[0]);

    // Check that the navigation occurred
    expect(screen.getByText('Edit Person Page')).toBeInTheDocument();
});
