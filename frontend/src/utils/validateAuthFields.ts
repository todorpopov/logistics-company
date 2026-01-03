export interface LoginValidationResult {
  emailError: string;
  passwordError: string;
  firstNameError?: string;
  lastNameError?: string;
  valid: boolean;
}

export function validateAuthFields(
  email: string,
  password: string,
  firstName?: string,
  lastName?: string
): LoginValidationResult {
  let emailError = '';
  let passwordError = '';
  let firstNameError = '';
  let lastNameError = '';
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

  if (typeof firstName !== 'undefined') {
    if (!firstName.trim()) {
      firstNameError = 'First name is required';
      valid = false;
    }
  }
  if (typeof lastName !== 'undefined') {
    if (!lastName.trim()) {
      lastNameError = 'Last name is required';
      valid = false;
    }
  }

  return { emailError, passwordError, firstNameError, lastNameError, valid };
}
