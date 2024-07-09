import { render, screen } from '@testing-library/react';
import App from './App';

test('renders manage accounts link', () => {
  render(<App />);
  const linkElement = screen.getByText(/manage accounts/i);
  expect(linkElement).toBeInTheDocument();
});

test('renders manage customers link', () => {
  render(<App />);
  const linkElement = screen.getByText(/manage customers/i);
  expect(linkElement).toBeInTheDocument();
});

test('renders manage pets link', () => {
  render(<App />);
  const linkElement = screen.getByText(/manage pets/i);
  expect(linkElement).toBeInTheDocument();
});
