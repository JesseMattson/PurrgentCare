import { render, screen } from '@testing-library/react';
import App from './App';

test('renders manage accounts link', () => {
  render(<App />);
  const linkElement = screen.getByText('Manage Accounts');
  expect(linkElement).toBeInTheDocument();
});

test('renders manage customers link', () => {
  render(<App />);
  const linkElement = screen.getByText('Manage Customers');
  expect(linkElement).toBeInTheDocument();
});

test('renders manage pets link', () => {
  render(<App />);
  const linkElement = screen.getByText('Manage Pets');
  expect(linkElement).toBeInTheDocument();
});