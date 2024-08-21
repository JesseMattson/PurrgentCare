import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import AccountEdit from './AccountEdit';
import { ACCOUNT_BASE_URL } from './constants';

// Mock the fetch API
global.fetch = jest.fn((url) => {
    if (url.includes('1')) {
        return Promise.resolve({
            json: () => Promise.resolve({
                id: 1,
                active: true,
                dateCreated: '2023-01-01',
            }),
        });
    }
    return Promise.resolve({
        json: () => Promise.resolve({}),
    });
});

const renderComponent = (initialEntries = [`${ACCOUNT_BASE_URL}/new`]) => {
    render(
        <MemoryRouter initialEntries={initialEntries}>
            <Routes>
                <Route path={`${ACCOUNT_BASE_URL}/:id`} element={<AccountEdit />} />
            </Routes>
        </MemoryRouter>
    );
};

test('renders the AccountEdit component for adding a new account', () => {
    renderComponent();

    // Check that the title is "Add Account"
    expect(screen.getByText('Add Account')).toBeInTheDocument();

    // Check that the active input field is empty
    const activeInput = screen.getByLabelText('Active');
    expect(activeInput.value).toBe('');
});

test('renders the AccountEdit component for editing an existing account', async () => {
    const mockAccountData = { id: 1, active: true, dateCreated: '2023-01-01' };

    // Mock the fetch for existing account data
    global.fetch.mockImplementationOnce(() =>
        Promise.resolve({
            json: () => Promise.resolve(mockAccountData),
        })
    );

    renderComponent([`${ACCOUNT_BASE_URL}/1`]);

    // Check that the title is "Edit Account"
    expect(await screen.findByText('Edit Account')).toBeInTheDocument();

    // Check that the active input field is populated with the fetched data
    const activeInput = screen.getByLabelText('Active');
    expect(activeInput.value).toBe(mockAccountData.active.toString());
});

/* TOOD: Fix broken tests
test('submits the form and navigates back to the account list', async () => {
    const mockNavigate = jest.fn();

    global.fetch.mockImplementationOnce(() =>
        Promise.resolve({
            json: () => Promise.resolve({ id: 1, active: true }),
        })
    );

    renderComponent();

    // Change the active input
    const activeInput = screen.getByLabelText('Active');
    fireEvent.change(activeInput, { target: { value: 'false' } });

    // Mock the fetch for the form submission
    global.fetch.mockImplementationOnce(() =>
        Promise.resolve({
            status: 200,
        })
    );

    // Submit the form
    const saveButton = screen.getByText('Save');
    fireEvent.click(saveButton);

    // Wait for the navigation to occur
    await waitFor(() => expect(mockNavigate).toHaveBeenCalledWith(`${ACCOUNT_BASE_URL}`));
});

test('navigates back to the account list when cancel is clicked', () => {
    renderComponent();

    const cancelButton = screen.getByText('Cancel');
    fireEvent.click(cancelButton);

    // Verify that navigation back to account list happened
    expect(screen.getByText('Accounts')).toBeInTheDocument();
});
*/