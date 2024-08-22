import React from 'react';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Home from './Home';

test('renders the Home component with navbar, image, and links', () => {
    const { getByAltText, getByText } = render(
        <MemoryRouter>
            <Home />
        </MemoryRouter>
    );

    // Check for the presence of the image with alt text "Care"
    expect(getByAltText('Care')).toBeInTheDocument();

    // Check for the presence of the navigation links
    expect(getByText('Manage Accounts')).toBeInTheDocument();
    expect(getByText('Manage Customers')).toBeInTheDocument();
    expect(getByText('Manage Pets')).toBeInTheDocument();
});
