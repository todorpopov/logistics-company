import { validateAuthFields } from './validateAuthFields';

describe('validateAuthFields', () => {
  it('should return error if email is missing', () => {
    const result = validateAuthFields('', 'password123');
    expect(result.emailError).toBe('Email is required.');
    expect(result.passwordError).toBe('');
    expect(result.valid).toBe(false);
  });

  it('should return error if email is invalid', () => {
    const result = validateAuthFields('invalid-email', 'password123');
    expect(result.emailError).toBe('Please enter a valid email address.');
    expect(result.passwordError).toBe('');
    expect(result.valid).toBe(false);
  });

  it('should return error if password is missing', () => {
    const result = validateAuthFields('user@example.com', '');
    expect(result.emailError).toBe('');
    expect(result.passwordError).toBe('Password is required.');
    expect(result.valid).toBe(false);
  });

  it('should return no errors for valid email and password', () => {
    const result = validateAuthFields('user@example.com', 'password123');
    expect(result.emailError).toBe('');
    expect(result.passwordError).toBe('');
    expect(result.valid).toBe(true);
  });

  it('should return both errors if both fields are missing', () => {
    const result = validateAuthFields('', '');
    expect(result.emailError).toBe('Email is required.');
    expect(result.passwordError).toBe('Password is required.');
    expect(result.valid).toBe(false);
  });
});

describe('validateAuthFields with first and last name', () => {
  it('should return error if firstName is missing', () => {
    const result = validateAuthFields('user@example.com', 'password123', '', 'Smith');
    expect(result.firstNameError).toBe('First name is required');
    expect(result.lastNameError).toBe('');
    expect(result.valid).toBe(false);
  });

  it('should return error if lastName is missing', () => {
    const result = validateAuthFields('user@example.com', 'password123', 'John', '');
    expect(result.firstNameError).toBe('');
    expect(result.lastNameError).toBe('Last name is required');
    expect(result.valid).toBe(false);
  });

  it('should return both errors if both names are missing', () => {
    const result = validateAuthFields('user@example.com', 'password123', '', '');
    expect(result.firstNameError).toBe('First name is required');
    expect(result.lastNameError).toBe('Last name is required');
    expect(result.valid).toBe(false);
  });

  it('should return no errors for valid email, password, firstName, and lastName', () => {
    const result = validateAuthFields('user@example.com', 'password123', 'John', 'Smith');
    expect(result.emailError).toBe('');
    expect(result.passwordError).toBe('');
    expect(result.firstNameError).toBe('');
    expect(result.lastNameError).toBe('');
    expect(result.valid).toBe(true);
  });
});
