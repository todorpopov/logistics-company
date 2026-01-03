export interface LoginValidationResult {
  emailError: string;
  passwordError: string;
  valid: boolean;
}

export function validateAuthFields(email: string, password: string): LoginValidationResult {
  let emailError = '';
  let passwordError = '';
  let valid = true;

  // Simple email regex for demonstration
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  if (!email) {
    emailError = 'Email is required.';
    valid = false;
  } else if (!emailRegex.test(email)) {
    emailError = 'Please enter a valid email address.';
    valid = false;
  }

  if (!password) {
    passwordError = 'Password is required.';
    valid = false;
  }

  return { emailError, passwordError, valid };
}
