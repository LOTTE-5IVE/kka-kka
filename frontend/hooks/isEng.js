export const isEng = (text) => {
  const pattern = /[^a-z]/gi;

  for (let i = 0; i < text.length; i++) {
    if (pattern.test(text.charAt(i))) {
      return false;
    }
  }

  return true;
};
