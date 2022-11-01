export const useTextCheck = (text) => {
  const pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;

  for (let i = 0; i < text.length; i++) {
    if (pattern.test(text.charAt(i))) {
      return false;
    }
  }

  return true;
};
