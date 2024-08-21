import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import PersonEdit from './PersonEdit';
import { PERSON_BASE_URL } from './constants';

// Mock the fetch API
global.fetch = jest.fn((url) => {
    if (url.includes('1')) {
        return Promise.resolve({
            json: () => Promise.resolve({
                id: 1,
                name: 'Joe Smith',
            }),
        });
    }
    return Promise.resolve({
        json: () => Promise.resolve({}),
    });
});

const renderComponent = (initialEntries = [`${PERSON_BASE_URL}/new`]) => {
    render(
        <MemoryRouter initialEntries={initialEntries}>
            <Routes>
                <Route path={`${PERSON_BASE_URL}/:id`} element={<PersonEdit />} />
            </Routes>
        </MemoryRouter>
    );
};

test('renders the PersonEdit component for adding a new person', () => {
    renderComponent();

    // Check that the title is "Add Person"
    expect(screen.getByText('Add Person')).toBeInTheDocument();

    // Check that the name input field is empty
    const nameInput = screen.getByLabelText('Name');
    expect(nameInput.value).toBe('');
});

test('renders the PersonEdit component for editing an existing person', async () => {
    const mockPersonData = { id: 1, name: 'John Doe' };

    // Mock the fetch for existing person data
    global.fetch.mockImplementationOnce(() =>
        Promise.resolve({
            json: () => Promise.resolve(mockPersonData),
        })
    );

    renderComponent([`${PERSON_BASE_URL}/1`]);

    // Check that the title is "Edit Person"
    expect(await screen.findByText('Edit Person')).toBeInTheDocument();

    // Check that the name input field is populated with the fetched data
    const nameInput = screen.getByLabelText('Name');
    expect(nameInput.value).toBe(mockPersonData.name);
});
