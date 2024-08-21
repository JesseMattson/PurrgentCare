import React from 'react';
import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import AccountList from './AccountList';
import { ACCOUNT_BASE_URL } from './constants';
import { format } from 'date-fns';

// Mock the fetch API
global.fetch = jest.fn();

const mockAccounts = [
    { id: 1, active: true, dateCreated: new Date(2023, 6, 1) },
    { id: 2, active: false, dateCreated: new Date(2023, 6, 2) },
];

beforeEach(() => {
    fetch.mockImplementation((url) => {
        if (url === `${ACCOUNT_BASE_URL}`) {
            return Promise.resolve({
                json: () => Promise.resolve(mockAccounts),
            });
        }
        return Promise.resolve({});
    });
});

afterEach(() => {
    jest.clearAllMocks();
});

const renderComponent = () => {
    render(
        <MemoryRouter>
            <AccountList />
        </MemoryRouter>
    );
};

test('renders the AccountList component and shows loading indicator', () => {
    fetch.mockImplementationOnce(() =>
        new Promise((resolve) => setTimeout(() => resolve({ json: () => mockAccounts }), 100))
    );

    renderComponent();

    // Check that the loading indicator is displayed
    expect(screen.getByText('Loading...')).toBeInTheDocument();
});

test('renders the AccountList component and displays accounts', async () => {
    renderComponent();

    // Wait for the accounts to be displayed
    await waitFor(() => expect(screen.getByText('Accounts')).toBeInTheDocument());

    // Check that the account data is rendered in the table
    const accountRows = screen.getAllByRole('row');
    expect(accountRows).toHaveLength(mockAccounts.length + 1); // +1 for the header row

    // Check specific account details
    expect(screen.getByText(mockAccounts[0].id.toString())).toBeInTheDocument();
    expect(screen.getByText(mockAccounts[0].active.toString())).toBeInTheDocument();
    expect(screen.getByText(format(mockAccounts[0].dateCreated, 'yyyy/MM/dd'))).toBeInTheDocument();

    expect(screen.getByText(mockAccounts[1].id.toString())).toBeInTheDocument();
    expect(screen.getByText(mockAccounts[1].active.toString())).toBeInTheDocument();
    expect(screen.getByText(format(mockAccounts[1].dateCreated, 'yyyy/MM/dd'))).toBeInTheDocument();
});

test('calls toggle_status when the toggle button is clicked', async () => {
    renderComponent();

    // Wait for the accounts to be displayed
    await waitFor(() => expect(screen.getByText('Accounts')).toBeInTheDocument());

    // Mock the fetch for the status toggle
    fetch.mockImplementationOnce(() =>
        Promise.resolve({
            status: 200,
        })
    );

    // Click the toggle button
    const toggleButton = screen.getAllByText('Disable')[0];
    fireEvent.click(toggleButton);

    // Verify that the fetch was called for the status toggle
    expect(fetch).toHaveBeenCalledWith(`${ACCOUNT_BASE_URL}/status/${mockAccounts[0].id}`, expect.any(Object));
});
