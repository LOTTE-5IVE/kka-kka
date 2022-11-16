export const isEmail = (text) => {
  const pattern = /^[a-zA-Z.-]+\.([a-zA-Z]{2,3})$/;

  if (pattern.test(text)) {
    return true;
  }

  return false;
};
