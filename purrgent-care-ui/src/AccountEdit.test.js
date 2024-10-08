import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { MemoryRouter, Route, Routes } from 'react-router-dom';
import AccountEdit from './AccountEdit';
import { ACCOUNT_BASE_URL, PERSON_BASE_URL } from './constants';

// Mock the fetch API globally
global.fetch = jest.fn();

// Utility to mock fetch responses based on URL
const mockFetchResponse = (url, data) => {
    global.fetch.mockImplementationOnce((fetchUrl) => {
        if (fetchUrl.includes(url)) {
            return Promise.resolve({
                json: () => Promise.resolve(data),
            });
        }
        return Promise.resolve({
            json: () => Promise.resolve({}),
        });
    });
};

// Mock useNavigate globally
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockNavigate,
}));

const renderComponent = (initialEntries = [`${ACCOUNT_BASE_URL}/new`]) => {
    render(
        <MemoryRouter initialEntries={initialEntries}>
            <Routes>
                <Route path={`${ACCOUNT_BASE_URL}/:id`} element={<AccountEdit />} />
            </Routes>
        </MemoryRouter>
    );
};

// Reset mocks before each test
beforeEach(() => {
    jest.clearAllMocks();
});

// Test for adding a new account
test('renders the AccountEdit component for adding a new account', async () => {
    // Mock the fetch call for persons (account holders dropdown)
    mockFetchResponse(PERSON_BASE_URL, [
        { id: 1, name: 'John Doe' },
        { id: 2, name: 'Jane Smith' }
    ]);

    renderComponent();

    // Ensure fetch and state update completes
    await waitFor(() => {
        // Check that the title is "Add Account"
        expect(screen.getByText('Add Account')).toBeInTheDocument();

        // Check that the active input field is set to true by default
        const activeInput = screen.getByLabelText('Active');
        expect(activeInput.value).toBe('true');  // Expect 'true' as default value
    });
});

// Test for editing an existing account
test('renders the AccountEdit component for editing an existing account', async () => {
    const mockAccountData = {
        id: 1,
        active: true,
        dateCreated: '2023-01-01',
        accountHolders: [{ id: '4' }]
    };

    // Mock the fetch calls for the existing account and persons
    mockFetchResponse(`${ACCOUNT_BASE_URL}/1`, mockAccountData);
    mockFetchResponse(PERSON_BASE_URL, [
        { id: 1, name: 'John Doe' },
        { id: 2, name: 'Jane Smith' }
    ]);

    renderComponent([`${ACCOUNT_BASE_URL}/1`]);

    // Wait for state updates and fetch to complete
    await waitFor(async () => {
        // Check that the title is "Edit Account"
        expect(await screen.findByText('Edit Account')).toBeInTheDocument();

        // Check that the active input field is populated with the fetched data
        const activeInput = screen.getByLabelText('Active');
        expect(activeInput.value).toBe(mockAccountData.active.toString());
    });
});

/* TODO: Fix these tests
// Test for form submission
test('submits the form and navigates back to the account list', async () => {
    const mockAccountData = { id: 1, active: true, dateCreated: '2023-01-01', accountHolders: [] };

    // Mock the fetch calls
    mockFetchResponse(PERSON_BASE_URL, [
        { id: 1, name: 'John Doe' },
        { id: 2, name: 'Jane Smith' }
    ]);
    mockFetchResponse(`${ACCOUNT_BASE_URL}/1`, mockAccountData);

    renderComponent([`${ACCOUNT_BASE_URL}/1`]);

    // Wait for the data to be loaded before interacting with the form
    await waitFor(() => expect(screen.getByLabelText('Active')).toBeInTheDocument());

    // Change the active input
    const activeInput = screen.getByLabelText('Active');
    fireEvent.change(activeInput, { target: { value: 'false' } });

    // Mock the fetch for form submission
    global.fetch.mockImplementationOnce(() =>
        Promise.resolve({
            status: 200,
        })
    );

    // Submit the form
    const saveButton = screen.getByText('Save');
    fireEvent.click(saveButton);

    // Wait for the form submission and navigation
    await waitFor(() => expect(mockNavigate).toHaveBeenCalledWith(`${ACCOUNT_BASE_URL}`));
});

// Test for cancel button functionality
test('navigates back to the account list when cancel is clicked', async () => {
    mockFetchResponse(PERSON_BASE_URL, [
        { id: 1, name: 'John Doe' },
        { id: 2, name: 'Jane Smith' }
    ]);

    renderComponent();

    // Wait for the data to be loaded
    await waitFor(() => expect(screen.getByLabelText('Active')).toBeInTheDocument());

    const cancelButton = screen.getByText('Cancel');
    fireEvent.click(cancelButton);

    // Verify that navigation back to account list happened
    expect(mockNavigate).toHaveBeenCalledWith(`${ACCOUNT_BASE_URL}`);
});
 */
