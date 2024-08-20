import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

// Mock the reportWebVitals function
jest.mock('./reportWebVitals', () => jest.fn());

test('renders index component on default route', () => {
    const { getByAltText, getByText } = render(<App />);

    // Check for the presence of the image and links
    expect(getByAltText('Care')).toBeInTheDocument();
    expect(getByText(/Manage Accounts/i)).toBeInTheDocument();
    expect(getByText(/Manage Customers/i)).toBeInTheDocument();
    expect(getByText(/Manage Pets/i)).toBeInTheDocument();
});

