export const isEngNum = (text) => {
  const pattern = /[^0-9a-zA-Z]/gi;

  for (let i = 0; i < text.length; i++) {
    if (pattern.test(text.charAt(i))) {
      return false;
    }
  }

  return true;
};
