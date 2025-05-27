import { render, screen } from "@testing-library/react";
import App from "../App";

describe("App component", () => {
  test("renders the heading", () => {
    render(<App />);
    const heading = screen.getByText(/hello/i);
    expect(heading).toBeInTheDocument();
  });
});
